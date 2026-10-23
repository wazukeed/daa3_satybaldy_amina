# Assignment 3 – Graph Algorithms

**Student:** Amina Satybaldy  
**Group:** SE-2517  
**Course:** Design and Analysis of Algorithms  
**University:** Astana IT University

## 1. Project Description

This project is about graph algorithms in Java. I implemented algorithms for finding minimum spanning trees, strongly connected components, topological order, and shortest paths in directed acyclic graphs.

The project uses Java 17, Maven, and JUnit 5.

## 2. Implemented Algorithms

### Graph

The `Graph` class stores vertices and weighted edges using an adjacency list. It supports directed and undirected graphs.

### Disjoint Set Union (DSU)

The `DSU` class uses path compression and union by rank. It also stores the number of connected components and their sizes.

### Minimum Spanning Tree

- **Kruskal:** Sorts edges by weight and uses DSU to avoid cycles.
- **Prim:** Uses a priority queue to select the smallest available edge.

Both algorithms can return a minimum spanning forest if the graph is disconnected.

### Strongly Connected Components

**Kosaraju** finds strongly connected components in a directed graph. It uses two DFS passes and a reversed graph. DFS is implemented without recursion.

### Topological Sorting

**Kahn** uses indegrees and a queue to find a topological order. It also detects directed cycles.

### DAG Shortest Paths

This algorithm calculates shortest paths in a directed acyclic graph using topological order. It supports negative edge weights because a DAG has no directed cycles.

## 3. Project Structure

- `src/main/java/org/example/` – Java source code
- `src/test/java/org/example/` – JUnit tests
- `data/` – Example graphs
- `results/results.csv` – Benchmark measurements
- `results/runtime.png` – Running time graph
- `results/edge_scans.png` – Edge scan graph
- `results/comparisons.png` – MST comparisons graph
- `scripts/plot_results.py` – Python script for creating graphs
- `REPORT.md` – Project report

## 4. Complexity

| Algorithm | Time Complexity |
|---|---|
| DSU operations | O(α(V)) amortized |
| Kruskal | O(E log E + E α(V)) |
| Prim (priority queue) | O(E log E) |
| Kosaraju | O(V + E) |
| Kahn | O(V + E) |
| DAG Shortest Paths | O(V + E) |

Here, V is the number of vertices and E is the number of edges.

## 5. Testing

I wrote JUnit tests for the graph structure and algorithms.

The tests include:

- Connected and disconnected graphs
- Cycle detection
- Minimum spanning tree weights
- Strongly connected components
- Negative edge weights in DAGs
- Unreachable vertices
- Large graphs and randomized checks

To run the tests, open the project in IntelliJ IDEA and run the Maven `test` lifecycle.

## 6. Benchmark

The benchmark compares five algorithms:

1. Kruskal
2. Prim
3. Kosaraju
4. Kahn
5. DAG Shortest Paths

Graph sizes: 1,000; 3,162; 10,000; 31,623; and 100,000 vertices.

For each size, the benchmark uses two edge densities: E = 3V and E = 6V.

Each experiment runs five times. The program calculates average execution time and records operation counters.

The random generator uses a fixed seed of 42 to make the experiments reproducible.

The results are saved in `results/results.csv`.

To reproduce the graphs, run:

`python3 scripts/plot_results.py`

## 7. Results

The benchmark results show that running time and the number of operations generally increase as the graphs become larger.

Kruskal and Prim solve the same MST problem, but they use different approaches. Kosaraju, Kahn, and DAG Shortest Paths solve different problems and have linear theoretical complexity.

More details and charts are included in `REPORT.md`.

## 8. Git Workflow

I developed the project using a main branch and separate feature branches:

- `feature/dsu`
- `feature/mst`
- `feature/scc`
- `feature/paths`
- `feature/metrics`

The final version is marked with the Git tag `v1.0`.