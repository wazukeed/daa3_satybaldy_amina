package org.example;

public class DSU {

    private final int[] parent;
    private final int[] rank;
    private final int[] size;
    private int components;

    public DSU(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid size");
        }

        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        components = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("Invalid element");
        }

        return findWithDepth(x, 1);
    }

    private int findWithDepth(int x, int depth) {
        Metrics.countDsuOperation(depth);

        if (parent[x] != x) {
            parent[x] = findWithDepth(parent[x], depth + 1);
        }

        return parent[x];
    }

    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
            size[rootB] += size[rootA];

        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
            size[rootA] += size[rootB];

        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
            size[rootA] += size[rootB];
        }

        components--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int components() {
        return components;
    }

    public int componentSize(int x) {
        return size[find(x)];
    }
}