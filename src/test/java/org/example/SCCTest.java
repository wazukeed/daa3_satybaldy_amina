package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {

    @Test
    void testSeveralComponents() {
        Graph graph = new Graph(6, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 3, 1);
        graph.addEdge(4, 5, 1);

        List<List<Integer>> result = Kosaraju.findSCCs(graph);

        Set<Set<Integer>> groups = new HashSet<>();

        for (List<Integer> component : result) {
            groups.add(new HashSet<>(component));
        }

        assertEquals(3, groups.size());
        assertTrue(groups.contains(Set.of(0, 1, 2)));
        assertTrue(groups.contains(Set.of(3, 4)));
        assertTrue(groups.contains(Set.of(5)));
    }

    @Test
    void testSingleComponent() {
        Graph graph = new Graph(3, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);

        assertEquals(1, Kosaraju.findSCCs(graph).size());
    }

    @Test
    void testDAG() {
        Graph graph = new Graph(4, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);

        assertEquals(4, Kosaraju.findSCCs(graph).size());
    }

    @Test
    void testLargeGraph() {
        int n = 100000;
        Graph graph = new Graph(n, true);

        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, i + 1, 1);
        }

        assertEquals(n, Kosaraju.findSCCs(graph).size());
    }

    @Test
    void testUndirectedGraph() {
        Graph graph = new Graph(3, false);

        assertThrows(IllegalArgumentException.class,
                () -> Kosaraju.findSCCs(graph));
    }
}