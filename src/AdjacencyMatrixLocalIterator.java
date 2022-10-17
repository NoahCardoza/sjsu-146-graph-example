import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyMatrixLocalIterator<T> implements Graph<T> {
    private int size;
    protected final LinkedHashMap<T, Integer> vertex2index;
    protected final ArrayList<ArrayList<Integer>> matrix;

    public AdjacencyMatrixLocalIterator(){
        size = 0;
        vertex2index = new LinkedHashMap<>();
        matrix = new ArrayList<>();
    }

    private static ArrayList<Integer> initZeroArray(int size) {
        ArrayList<Integer> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(0);
        }
        return array;
    }

    public AdjacencyMatrixLocalIterator(List<T> vertices) {
        this();
        int numberOfVertices = vertices.size();
        for (T vertex : vertices) {
            matrix.add(initZeroArray(numberOfVertices));
            vertex2index.put(vertex, size++);
        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        upsertVertex(vertex1);
        upsertVertex(vertex2);

        int v1i = vertex2index.get(vertex1);
        int v2i = vertex2index.get(vertex2);

        matrix.get(v1i).set(v2i, 1);
        matrix.get(v2i).set(v1i, 1);
    }

    private void upsertVertex(T vertex) {
        if (!vertex2index.containsKey(vertex)) {
            vertex2index.put(vertex, size++);
            matrix.add(initZeroArray(size));
            for (int i = 0; i < size - 1; i++) {
                matrix.get(i).add(0);
            }
        }
    }

    @Override
    public boolean searchEdge(T vertex1, T vertex2) {
        Integer parentIndex = vertex2index.get(vertex1);
        if (parentIndex == null) {
            return false;
        }
        Integer childIndex = vertex2index.get(vertex2);
        if (childIndex == null) {
            return false;
        }

        return matrix.get(parentIndex).get(childIndex) > 0;
    }

    @Override
    public Iterator<T> iterateEdges(T vertex) {
        Integer index = vertex2index.get(vertex);

        if (index == null) {
            return Collections.emptyIterator();
        }

        Iterator<Integer> edges = matrix.get(index).iterator();
        return new Iterator<>() {
            private int i = 0;
            private final List<T> index2vertex = vertex2index.keySet().stream().toList();
            private T nextVertex = null;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (!edges.hasNext()) {
                        return false;
                    }

                    if (edges.next() > 0) {
                        nextVertex = index2vertex.get(i++);
                        return true;
                    }
                    i++;
                }
            }

            @Override
            public T next() {
                if (nextVertex == null) {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                T tmpVertex = nextVertex;
                nextVertex = null;
                return tmpVertex;
            }
        };
    }


    @Override
    public String toString() {
        Iterator<T> labels = vertex2index.keySet().iterator();
        return matrix.stream().map(
                row -> String.format(
                        "%s: %s",
                        labels.next(),
                        row.stream().map(Object::toString).collect(Collectors.joining(" "))
                )).collect(Collectors.joining(System.lineSeparator()));
    }
}
