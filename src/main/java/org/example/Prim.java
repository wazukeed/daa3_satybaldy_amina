package org.example;

import java.util.*;

public class Prim {

    public static MSTResult findMST(Graph graph) {

        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        int n = graph.getVertexCount();

        boolean[] visited = new boolean[n];
        List<Edge> mstEdges = new ArrayList<>();

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> {
            Metrics.countComparison();
            return Integer.compare(a.weight, b.weight);
        });

        long totalWeight = 0;
        int components = 0;

        for (int start = 0; start < n; start++) {

            if (visited[start]) {
                continue;
            }

            components++;
            visited[start] = true;

            for (Edge edge : graph.neighbors(start)) {
                Metrics.countEdge();
                pq.add(edge);
            }

            while (!pq.isEmpty()) {

                Edge edge = pq.poll();

                if (visited[edge.to]) {
                    continue;
                }

                visited[edge.to] = true;
                mstEdges.add(edge);
                totalWeight += edge.weight;

                for (Edge next : graph.neighbors(edge.to)) {
                    Metrics.countEdge();

                    if (!visited[next.to]) {
                        pq.add(next);
                    }
                }
            }
        }

        return new MSTResult(
                mstEdges,
                totalWeight,
                components
        );
    }
}