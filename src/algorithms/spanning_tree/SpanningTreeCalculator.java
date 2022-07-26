package algorithms.spanning_tree;

import datastructures.graph.Graph;

import java.util.Objects;

/**
 * Computes maximal and minimal spanning trees of UNDIRECTED graphs. Note that for performance reasons, some internal
 * state is stored. This means that you should not change the graph between calls in any way. Otherwise the result
 * might not reflect the changes made in the graph.
 * @param <T> the type of the nodes in the graph
 */
public abstract class SpanningTreeCalculator<T> {

    protected Graph<T> graph;
    protected Graph<T> spanningTree;
    protected long totalWeight = 0;
    protected Boolean min = null;

    public SpanningTreeCalculator(Graph<T> graph){
        Objects.requireNonNull(graph);
        this.graph = graph;
    }

    protected abstract void computeSpanningTree(boolean isMinimumSpanningTree);

    /**
     * Returns the sum of the weights of the edges in a minimal spanning tree
     * @return Returns the sum of the weights of the edges in a minimal spanning tree
     */
    public final long getWeightOfMinimumSpanningTree() {
        if (min == null || !min) {
            min = true;
            computeSpanningTree(true);
        }
        return totalWeight;
    }

    /**
     * Returns a minimal spanning tree
     * @return Returns a minimal spanning tree
     */
    public final Graph<T> getMinimumSpanningTree() {
        if (min == null || !min) {
            min = true;
            computeSpanningTree(true);
        }
        return spanningTree;
    }

    /**
     * Returns the sum of the weights of the edges in a maximal spanning tree
     * @return Returns the sum of the weights of the edges in a maximal spanning tree
     */
    public final long getWeightOfMaximumSpanningTree() {
        if (min == null || min) {
            min = false;
            computeSpanningTree(false);
        }
        return totalWeight;
    }

    /**
     * Returns a maximal spanning tree
     * @return Returns a maximal spanning tree
     */
    public final Graph<T> getMaximumSpanningTree() {
        if (min == null || min) {
            min = false;
            computeSpanningTree(false);
        }
        return spanningTree;
    }
}
