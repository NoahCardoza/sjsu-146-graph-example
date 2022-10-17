import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyList<T> implements Graph<T> {
    HashMap<T, HashSet<T>> vertexes;

    AdjacencyList() {
        vertexes = new HashMap<>();
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        if (!vertexes.containsKey(vertex1)) {
            vertexes.put(vertex1, new HashSet<>());
        }
        vertexes.get(vertex1).add(vertex2);

        if (!vertexes.containsKey(vertex2)) {
            vertexes.put(vertex2, new HashSet<>());
        }
        vertexes.get(vertex2).add(vertex1);
    }

    @Override
    public boolean searchEdge(T vertex1, T vertex2) {
        HashSet<T> edges = vertexes.get(vertex1);
        if (edges == null) {
            return false;
        }
        return edges.contains(vertex2);
    }

    @Override
    public Iterator<T> iterateEdges(T vertex) {
        HashSet<T> edges = vertexes.get(vertex);
        if (edges == null) {
            return Collections.emptyIterator();
        }

        return edges.iterator();
    }

    @Override
    public String toString() {
        return vertexes.entrySet().stream().map(
                edge -> String.format("%s: %s", edge.getKey(), edge.getValue().stream().map(Object::toString).collect(Collectors.joining(", ")))
                ).collect(Collectors.joining(System.lineSeparator()));
    }
}