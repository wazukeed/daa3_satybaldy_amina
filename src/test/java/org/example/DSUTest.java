package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DSUTest {

    @Test
    void testInitialState() {
        DSU dsu = new DSU(5);

        assertEquals(5, dsu.components());
        assertFalse(dsu.connected(0, 1));
        assertEquals(1, dsu.componentSize(0));
    }

    @Test
    void testUnion() {
        DSU dsu = new DSU(5);

        assertTrue(dsu.union(0, 1));
        assertTrue(dsu.union(1, 2));

        assertTrue(dsu.connected(0, 2));
        assertEquals(3, dsu.components());
    }

    @Test
    void testSameComponent() {
        DSU dsu = new DSU(3);

        assertTrue(dsu.union(0, 1));
        assertFalse(dsu.union(0, 1));

        assertEquals(2, dsu.components());
    }

    @Test
    void testComponentSize() {
        DSU dsu = new DSU(5);

        dsu.union(0, 1);
        dsu.union(2, 3);
        dsu.union(0, 2);

        assertEquals(4, dsu.componentSize(3));
        assertEquals(2, dsu.components());
    }

    @Test
    void testInvalidElement() {
        DSU dsu = new DSU(3);

        assertThrows(IllegalArgumentException.class, () -> {
            dsu.find(5);
        });
    }
}