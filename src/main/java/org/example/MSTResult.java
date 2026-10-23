package org.example;

import java.util.List;

public class MSTResult {

    private final List<Edge> edges;
    private final long totalWeight;
    private final int components;

    public MSTResult(List<Edge> edges, long totalWeight, int components) {
        this.edges = List.copyOf(edges);
        this.totalWeight = totalWeight;
        this.components = components;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public int getComponents() {
        return components;
    }

    public boolean isConnected() {
        return components <= 1;
    }
}