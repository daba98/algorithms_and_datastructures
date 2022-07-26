package datastructures.graph;

import java.util.*;

/**
 * A datastructure to build up and store a graph. Amongst operations to add and remove nodes and edges, there are also
 * methods to get the successors or predecessors of a node,
 * @param <T> the type of the nodes in the graph
 */
public abstract class Graph<T> {

    private static final int DEFAULT_WEIGHT = 1;

    private Integer nextId;
    private final Map<T, Integer> nodes;

    /**
     * Creates an empty graph.
     */
    public Graph(){
        nodes = new HashMap<>();
        nextId = 0;
    }

    /**
     * Returns the set of nodes in the graph
     * @return Returns the set of nodes in the graph
     */
    public Set<T> getNodes(){return nodes.keySet();}

    /**
     * Returns the number of nodes in the graph.
     * @return Returns the number of nodes in the graph.
     */
    public int size(){return nodes.size();}

    /**
     * Returns true if the node is in the graph
     * @param node the node whose presence is to be checked
     * @return Returns true if the node is in the graph
     * @exception NullPointerException if the node is null
     */
    public boolean contains(T node){
        Objects.requireNonNull(node);
        return nodes.containsKey(node);}

    /**
     * Returns a unique ID associated with the node
     * @param node the node whose ID is requested
     * @return Returns a unique ID associated with the node
     * @exception NullPointerException if the node is null
     * @exception IllegalArgumentException if the node is not in the graph
     */
    public int getId(T node){
        Objects.requireNonNull(node);
        Integer id = nodes.get(node);
        if(id != null)
            return id;
        throw new IllegalArgumentException("The Graph does not contain the node " + node);
    }

    /**
     * Adds the node to the graph if it is not present yet
     * @param node the node to be added to the graph
     * @exception NullPointerException if the node is null
     */
    public final void addNode(T node){
        Objects.requireNonNull(node);
        if(!nodes.containsKey(node))
            nodes.put(node, nextId++);

        addNodeSub(node);
    }

    /**
     * Adds all nodes to the graph
     * @param nodes the collection of nodes to be added to the graph
     * @exception NullPointerException if the nodes is null or an element in nodes is null
     */
    public final void addNodes(Collection<T> nodes){
        Objects.requireNonNull(nodes);
        for(T node : nodes){
            addNode(node);
        }
    }


    protected abstract void addNodeSub(T node);

    /**
     * Adds a directed edge between from node from to node to to the graph it is not present yet
     * @param from the starting node the edge is to be connected to
     * @param to the ending node the edge is to be connected to
     * @exception NullPointerException if the nodes from or to are null
     * @exception IllegalArgumentException if the nodes from or to are not in the graph yet
     */
    public final void addDirectedEdge(T from, T to){
        addDirectedEdge(from, to , DEFAULT_WEIGHT);
    }

    /**
     * Adds an undirected edge between node1 and node2 to the graph it is not present yet
     * @param node1 the first of the two nodes the edge is to be connected to
     * @param node2 the second of the two nodes the edge is to be connected to
     * @exception NullPointerException if node1 or node2 is null
     * @exception IllegalArgumentException if node1 or node2 is not in the graph yet
     */
    public final void addUndirectedEdge(T node1, T node2){
        addUndirectedEdge(node1, node2, DEFAULT_WEIGHT);
    }

    /**
     * Adds an undirected edge from node from to node to with the specified weight to the graph. If there already is an
     * undirected or directed edge between the two nodes, then the weight is overwritten by the new specified weight.
     * @param from the starting node of the edge
     * @param to the ending node of the edge
     * @param weight the weight of the edge that is to be added
     * @exception NullPointerException if from or to is null
     * @exception IllegalArgumentException if from or to is not in the graph yet
     */
    public final void addDirectedEdge(T from, T to, int weight){
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if(!contains(from))
            throw new IllegalArgumentException(from + " is not in the graph!");
        if(!contains(to))
            throw new IllegalArgumentException(to + " is not in the graph!");

        addDirectedEdgeSub(from, to, weight);
    }

    protected abstract void addDirectedEdgeSub(T from, T to, int weight);

    /**
     * Adds an undirected edge between node1 and node2 with the specified weight to the graph. If there already is an
     * undirected or directed edge between the two nodes, then the weight is overwritten by the new specified weight.
     * @param node1 the first of the two nodes the edge is to be connected to
     * @param node2 the second of the two nodes the edge is to be connected to
     * @param weight the weight of the edge that is to be added
     * @exception NullPointerException if node1 or node2 is null
     * @exception IllegalArgumentException if node1 or node2 is not in the graph yet
     */
    public void addUndirectedEdge(T node1, T node2, int weight){
        addDirectedEdge(node1, node2, weight);
        addDirectedEdge(node2, node1, weight);
    }

    /**
     * Removes the directed edge starting at node from and ending at node to from the graph if it is present.
     * Note that if the from node is equal to the to node then the whole edge is removed regardless of whether it was
     * added as an undirected or a directed edge
     * @param from the starting node of the edge to be deleted
     * @param to the ending node of the edge to be deleted
     * @exception NullPointerException if from or to are null
     * @exception IllegalArgumentException if from or to are not in the graph yet
     */
    public final void removeDirectedEdge(T from, T to){
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if(!contains(from))
            throw new IllegalArgumentException(from + " is not in the graph!");
        if(!contains(to))
            throw new IllegalArgumentException(to + " is not in the graph!");

        removeDirectedEdgeSub(from, to);
    }

    protected abstract void removeDirectedEdgeSub(T from, T to);

    /**
     * Removes all edges between node1 and node2
     * @param node1 the first node of the edges to be deleted
     * @param node2 the second node of the edges to be deleted
     * @exception NullPointerException if the node1 or node2 is null
     */
    public final void removeUndirectedEdge(T node1, T node2){
        Objects.requireNonNull(node1);
        Objects.requireNonNull(node2);
        removeDirectedEdge(node1, node2);
        removeDirectedEdge(node2, node1);
    }

    /**
     * Returns the set of successors of the specified node
     * @param node the node whose successors are to be returned
     * @return Returns the set of successors of the specified node
     * @exception NullPointerException if node is null
     * @exception IllegalArgumentException if node is not in the graph
     */
    public final Set<T> getSuccessors(T node){
        Objects.requireNonNull(node);
        if(!contains(node))
            throw new IllegalArgumentException(node + " is not in the graph!");

        return getSuccessorsSub(node);
    }

    protected abstract Set<T> getSuccessorsSub(T node);

    /**
     * Returns the set of predecessors of the specified node
     * @param node the node whose predecessors are to be returned
     * @return Returns the set of predecessors of the specified node
     * @exception NullPointerException if node is null
     * @exception IllegalArgumentException if node is not in the graph
     */
    public final Set<T> getPredecessors(T node){
        Objects.requireNonNull(node);
        if(!contains(node))
            throw new IllegalArgumentException(node + " is not in the graph!");

        return getPredecessorsSub(node);
    }

    protected abstract Set<T> getPredecessorsSub(T node);

    /**
     * Returns the number of nodes that the node is connected to via an incoming edge
     * @param node the node whose in-degree is to be returned
     * @return Returns the number of nodes that the node is connected to via an incoming edge
     * @exception NullPointerException if node is null
     * @exception IllegalArgumentException if node is not in the graph
     */
    public final int inDegree(T node){
        return getPredecessors(node).size();
    }

    /**
     * Returns the number of nodes that the node is connected to via an outgoing edge
     * @param node the node whose out-degree is to be returned
     * @return Returns the number of nodes that the node is connected to via an outgoing edge
     * @exception NullPointerException if node is null
     * @exception IllegalArgumentException if node is not in the graph
     */
    public final int outDegree(T node){
        return getSuccessors(node).size();
    }

    /**
     * Returns the weight of the edge from the node from to the node to or the default value 1 if the graph is not weighted
     * @param from the starting node of the edge whose weight is returned
     * @param to the ending node of the edge whose weight is returned
     * @return Returns the weight of the edge from the node from to the node to
     * @exception NullPointerException if from or to are null
     * @exception IllegalArgumentException if from or to are not in the graph or if there is no edge from node from to node to
     */
    public final int getEdgeWeight(T from, T to){
        if(!containsEdge(from, to))
            throw new IllegalArgumentException("There is no edge from " + from + " to " + to);

        return getEdgeWeightSub(from, to);
    }

    protected abstract int getEdgeWeightSub(T from, T to);

    /**
     * Sets the weight of the edge from the node from to the node to to the new specified weight
     * @param from the starting node of the edge whose weight is set
     * @param to the ending node of the edge whose weight is set
     * @exception NullPointerException if from or to are null
     * @exception IllegalArgumentException if from or to are not in the graph or if there is no edge from node from to node to
     */
    public final void setEdgeWeight(T from, T to, int weight){
        if(!containsEdge(from, to))
            throw new IllegalArgumentException("There is no edge from " + from + " to " + to);

        setEdgeWeightSub(from, to, weight);
    }

    protected abstract void setEdgeWeightSub(T from, T to, int weight);

    /**
     * Returns true if the graph contains an edge from the node from to the node to
     * @param from the starting node of the edge whose presence is to be checked
     * @param to the ending node of the edge whose presence is to be checked
     * @return Returns true if the graph contains an edge from the node from to the node to
     * @exception NullPointerException if from or to are null
     * @exception IllegalArgumentException if from or to are not in the graph
     */
    public final boolean containsEdge(T from, T to){
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        if(!contains(from))
            throw new IllegalArgumentException(from + " is not in the graph!");
        if(!contains(to))
            throw new IllegalArgumentException(to + " is not in the graph!");

        return containsEdgeSub(from, to);
    }

    protected abstract boolean containsEdgeSub(T from, T to);

    /**
     * Returns an iterator that iterates over the nodes in a depth-first-search manner starting at the node start
     * @param start the node the depth-first-search is to be started
     * @return Returns an iterator that iterates over the nodes in a depth-first-search manner starting at the node start
     * @exception NullPointerException if start is null
     * @exception IllegalArgumentException if start is not in the graph
     */
    public DFSIterator<T> getDFSIterator(T start){
        return new DFSIterator<>(this, start);
    }

    /**
     * Returns an iterator that iterates over the nodes in a breadth-first-search manner starting at the node start
     * @param start the node the breadth-first-search is to be started
     * @return Returns an iterator that iterates over the nodes in a breadth-first-search manner starting at the node start
     * @exception NullPointerException if start is null
     * @exception IllegalArgumentException if start is not in the graph
     */
    public BFSIterator<T> getBFSIterator(T start){
        return new BFSIterator<>(this, start);
    }

    /**
     * Returns an iterator that iterates over the nodes in the graph in their topological sort order if the graph is acyclic
     * @return Returns an iterator that iterates over the nodes in the graph in their topological sort order if the graph is acyclic
     * @exception IllegalArgumentException if the graph is not acyclic
     */
    public TopologicalSortIterator<T> getTopologicalSortIterator(){
        return new TopologicalSortIterator<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?> graph = (Graph<?>) o;
        return nextId.equals(graph.nextId) && nodes.equals(graph.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextId, nodes);
    }
}
