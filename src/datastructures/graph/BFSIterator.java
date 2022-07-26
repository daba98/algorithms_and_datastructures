package datastructures.graph;

import java.util.*;

/**
 * An iterator that iterates over the nodes of a graph in a breadth-first-search manner starting at the node start. Note
 * that the graph must not be changed during the iteration, because then te iterator might produce a wrong result.
 * @param <T> the type of the nodes in the graph
 */
public class BFSIterator<T> implements Iterator<T> {

    private final Graph<T> graph;
    private final List<T> queue;
    private final Set<T> visitedNodes;

    protected BFSIterator(Graph<T> graph, T start){
        Objects.requireNonNull(graph);
        Objects.requireNonNull(start);
        if(!graph.contains(start))
            throw new IllegalArgumentException(start + " is not in the graph!");

        this.graph = graph;
        queue = new LinkedList<>();
        queue.add(start);
        visitedNodes = new HashSet<>();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        if(!queue.isEmpty()){
            T node = queue.remove(0);
            visitedNodes.add(node);
            Set<T> successors = graph.getSuccessors(node);
            for(T succ : successors){
                if(!visitedNodes.contains(succ)) {
                    queue.add(succ);
                    visitedNodes.add(succ);
                }
            }

            return node;
        }
        throw new NoSuchElementException();
    }
}
