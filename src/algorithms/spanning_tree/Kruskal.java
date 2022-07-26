package algorithms.spanning_tree;

import datastructures.UnionFind;
import datastructures.graph.AdjacencyListGraph;
import datastructures.graph.Graph;

import java.util.*;

/**
 * This class can compute maximum- and minimum spanning trees for UNDIRECTED graphs. It treats directed graphs as if the
 * edges were undirected. Let |E| denote the number of edges in the graph, then the computation of a spanning tree takes
 * time in O(|E|log(|E|)).
 * @param <T> the type of the nodes in the graph
 */
public class Kruskal<T> extends SpanningTreeCalculator<T> {

    /**
     * Creates an instance to compute minimum and maximum spanning trees of the graph via Kruskal's algorithm. Note that
     * the graph must be UNDIRECTED.
     * @param graph the graph the spanning tree is computed for
     * @exception NullPointerException if the graph is null
     */
    public Kruskal(Graph<T> graph){
        super(graph);
    }

    protected void computeSpanningTree(boolean isMinimumSpanningTree){
        if(isMinimumSpanningTree)
            computeSpanningTree(new MinComparator<>());
        else
            computeSpanningTree(new MaxComparator<>());
    }

    private void computeSpanningTree(Comparator<Edge<T>> comp){
        List<Edge<T>> edges = getAllEdges();
        edges.sort(comp);

        totalWeight = 0;
        spanningTree = new AdjacencyListGraph<>();
        UnionFind<Integer> unionFind = new UnionFind<>();
        while(!edges.isEmpty()){
            Edge<T> edge = edges.remove(0);
            if(!unionFind.find(edge.fromId).equals(unionFind.find(edge.toId))){
                unionFind.union(edge.fromId, edge.toId);
                spanningTree.addNode(edge.from);
                spanningTree.addNode(edge.to);
                spanningTree.addUndirectedEdge(edge.from, edge.to, edge.weight);
                totalWeight += edge.weight;
            }
        }
    }

    private List<Edge<T>> getAllEdges(){
        List<Edge<T>> edges = new LinkedList<>();
        for(T node : graph.getNodes()){
            for(T succ : graph.getSuccessors(node)){
                edges.add(createEdge(node, succ));
            }
        }
        return edges;
    }

    private Edge<T> createEdge(T from, T to){
        return new Edge<>(from, graph.getId(from), to, graph.getId(to), graph.getEdgeWeight(from, to));
    }


    private static class Edge<T>{
        public T from;
        public int fromId;
        public T to;
        public int toId;
        public int weight;

        public Edge(T from, int fromId, T to, int toId, int weight) {
            this.from = from;
            this.fromId = fromId;
            this.to = to;
            this.toId = toId;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return fromId == edge.fromId &&
                    toId == edge.toId &&
                    weight == edge.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromId, toId, weight);
        }
    }


    private static class MinComparator<T> implements Comparator<Edge<T>>{

        @Override
        public int compare(Edge<T> edge1, Edge<T> edge2) {
            if(edge1.weight < edge2.weight)
                return -1;
            if(edge1.weight > edge2.weight)
                return 1;
            if(edge1.fromId < edge2.fromId)
                return -1;
            if(edge1.fromId > edge2.fromId)
                return 1;
            return Integer.compare(edge1.toId, edge2.toId);
        }
    }

    private static class MaxComparator<T> implements Comparator<Edge<T>>{

        @Override
        public int compare(Edge<T> edge1, Edge<T> edge2) {
            if(edge1.weight < edge2.weight)
                return 1;
            if(edge1.weight > edge2.weight)
                return -1;
            if(edge1.fromId < edge2.fromId)
                return -1;
            if(edge1.fromId > edge2.fromId)
                return 1;
            return Integer.compare(edge1.toId, edge2.toId);
        }
    }
}
