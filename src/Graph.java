import java.util.Iterator;

public interface Graph<T> {
    void addEdge(T vertex1, T vertex2);
    boolean searchEdge(T vertex1, T vertex2);

    Iterator<T> iterateEdges(T vertex);
}