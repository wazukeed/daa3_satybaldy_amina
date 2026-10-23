package org.example;

import java.util.*;
import java.io.IOException;
import java.nio.file.Path;

public class Graph {

    private final int vertexCount;
    private final boolean directed;

    private final List<List<Edge>> adjacency;
    private final List<Edge> edges;

    public Graph(int vertexCount, boolean directed) {
        this.vertexCount = vertexCount;
        this.directed = directed;

        adjacency = new ArrayList<>();
        edges = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        if (from < 0 || from >= vertexCount ||
                to < 0 || to >= vertexCount) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        Edge edge = new Edge(from, to, weight);

        adjacency.get(from).add(edge);
        edges.add(edge);

        if (!directed) {
            adjacency.get(to).add(new Edge(to, from, weight));
        }
    }

    public List<Edge> neighbors(int vertex) {
        return adjacency.get(vertex);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public boolean isDirected() {
        return directed;
    }

    public static Graph readFromFile(String filePath,
                                     boolean directed) throws IOException {

        try (Scanner scanner = new Scanner(Path.of(filePath))) {

            int vertices = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            Graph graph = new Graph(vertices, directed);

            for (int i = 0; i < edgeCount; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int weight = scanner.nextInt();

                graph.addEdge(from, to, weight);
            }

            return graph;
        }
    }
}