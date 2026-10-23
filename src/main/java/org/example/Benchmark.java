package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class Benchmark {

    private static final int[] SIZES = {
            1000, 3162, 10000, 31623, 100000
    };

    private static final int[] FACTORS = {3, 6};
    private static final int REPEATS = 5;

    public static void main(String[] args) throws IOException {

        Path folder = Path.of("results");
        Files.createDirectories(folder);

        Path file = folder.resolve("results.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {

            writer.write(
                    "algorithm,vertices,edges,factor,repeats," +
                            "avg_time_ms,dsu_ops,edge_scans,comparisons,max_depth"
            );
            writer.newLine();

            for (int n : SIZES) {
                for (int factor : FACTORS) {

                    int edges = n * factor;
                    long seed = 42;

                    System.out.println("Testing n=" + n +
                            ", edges=" + edges);

                    Graph undirected =
                            GraphGenerator.connectedUndirected(n, edges, seed);

                    Graph directed =
                            GraphGenerator.directedGraph(n, edges, seed);

                    Graph dag =
                            GraphGenerator.dagGraph(n, edges, seed);

                    measure(writer, "Kruskal", n, edges, factor,
                            () -> Kruskal.findMST(undirected));

                    measure(writer, "Prim", n, edges, factor,
                            () -> Prim.findMST(undirected));

                    measure(writer, "Kosaraju", n, edges, factor,
                            () -> Kosaraju.findSCCs(directed));

                    measure(writer, "Kahn", n, edges, factor,
                            () -> Kahn.topologicalSort(dag));

                    measure(writer, "DAG Shortest Path", n, edges, factor,
                            () -> DagShortestPath.shortestPaths(dag, 0));
                }
            }
        }

        System.out.println("Benchmark finished!");
        System.out.println("Results saved to results/results.csv");
    }

    private static void measure(
            BufferedWriter writer,
            String algorithm,
            int n,
            int edges,
            int factor,
            Runnable task
    ) throws IOException {

        long totalTime = 0;
        Metrics lastMetrics = new Metrics();

        for (int i = 0; i < REPEATS; i++) {

            Metrics metrics = new Metrics();
            Metrics.start(metrics);

            try {
                long start = System.nanoTime();

                task.run();

                long end = System.nanoTime();
                totalTime += end - start;

            } finally {
                Metrics.stop();
            }

            lastMetrics = metrics;
        }

        double averageMs = totalTime / (REPEATS * 1_000_000.0);

        writer.write(
                algorithm + "," +
                        n + "," +
                        edges + "," +
                        factor + "," +
                        REPEATS + "," +
                        String.format(Locale.US, "%.4f", averageMs) + "," +
                        lastMetrics.dsuOps + "," +
                        lastMetrics.edgeScans + "," +
                        lastMetrics.comparisons + "," +
                        lastMetrics.maxDepth
        );

        writer.newLine();
    }
}