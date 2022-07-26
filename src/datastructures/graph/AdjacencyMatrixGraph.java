package datastructures.graph;

import java.util.*;

/**
 * A graph implementation where the edges are stored in an adjacency matrix with an initial default size of 32. Every time
 * a new node is added when the number of nodes equals the size of the adjacency matrix, the size of the adjacency matrix
 * is doubled. Note that this implementation is meant for graphs with a large number of edges.
 * @param <T> the type of the nodes in the graph
 */
public class AdjacencyMatrixGraph<T> extends Graph<T> {

    private static final int INIT_SIZE = 32;

    private Integer[][] adjacencyMatrix;
    private final Map<Integer, T> idxToNode;

    /**
     * Creates an empty graph whose edges are stored in an adjacency matrix. The adjacency matrix is initialized with an
     * initial size of 32
     */
    public AdjacencyMatrixGraph(){
        this(INIT_SIZE);
    }

    /**
     * Creates a graph whose edges are stored in an adjacency matrix. The initial size of the adjacency matrix is determined by
     * the size of the specified nodes collection. All the nodes in the collection are added to the graph.
     * @param nodes the collection of nodes to be added to the inital graph
     * @exception NullPointerException if the collection nodes is null or if one element in nodes is null
     */
    public AdjacencyMatrixGraph(Collection<T> nodes){
        this(nodes.size());
        addNodes(nodes);
    }

    /**
     * Creates an empty graph whose edges are stored in an adjacency matrix. The adjacency matrix is initialized with the
     * specified initSize
     * @param initSize the initial size of the adjacency matrix
     */
    public AdjacencyMatrixGraph(int initSize){
        super();
        idxToNode = new HashMap<>();
        adjacencyMatrix = new Integer[initSize][initSize];
    }

    @Override
    protected void addNodeSub(T node) {
        addNode(node, true);
    }

    private void addNode(T node, boolean increaseAdjacencyMatrix){
        int idx = getIdx(node);
        idxToNode.put(idx, node);

        if(increaseAdjacencyMatrix && size() >= adjacencyMatrix.length){
            int newSize = 2 * adjacencyMatrix.length;
            increaseAdjacencyMatrix(newSize);
        }
    }

    private void increaseAdjacencyMatrix(int newSize){
        Integer[][] bigAdjacencyMatrix = new Integer[newSize][newSize];

        for(int i = 0; i < adjacencyMatrix.length; i++)
            System.arraycopy(adjacencyMatrix[i], 0, bigAdjacencyMatrix[i], 0, adjacencyMatrix.length);

        adjacencyMatrix = bigAdjacencyMatrix;
    }

    @Override
    public void addDirectedEdgeSub(T from, T to, int weight) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);
        adjacencyMatrix[fromIdx][toIdx] = weight;
    }

    @Override
    public void removeDirectedEdgeSub(T from, T to) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);
        adjacencyMatrix[fromIdx][toIdx] = null;
    }

    @Override
    public Set<T> getSuccessorsSub(T node) {
        int nodeIdx = getIdx(node);
        Set<T> neighbours = new HashSet<>();
        for(int i = 0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[nodeIdx][i] != null)
                neighbours.add(idxToNode.get(i));
        }

        return neighbours;
    }

    @Override
    public Set<T> getPredecessorsSub(T node) {
        int nodeIdx = getIdx(node);
        Set<T> neighbours = new HashSet<>();
        for(int i = 0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[i][nodeIdx] != null)
                neighbours.add(idxToNode.get(i));
        }

        return neighbours;
    }

    @Override
    protected int getEdgeWeightSub(T from, T to) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);
        return adjacencyMatrix[fromIdx][toIdx];
    }

    @Override
    protected void setEdgeWeightSub(T from, T to, int weight) {
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);
        adjacencyMatrix[fromIdx][toIdx] = weight;
    }

    @Override
    protected boolean containsEdgeSub(T from, T to){
        int fromIdx = getIdx(from);
        int toIdx = getIdx(to);
        return adjacencyMatrix[fromIdx][toIdx] != null;
    }

    private int getIdx(T node){
        return getId(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdjacencyMatrixGraph<?> that = (AdjacencyMatrixGraph<?>) o;
        return Arrays.equals(adjacencyMatrix, that.adjacencyMatrix) && idxToNode.equals(that.idxToNode);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), idxToNode);
        result = 31 * result + Arrays.hashCode(adjacencyMatrix);
        return result;
    }
}
