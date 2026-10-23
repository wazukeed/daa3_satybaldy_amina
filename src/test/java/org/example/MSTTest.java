package org.example;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    @Test
    void testConnectedGraph() {
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 3);

        MSTResult k = Kruskal.findMST(graph);
        MSTResult p = Prim.findMST(graph);

        assertEquals(6, k.getTotalWeight());
        assertEquals(k.getTotalWeight(), p.getTotalWeight());

        assertEquals(3, k.getEdges().size());
        assertEquals(3, p.getEdges().size());

        assertTrue(k.isConnected());
        assertTrue(p.isConnected());
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(3, 4, 1);

        MSTResult k = Kruskal.findMST(graph);
        MSTResult p = Prim.findMST(graph);

        assertEquals(6, k.getTotalWeight());
        assertEquals(k.getTotalWeight(), p.getTotalWeight());

        assertEquals(2, k.getComponents());
        assertEquals(2, p.getComponents());
    }

    @Test
    void testNegativeWeights() {
        Graph graph = new Graph(3, false);

        graph.addEdge(0, 1, -5);
        graph.addEdge(1, 2, 2);
        graph.addEdge(0, 2, 10);

        assertEquals(-3, Kruskal.findMST(graph).getTotalWeight());
        assertEquals(-3, Prim.findMST(graph).getTotalWeight());
    }

    @Test
    void testDirectedGraph() {
        Graph graph = new Graph(3, true);
        graph.addEdge(0, 1, 5);

        assertThrows(IllegalArgumentException.class,
                () -> Kruskal.findMST(graph));

        assertThrows(IllegalArgumentException.class,
                () -> Prim.findMST(graph));
    }

    @Test
    void testRandomGraphs() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int n = 2 + random.nextInt(19);
            Graph graph = new Graph(n, false);

            for (int v = 1; v < n; v++) {
                graph.addEdge(v, random.nextInt(v),
                        random.nextInt(101) - 50);
            }

            for (int i = 0; i < n * 3; i++) {
                int a = random.nextInt(n);
                int b = random.nextInt(n);

                if (a != b) {
                    graph.addEdge(a, b,
                            random.nextInt(101) - 50);
                }
            }

            MSTResult k = Kruskal.findMST(graph);
            MSTResult p = Prim.findMST(graph);

            assertEquals(k.getTotalWeight(), p.getTotalWeight());
            assertEquals(n - 1, k.getEdges().size());
            assertEquals(n - 1, p.getEdges().size());
        }
    }
}