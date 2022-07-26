package datastructures.graph;

import java.util.*;

/**
 * An iterator that iterates over the nodes of a graph in a depth-first-search manner starting at the node start. Note
 * that the graph must not be changed during the iteration, because then te iterator might produce a wrong result.
 * @param <T> the type of the nodes in the graph
 */
public class DFSIterator<T> implements Iterator<T> {

    private final Graph<T> graph;
    private final Stack<T> stack;
    private final Set<T> visitedNodes;

    protected DFSIterator(Graph<T> graph, T start){
        Objects.requireNonNull(graph);
        Objects.requireNonNull(start);
        if(!graph.contains(start))
            throw new IllegalArgumentException(start + " is not in the graph!");

        this.graph = graph;
        stack = new Stack<>();
        stack.push(start);
        visitedNodes = new HashSet<>();
    }

    @Override
    public boolean hasNext() {
        while(!stack.isEmpty()){
            T node = stack.pop();
            if(!visitedNodes.contains(node)){
                stack.push(node);
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        while(!stack.empty()){
            T node = stack.pop();
            if(!visitedNodes.contains(node)){
                Set<T> successors = graph.getSuccessors(node);
                for(T succ : successors){
                    if(!visitedNodes.contains(succ))
                        stack.push(succ);
                }

                visitedNodes.add(node);
                return node;
            }
        }
        throw new NoSuchElementException();
    }
}
