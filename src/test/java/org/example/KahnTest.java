package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KahnTest {

    @Test
    void testTopologicalSort() {
        Graph graph = new Graph(6, true);

        graph.addEdge(5, 2, 1);
        graph.addEdge(5, 0, 1);
        graph.addEdge(4, 0, 1);
        graph.addEdge(4, 1, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 1, 1);

        List<Integer> order = Kahn.topologicalSort(graph);

        assertEquals(6, order.size());

        int[] position = new int[6];

        for (int i = 0; i < order.size(); i++) {
            position[order.get(i)] = i;
        }

        for (Edge edge : graph.getEdges()) {
            assertTrue(position[edge.from] < position[edge.to]);
        }

        assertFalse(Kahn.hasCycle(graph));
    }

    @Test
    void testCycle() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        assertTrue(Kahn.hasCycle(graph));

        assertThrows(IllegalStateException.class,
                () -> Kahn.topologicalSort(graph));
    }

    @Test
    void testNoEdges() {
        Graph graph = new Graph(4, true);

        assertEquals(4, Kahn.topologicalSort(graph).size());
        assertFalse(Kahn.hasCycle(graph));
    }

    @Test
    void testUndirectedGraph() {
        Graph graph = new Graph(3, false);

        assertThrows(IllegalArgumentException.class,
                () -> Kahn.topologicalSort(graph));
    }

    @Test
    void testLargeGraph() {
        int n = 100000;
        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        List<Integer> order = Kahn.topologicalSort(graph);

        assertEquals(n, order.size());
        assertEquals(0, order.get(0));
        assertEquals(n - 1, order.get(n - 1));
    }
}