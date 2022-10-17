import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    public void graph() {
        test(new AdjacencyList<>());
        test(new AdjacencyMatrixLocalIterator<>());
        test(new AdjacencyMatrixLocalIterator<>(Stream.of(
        "PHX",
                "BKK",
                "OKC",
                "JFK",
                "LAX",
                "MEX",
                "EZE",
                "HEL",
                "LOS",
                "LAP",
                "LIM"
        ).toList()));
        test(new AdjacentMatrixStaticIterator<>());
        test(new AdjacentMatrixStaticIterator<>(Stream.of(
                "PHX",
                "BKK",
                "OKC",
                "JFK",
                "LAX",
                "MEX",
                "EZE",
                "HEL",
                "LOS",
                "LAP",
                "LIM"
        ).toList()));
    }

    private void test(Graph<String> graph) {
        graph.addEdge("PHX", "LAX");
        graph.addEdge("PHX", "JFK");
        graph.addEdge("JFK", "OKC");
        graph.addEdge("JFK", "HEL");
        graph.addEdge("JFK", "LOS");
        graph.addEdge("MEX", "LAX");
        graph.addEdge("MEX", "BKK");
        graph.addEdge("MEX", "LIM");
        graph.addEdge("MEX", "EZE");
        graph.addEdge("LIM", "BKK");

        assertTrue(graph.searchEdge("MEX", "LAX"));
        assertTrue(graph.searchEdge("LAX", "MEX"));

        assertFalse(graph.searchEdge("JFK", "LAX"));

        assertFalse(graph.searchEdge("LOL", "LOL"));

        Iterator<String> iter = graph.iterateEdges("LAX");

        HashSet<String> edges = new HashSet<>();
        edges.add(iter.next());
        edges.add(iter.next());

        assertTrue(edges.contains("PHX"));
        assertTrue(edges.contains("MEX"));

        assertFalse(graph.iterateEdges("LAP").hasNext());

        System.out.println(graph);
    }
}