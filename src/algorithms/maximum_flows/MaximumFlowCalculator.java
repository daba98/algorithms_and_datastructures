package algorithms.maximum_flows;

import datastructures.graph.AdjacencyListGraph;
import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;

import java.util.List;
import java.util.Objects;

/**
 * Computes the maximum flow in a given DIRECTED graph. Note that the weights of the edges in the graph are interpreted as
 * capacities, so there must not be negative edge weights.
 * @param <T> the type of the nodes in the graph
 */
public abstract class MaximumFlowCalculator<T> {

    private final Graph<T> graph;
    protected Graph<T> residualGraph;


    public MaximumFlowCalculator(Graph<T> graph){
        Objects.requireNonNull(graph);
        this.graph = graph;
        buildResidualGraph(graph);
    }

    private void buildResidualGraph(Graph<T> graph){
        if(graph instanceof AdjacencyMatrixGraph)
            residualGraph = new AdjacencyMatrixGraph<>(graph.getNodes());
        else{
            residualGraph = new AdjacencyListGraph<>();
            residualGraph.addNodes(graph.getNodes());
        }

        for(T node : graph.getNodes()){
            for(T succ : graph.getSuccessors(node)){
                int edgeWeight = graph.getEdgeWeight(node, succ);
                if(edgeWeight < 0)
                    throw new IllegalArgumentException("There must not be edges with negative weights in the graph!");
                if(edgeWeight > 0)
                    residualGraph.addDirectedEdge(node, succ, edgeWeight);
            }
        }
    }

    protected abstract List<T> getPath(T from, T to);

    /**
     * Returns the maximum amount of flow that can flow from source to target. Note that the weights of the edges in the
     * graph are interpreted as capacities.
     * @param source the source node of the flow
     * @param target the target node of the flow
     * @return Returns the maximum amount of flow that can flow from source to target
     * @exception NullPointerException if source or target is null
     * @exception IllegalArgumentException if source or target is not in the graph or if source and target are the same node
     */
    public long getMaximumFlow(T source, T target){
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        if(!graph.contains(source))
            throw new IllegalArgumentException(source + " is not in the graph!");
        if(!graph.contains(target))
            throw new IllegalArgumentException(target + " is not in the graph!");
        if(source.equals(target))
            throw new IllegalArgumentException("Source and target must not be the same node!");

        buildResidualGraph(graph);
        List<T> path = getPath(source, target);
        while(path != null){
            int minCapacity = Integer.MAX_VALUE;
            T current = path.get(0);
            for(int i = 1; i < path.size(); i++){
                T next = path.get(i);
                int capacity = getResidualCapacity(current, next);
                if(capacity < minCapacity)
                    minCapacity = capacity;
                current = next;
            }

            current = path.get(0);
            for(int i = 1; i < path.size(); i++){
                T next = path.get(i);
                addFlow(current, next, minCapacity);
                current = next;
            }

            path = getPath(source, target);
        }

        long flow = 0;
        for(T succ : graph.getSuccessors(source))
            flow += getFlow(source, succ);
        return flow;
    }

    private int getFlow(T from, T to){
        if(!residualGraph.containsEdge(to, from))
            return 0;
        else
            return residualGraph.getEdgeWeight(to, from);
    }

    private int getResidualCapacity(T from, T to){
        if(!residualGraph.containsEdge(to, from))
            return graph.getEdgeWeight(from, to);
        else
            return graph.getEdgeWeight(from, to) - residualGraph.getEdgeWeight(to, from);
    }

    private void addFlow(T from, T to, int flow){
        if(graph.containsEdge(from, to)){
            if(!residualGraph.containsEdge(to, from)) {
                residualGraph.addDirectedEdge(to, from, flow);
            }
            else {
                int newWeight = residualGraph.getEdgeWeight(to, from) + flow;
                if(newWeight == 0)
                    residualGraph.removeDirectedEdge(to, from);
                else
                    residualGraph.setEdgeWeight(to, from, newWeight);
            }

            if(getResidualCapacity(from, to) <= 0)
                residualGraph.removeDirectedEdge(from, to);
        }
        else{
            if(!residualGraph.containsEdge(from, to))
                residualGraph.addDirectedEdge(from, to, -flow);
            else {
                int newWeight = residualGraph.getEdgeWeight(from, to) - flow;
                if(newWeight <= 0)
                    residualGraph.removeDirectedEdge(from, to);
                else
                    residualGraph.setEdgeWeight(from, to, newWeight);
            }
        }

    }
}
