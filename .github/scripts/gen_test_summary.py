#!/usr/bin/env python3
import glob
import os
import sys
import xml.etree.ElementTree as ET

def parse_tests(surefire_dir: str):
    reports = glob.glob(os.path.join(surefire_dir, 'TEST-*.xml'))
    rows = []
    totals = { 'run': 0, 'fail': 0, 'error': 0, 'skip': 0 }
    for rp in reports:
        try:
            tree = ET.parse(rp)
            root = tree.getroot()
            suites = [root] if root.tag.endswith('testsuite') else root.findall('.//testsuite')
            for suite in suites:
                # suite-level totals
                try:
                    totals['run'] += int(suite.attrib.get('tests', '0'))
                    totals['fail'] += int(suite.attrib.get('failures', '0'))
                    totals['error'] += int(suite.attrib.get('errors', '0'))
                    totals['skip'] += int(suite.attrib.get('skipped', '0'))
                except Exception:
                    pass
                for tc in suite.findall('.//testcase'):
                    classname = tc.attrib.get('classname','')
                    name = tc.attrib.get('name','')
                    time = tc.attrib.get('time','0')
                    status = 'PASS'
                    message = ''
                    if tc.find('skipped') is not None:
                        status = 'SKIPPED'
                        message = (tc.find('skipped').attrib.get('message') or '').strip()
                    elif tc.find('failure') is not None:
                        status = 'FAIL'
                        f = tc.find('failure')
                        message = (f.attrib.get('message') or '').strip() or (f.text or '').strip().split('\n')[0] if f is not None else ''
                    elif tc.find('error') is not None:
                        status = 'ERROR'
                        e = tc.find('error')
                        message = (e.attrib.get('message') or '').strip() or (e.text or '').strip().split('\n')[0] if e is not None else ''
                    rows.append((classname, name, status, time, message))
        except Exception:
            continue
    return totals, rows

def parse_coverage(jacoco_xml: str):
    try:
        tree = ET.parse(jacoco_xml)
        root = tree.getroot()
        missed = 0
        covered = 0
        for c in root.findall('.//counter'):
            if c.attrib.get('type') == 'LINE':
                missed += int(c.attrib.get('missed', '0'))
                covered += int(c.attrib.get('covered', '0'))
        total = missed + covered
        pct = int(covered * 100 / total) if total > 0 else 0
        return pct, covered, total
    except Exception:
        return 0, 0, 0

def main():
    surefire_dir = sys.argv[1] if len(sys.argv) > 1 else 'backend/target/surefire-reports'
    jacoco_xml = sys.argv[2] if len(sys.argv) > 2 else 'backend/target/site/jacoco/jacoco.xml'
    out_path = sys.argv[3] if len(sys.argv) > 3 else 'pr-test-summary.md'

    totals, rows = parse_tests(surefire_dir)
    pct, covered, total = parse_coverage(jacoco_xml)

    lines = []
    lines.append('### Backend Unit Tests Summary')
    lines.append('')
    lines.append(f"- **Tests run**: {totals['run']}")
    lines.append(f"- **Failures**: {totals['fail']}")
    lines.append(f"- **Errors**: {totals['error']}")
    lines.append(f"- **Skipped**: {totals['skip']}")
    lines.append(f"- **Line coverage**: {pct}% (covered {covered} / total {total})")
    lines.append('')
    status_line = '> ❌ Some tests failed. MR/PR is blocked.' if (totals['fail'] or totals['error']) else '> ✅ All tests passed. Coverage gate enforced at 100%.'
    lines.append(status_line)
    lines.append('')

    lines.append('#### Detailed Test Cases')
    lines.append('')
    lines.append('| Class | Test | Status | Time (s) | Message |')
    lines.append('|---|---|---:|---:|---|')
    for classname, name, status, time, message in sorted(rows):
        cname = (classname or '').replace('|','\\|')
        tname = (name or '').replace('|','\\|')
        msg = (message or '').replace('\n',' ').replace('|','\\|')
        lines.append(f"| {cname} | {tname} | {status} | {time} | {msg} |")

    with open(out_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines) + '\n')

if __name__ == '__main__':
    main()


