import java.util.*;

public class AdjacentMatrixStaticIterator<T> extends AdjacencyMatrixLocalIterator<T> {
    public AdjacentMatrixStaticIterator() {
        super();
    }

    public AdjacentMatrixStaticIterator(List<T> vertices) {
        super(vertices);
    }

    private class EdgeIterator implements Iterator<T> {
        private int i;
        private T nextVertex;
        private final List<T> index2vertex;
        private final Iterator<Integer> edges;

        EdgeIterator(int vertexIndex){
            i = 0;
            nextVertex = null;
            this.edges = matrix.get(vertexIndex).iterator();;
            this.index2vertex = vertex2index.keySet().stream().toList();
        }

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

    @Override
    public Iterator<T> iterateEdges(T vertex) {
        Integer index = vertex2index.get(vertex);

        if (index == null) {
            return Collections.emptyIterator();
        }

        return new EdgeIterator(index);
    }
}
