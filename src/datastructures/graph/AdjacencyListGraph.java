package datastructures.graph;

import java.util.*;

/**
 * A graph implementation where edges are stored in adjacency lists. So each node has a list associated with it where all
 * its successors are stored and a list where all its predecessors are stored. Therefore this implementation is meant for rather
 * sparse graphs.
 * @param <T> the type of the nodes in the graph
 */
public class AdjacencyListGraph<T> extends Graph<T> {

    private final Map<T, Set<Edge<T>>>successorLists;
    private final Map<T, Set<Edge<T>>> predecessorLists;

    /**
     * Creates an empty graph whose edges are stored in adjacency lists
     */
    public AdjacencyListGraph(){
        super();
        successorLists = new HashMap<>();
        predecessorLists = new HashMap<>();
    }

    @Override
    protected void addNodeSub(T node) { }

    @Override
    protected void addDirectedEdgeSub(T from, T to, int weight) {
        Set<Edge<T>> successors = successorLists.getOrDefault(from, new HashSet<>());
        successors.add(new Edge<>(to, weight));
        successorLists.put(from, successors);

        Set<Edge<T>> predecessors = predecessorLists.getOrDefault(to, new HashSet<>());
        predecessors.add(new Edge<>(from, weight));
        predecessorLists.put(to, predecessors);
    }

    @Override
    protected void removeDirectedEdgeSub(T from, T to) {
        Set<Edge<T>> successors = successorLists.getOrDefault(from, new HashSet<>());
        successors.remove(new Edge<>(to));
        successorLists.put(from, successors);

        Set<Edge<T>> predecessors = predecessorLists.getOrDefault(to, new HashSet<>());
        predecessors.remove(new Edge<>(from));
        predecessorLists.put(to, predecessors);
    }

    @Override
    protected boolean containsEdgeSub(T from, T to){
        return successorLists.getOrDefault(from, new HashSet<>()).contains(new Edge<>(to));
    }

    @Override
    protected Set<T> getSuccessorsSub(T node) {
        return convert(successorLists.getOrDefault(node, new HashSet<>()));
    }

    @Override
    protected Set<T> getPredecessorsSub(T node) {
        return convert(predecessorLists.getOrDefault(node, new HashSet<>()));
    }

    @Override
    protected int getEdgeWeightSub(T from, T to) {
        for(Edge<T> edge : successorLists.getOrDefault(from, new HashSet<>())){
            if(to.equals(edge.getNode()))
                return edge.getWeight();
        }

        throw new IllegalArgumentException("There is no such edge!");
    }

    @Override
    protected void setEdgeWeightSub(T from, T to, int weight) {
        Set<Edge<T>> edges = successorLists.get(from);
        edges.remove(new Edge<>(to));
        edges.add(new Edge<>(to, weight));
        successorLists.put(from, edges);

        edges = predecessorLists.get(to);
        edges.remove(new Edge<>(from));
        edges.add(new Edge<>(from, weight));
        predecessorLists.put(to, edges);
    }

    private Set<T> convert(Set<Edge<T>> edges){
        Set<T> set = new HashSet<>();
        for(Edge<T> edge : edges)
            set.add(edge.getNode());

        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdjacencyListGraph<?> that = (AdjacencyListGraph<?>) o;
        return successorLists.equals(that.successorLists) && predecessorLists.equals(that.predecessorLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), successorLists, predecessorLists);
    }

    private static class Edge<T> {
        private T node;
        private int weight;

        public Edge(T node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        public Edge(T node){
            this.node = node;
        }

        public T getNode() {
            return node;
        }

        public void setNode(T node) {
            this.node = node;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return node.equals(edge.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node);
        }
    }
}
