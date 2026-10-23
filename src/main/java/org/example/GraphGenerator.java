package org.example;

import java.util.Random;

public class GraphGenerator {

    public static Graph connectedUndirected(int n, int edgeCount, long seed) {
        if (n < 2 || edgeCount < n - 1) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Graph graph = new Graph(n, false);
        Random random = new Random(seed);

        // First connect all vertices
        for (int i = 1; i < n; i++) {
            int parent = random.nextInt(i);
            int weight = 1 + random.nextInt(100);
            graph.addEdge(parent, i, weight);
        }

        // Add more random edges
        while (graph.getEdgeCount() < edgeCount) {
            int from = random.nextInt(n);
            int to = random.nextInt(n);

            if (from != to) {
                graph.addEdge(from, to, 1 + random.nextInt(100));
            }
        }

        return graph;
    }

    public static Graph directedGraph(int n, int edgeCount, long seed) {
        if (n < 2 || edgeCount < 0) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Graph graph = new Graph(n, true);
        Random random = new Random(seed);

        while (graph.getEdgeCount() < edgeCount) {
            int from = random.nextInt(n);
            int to = random.nextInt(n);

            if (from != to) {
                graph.addEdge(from, to, random.nextInt(101));
            }
        }

        return graph;
    }

    public static Graph dagGraph(int n, int edgeCount, long seed) {
        if (n < 2 || edgeCount < n - 1) {
            throw new IllegalArgumentException("Invalid graph size");
        }

        Graph graph = new Graph(n, true);
        Random random = new Random(seed);

        // A chain makes every vertex reachable from 0
        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, random.nextInt(31) - 10);
        }

        // Edges go only from smaller to larger vertices
        while (graph.getEdgeCount() < edgeCount) {
            int from = random.nextInt(n);
            int to = random.nextInt(n);

            if (from < to) {
                graph.addEdge(from, to, random.nextInt(31) - 10);
            }
        }

        return graph;
    }
}