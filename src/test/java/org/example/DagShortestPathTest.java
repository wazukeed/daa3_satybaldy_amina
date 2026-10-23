package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathTest {

    @Test
    void testSimpleDAG() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 4, 3);

        long[] dist = DagShortestPath.shortestPaths(graph, 0);

        assertArrayEquals(new long[]{0, 2, 3, 4, 7}, dist);
    }

    @Test
    void testNegativeWeights() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, -10);
        graph.addEdge(2, 3, 3);

        long[] dist = DagShortestPath.shortestPaths(graph, 0);

        assertArrayEquals(new long[]{0, 4, -6, -3}, dist);
    }

    @Test
    void testUnreachableVertices() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 7);
        graph.addEdge(2, 3, 1);

        long[] dist = DagShortestPath.shortestPaths(graph, 0);

        assertEquals(0, dist[0]);
        assertEquals(7, dist[1]);
        assertEquals(Long.MAX_VALUE, dist[2]);
        assertEquals(Long.MAX_VALUE, dist[3]);
    }

    @Test
    void testCycle() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        assertThrows(IllegalStateException.class,
                () -> DagShortestPath.shortestPaths(graph, 0));
    }

    @Test
    void testInvalidInput() {
        Graph graph = new Graph(3, true);

        assertThrows(IllegalArgumentException.class,
                () -> DagShortestPath.shortestPaths(graph, -1));

        assertThrows(IllegalArgumentException.class,
                () -> DagShortestPath.shortestPaths(graph, 3));

        Graph undirected = new Graph(3, false);

        assertThrows(IllegalArgumentException.class,
                () -> DagShortestPath.shortestPaths(undirected, 0));
    }

    @Test
    void testRandomGraphs() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            int n = 3 + random.nextInt(18);
            Graph graph = new Graph(n, true);

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (random.nextInt(4) == 0) {
                        graph.addEdge(i, j, random.nextInt(21) - 10);
                    }
                }
            }

            long[] expected = new long[n];
            Arrays.fill(expected, Long.MAX_VALUE);
            expected[0] = 0;

            for (int k = 0; k < n - 1; k++) {
                for (Edge edge : graph.getEdges()) {
                    if (expected[edge.from] != Long.MAX_VALUE) {
                        long newDist = expected[edge.from] + edge.weight;

                        if (newDist < expected[edge.to]) {
                            expected[edge.to] = newDist;
                        }
                    }
                }
            }

            long[] actual = DagShortestPath.shortestPaths(graph, 0);

            assertArrayEquals(expected, actual);
        }
    }

    @Test
    void testLargeGraph() {
        int n = 100000;
        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        long[] dist = DagShortestPath.shortestPaths(graph, 0);

        assertEquals(0, dist[0]);
        assertEquals(99999, dist[n - 1]);
    }
}