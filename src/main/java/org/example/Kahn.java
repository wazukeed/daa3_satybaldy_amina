package org.example;

import java.util.*;

public class Kahn {

    public static List<Integer> topologicalSort(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        int n = graph.getVertexCount();
        int[] indegree = new int[n];

        for (Edge edge : graph.getEdges()) {
            Metrics.countEdge();
            indegree[edge.to]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {

            int v = queue.poll();
            order.add(v);

            for (Edge edge : graph.neighbors(v)) {

                Metrics.countEdge();

                indegree[edge.to]--;

                Metrics.countComparison();
                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        if (order.size() != n) {
            throw new IllegalStateException("Graph contains a cycle");
        }

        return order;
    }

    public static boolean hasCycle(Graph graph) {
        try {
            topologicalSort(graph);
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }
}