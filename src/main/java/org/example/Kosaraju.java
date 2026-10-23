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

        // First DFS: find finishing order
        for (int start = 0; start < n; start++) {

            if (visited[start]) {
                continue;
            }

            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{start, 0});
            visited[start] = true;

            while (!stack.isEmpty()) {

                int[] top = stack.peek();
                int v = top[0];

                if (top[1] < graph.neighbors(v).size()) {

                    Edge edge = graph.neighbors(v).get(top[1]);
                    top[1]++;

                    if (!visited[edge.to]) {
                        visited[edge.to] = true;
                        stack.push(new int[]{edge.to, 0});
                    }

                } else {
                    order.add(v);
                    stack.pop();
                }
            }
        }

        // Reverse all edges
        List<List<Integer>> reversed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            reversed.add(new ArrayList<>());
        }

        for (Edge edge : graph.getEdges()) {
            reversed.get(edge.to).add(edge.from);
        }

        Arrays.fill(visited, false);

        List<List<Integer>> components = new ArrayList<>();

        // Second DFS: find components
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

                int v = stack.pop();
                component.add(v);

                for (int next : reversed.get(v)) {

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