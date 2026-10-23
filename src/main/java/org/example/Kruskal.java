package org.example;

import java.util.*;

public class Kruskal {

    public static MSTResult findMST(Graph graph) {

        if (graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());

        sortedEdges.sort((a, b) -> {
            Metrics.countComparison();
            return Integer.compare(a.weight, b.weight);
        });

        DSU dsu = new DSU(graph.getVertexCount());

        List<Edge> mstEdges = new ArrayList<>();
        long totalWeight = 0;

        for (Edge edge : sortedEdges) {
            Metrics.countEdge();

            if (dsu.union(edge.from, edge.to)) {
                mstEdges.add(edge);
                totalWeight += edge.weight;
            }

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break;
            }
        }

        return new MSTResult(
                mstEdges,
                totalWeight,
                dsu.components()
        );
    }
}