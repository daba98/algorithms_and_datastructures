package algorithms.maximum_flows;

import datastructures.graph.Graph;

import java.util.*;

/**
 * An implementation of the MaximumFlowCalculator that uses the Ford-Fulkerson algorithm to compute maximal flows in
 * DIRECTED graphs. Note that the weights of the edges in the graph are interpreted as capacities, so there must not be
 * negative edge weights. If |E| denotes the number of edges in the graph and F is the maximal flow, then the maximal
 * flow is computed in O(|E|F)
 * @param <T> the type of the nodes in the graph
 */
public class FordFulkerson<T> extends MaximumFlowCalculator<T> {

    private final Stack<T> stack;
    private final Set<T> visitedNodes;

    /**
     * Creates an instance for computing maximal flows via the Ford-Fulkerson algorithm
     * @param graph the graph that represents the flow network
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException if the graph contains edges with negative weights
     */
    public FordFulkerson(Graph<T> graph){
        super(graph);
        stack = new Stack<>();
        visitedNodes = new HashSet<>();
    }

    @Override
    protected List<T> getPath(T from, T to) {
        Map<T, T> pre = new HashMap<>();
        stack.clear();
        visitedNodes.clear();
        stack.push(from);

        while(!stack.empty()){
            T node = stack.pop();
            if(!visitedNodes.contains(node)){
                for(T succ : residualGraph.getSuccessors(node)){
                    if(!visitedNodes.contains(succ)) {
                        stack.push(succ);
                        pre.put(succ, node);
                        if(succ.equals(to)){
                            stack.clear();
                            break;
                        }
                    }
                }
                visitedNodes.add(node);
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
