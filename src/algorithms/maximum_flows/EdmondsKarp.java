package algorithms.maximum_flows;

import datastructures.graph.Graph;

import java.util.*;

/**
 * An implementation of the MaximumFlowCalculator that uses the Edmonds-Karp algorithm to compute maximal flows in
 * DIRECTED graphs. Note that the weights of the edges in the graph are interpreted as capacities, so there must not be
 * negative edge weights. If |V| denotes the number of nodes in the graph and |E| the number of edges, then a maximum
 * flow is computed in O(|V||E|^2)
 * @param <T> the type of the nodes in the graph
 */
public class EdmondsKarp<T> extends MaximumFlowCalculator<T>{

    private final List<T> queue;
    private final Set<T> visitedNodes;


    /**
     * Creates an instance for computing maximal flows via the Edmonds-Karp algorithm
     * @param graph the graph that represents the flow network
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException if the graph contains edges with negative weights
     */
    public EdmondsKarp(Graph<T> graph){
        super(graph);
        queue = new LinkedList<>();
        visitedNodes = new HashSet<>();
    }

    @Override
    protected List<T> getPath(T from, T to) {
        Map<T, T> pre = new HashMap<>();
        queue.clear();
        visitedNodes.clear();
        queue.add(from);

        while(!queue.isEmpty()){
            T node = queue.remove(0);
            visitedNodes.add(node);
            Set<T> successors = residualGraph.getSuccessors(node);
            for(T succ : successors){
                if(!visitedNodes.contains(succ)) {
                    queue.add(succ);
                    pre.put(succ, node);
                    visitedNodes.add(succ);
                    if(succ.equals(to)){
                        queue.clear();
                        break;
                    }
                }
            }
        }

        List<T> path = new LinkedList<>();
        path.add(to);
        T current = to;
        while(pre.containsKey(current)){
            current = pre.get(current);
            path.add(0, current);
        }
        if(path.get(0).equals(from))
            return path;

        return null;
    }
}
