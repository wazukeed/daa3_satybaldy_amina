package org.example;

import java.util.Arrays;
import java.util.List;

public class DagShortestPath {

    public static long[] shortestPaths(Graph graph, int source) {
        int n = graph.getVertexCount();

        if (source < 0 || source >= n) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        List<Integer> order = Kahn.topologicalSort(graph);

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[source] = 0;

        for (int vertex : order) {
            if (dist[vertex] == Long.MAX_VALUE) {
                continue;
            }

            for (Edge edge : graph.neighbors(vertex)) {
                Metrics.countEdge();

                long newDist = dist[vertex] + edge.weight;

                Metrics.countComparison();

                if (newDist < dist[edge.to]) {
                    dist[edge.to] = newDist;
                }
            }
        }

        return dist;
    }
}