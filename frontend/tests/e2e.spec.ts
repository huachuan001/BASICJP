import { test, expect } from '@playwright/test';

test('TC001: 首页加载与标题显示', async ({ page }) => {
  await page.goto('/');
  await expect(page.getByRole('heading', { name: '记账管理系统' })).toBeVisible();
});


