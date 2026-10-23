package org.example;

public class Metrics {

    public long dsuOps = 0;
    public long edgeScans = 0;
    public long comparisons = 0;
    public int maxDepth = 0;

    private static final ThreadLocal<Metrics> ACTIVE = new ThreadLocal<>();

    public static void start(Metrics metrics) {
        ACTIVE.set(metrics);
    }

    public static void stop() {
        ACTIVE.remove();
    }

    public static void countEdge() {
        Metrics m = ACTIVE.get();
        if (m != null) {
            m.edgeScans++;
        }
    }

    public static void countComparison() {
        Metrics m = ACTIVE.get();
        if (m != null) {
            m.comparisons++;
        }
    }

    public static void countDsuOperation(int depth) {
        Metrics m = ACTIVE.get();
        if (m != null) {
            m.dsuOps++;
            m.recordDepth(depth);
        }
    }

    public static void recordStackDepth(int depth) {
        Metrics m = ACTIVE.get();
        if (m != null) {
            m.recordDepth(depth);
        }
    }

    public void reset() {
        dsuOps = 0;
        edgeScans = 0;
        comparisons = 0;
        maxDepth = 0;
    }

    public void recordDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    @Override
    public String toString() {
        return "DSU operations: " + dsuOps +
                ", Edge scans: " + edgeScans +
                ", Comparisons: " + comparisons +
                ", Max depth: " + maxDepth;
    }
}