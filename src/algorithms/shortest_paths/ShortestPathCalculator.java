package algorithms.shortest_paths;

import datastructures.graph.Graph;

import java.util.List;
import java.util.Objects;

/**
 * Computes the shortest paths in a given graph. Note that for performance reasons, some internal
 * state is stored. This means that you should not change the graph between calls in any way. Otherwise the result
 * might not reflect the changes made in the graph.
 * @param <T> the type of the nodes in the graph
 */
public abstract class ShortestPathCalculator<T> {

    protected Graph<T> graph;

    public ShortestPathCalculator(Graph<T> graph){
        Objects.requireNonNull(graph);
        this.graph = graph;
    }

    /**
     * Returns the sum of the weights along the shortest path from node from to node to or null if no path exists
     * @param from the starting node
     * @param to the target node
     * @return Returns the sum of the weights along the shortest path from node from to node to or null if no path exists
     * @exception NullPointerException if from or to is null
     * @exception IllegalArgumentException if from or to are not contained in the graph
     */
    public final Long getWeightOfShortestPath(T from, T to){
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if(!graph.contains(from))
            throw new IllegalArgumentException(from + " is not contained in the graph!");
        if(!graph.contains(to))
            throw new IllegalArgumentException(to + " is not contained in the graph!");


        return getWeightOfShortestPathSub(from, to);
    }
    protected abstract Long getWeightOfShortestPathSub(T from, T to);

    /**
     * Returns the shortest path from node from to node to or null if no path exists
     * @param from the starting node
     * @param to the target node
     * @return Returns the shortest path from node from to node to or null if no path exists
     * @exception NullPointerException if from or to is null
     * @exception IllegalArgumentException if from or to are not contained in the graph
     */
    public final List<T> getShortestPath(T from, T to){
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if(!graph.contains(from))
            throw new IllegalArgumentException(from + " is not contained in the graph!");
        if(!graph.contains(to))
            throw new IllegalArgumentException(to + " is not contained in the graph!");


        return getShortestPathSub(from, to);
    }

    protected abstract List<T> getShortestPathSub(T from, T to);
}
