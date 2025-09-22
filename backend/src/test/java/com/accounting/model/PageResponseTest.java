package test.java.com.accounting.model;
import com.accounting.model.PageResponse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResponseTest {

    @Test
    void constructorCalculatesTotalPages_andGettersSetters() {
        PageResponse<Integer> p = new PageResponse<>(List.of(1,2,3), 0, 2, 3);
        assertEquals(2, p.getTotalPages());
        assertEquals(3, p.getContent().size());
        assertEquals(0, p.getPage());
        assertEquals(2, p.getSize());
        assertEquals(3, p.getTotalElements());

        p.setTotalPages(5);
        p.setPage(1);
        p.setSize(10);
        p.setTotalElements(100);
        p.setContent(List.of(4,5));

        assertEquals(5, p.getTotalPages());
        assertEquals(1, p.getPage());
        assertEquals(10, p.getSize());
        assertEquals(100, p.getTotalElements());
        assertEquals(2, p.getContent().size());
    }
}


