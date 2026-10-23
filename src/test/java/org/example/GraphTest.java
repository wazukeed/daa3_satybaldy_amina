package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    void testUndirectedGraph() {
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 2, 3);

        assertEquals(4, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
        assertEquals(1, graph.neighbors(0).size());
        assertEquals(2, graph.neighbors(1).size());
        assertEquals(1, graph.neighbors(2).size());
    }

    @Test
    void testDirectedGraph() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 10);

        assertEquals(1, graph.neighbors(0).size());
        assertEquals(0, graph.neighbors(1).size());
    }

    @Test
    void testInvalidVertex() {
        Graph graph = new Graph(3, false);

        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 5, 10);
        });
    }
}