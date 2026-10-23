package org.example;

import java.util.*;

public class Kosaraju {

    public static List<List<Integer>> findSCCs(Graph graph) {

        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        int n = graph.getVertexCount();
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();

        // First DFS: finishing order
        for (int start = 0; start < n; start++) {

            if (visited[start]) {
                continue;
            }

            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{start, 0});
            visited[start] = true;

            while (!stack.isEmpty()) {
                Metrics.recordStackDepth(stack.size());

                int[] current = stack.peek();
                int vertex = current[0];
                List<Edge> neighbors = graph.neighbors(vertex);

                if (current[1] < neighbors.size()) {
                    Edge edge = neighbors.get(current[1]);
                    current[1]++;

                    Metrics.countEdge();

                    if (!visited[edge.to]) {
                        visited[edge.to] = true;
                        stack.push(new int[]{edge.to, 0});
                    }
                } else {
                    stack.pop();
                    order.add(vertex);
                }
            }
        }

        // Reverse all edges
        List<List<Integer>> reversed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            reversed.add(new ArrayList<>());
        }

        for (Edge edge : graph.getEdges()) {
            Metrics.countEdge();
            reversed.get(edge.to).add(edge.from);
        }

        Arrays.fill(visited, false);

        List<List<Integer>> components = new ArrayList<>();

        // Second DFS: find SCCs
        for (int i = order.size() - 1; i >= 0; i--) {

            int start = order.get(i);

            if (visited[start]) {
                continue;
            }

            List<Integer> component = new ArrayList<>();
            Deque<Integer> stack = new ArrayDeque<>();

            stack.push(start);
            visited[start] = true;

            while (!stack.isEmpty()) {
                Metrics.recordStackDepth(stack.size());

                int vertex = stack.pop();
                component.add(vertex);

                for (int next : reversed.get(vertex)) {
                    Metrics.countEdge();

                    if (!visited[next]) {
                        visited[next] = true;
                        stack.push(next);
                    }
                }
            }

            components.add(component);
        }

        return components;
    }
}