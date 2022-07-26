package algorithms.shortest_paths;

import datastructures.graph.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An Implementation of the ShortestPathCalculator that uses the Bellman-Ford algorithm to compute the shortest paths
 * in a graph without negative cycles. In general, if |V| denotes the number of nodes in the graph and |E| the number of
 * edges, then the shortest paths are computed in O(|V|^3)
 * @param <T> the type of the nodes in the graph
 */
public class BellmanFord<T> extends ShortestPathCalculator<T>{

    private final Map<T, Long> distances;
    private final Map<T, T> previous;
    private T start;


    /**
     * Creates an instance for computing shortest paths in a graph via the Bellman-Ford algorithm. Note that the graph
     * must not contain negative cycles.
     * @param graph the graph the shortest paths will be calculated on
     * @exception NullPointerException if the graph is null
     */
    public BellmanFord(Graph<T> graph){
        super(graph);
        distances = new HashMap<>();
        previous = new HashMap<>();
    }

    protected Long getWeightOfShortestPathSub(T from, T to){
        if(start == null || start != from){
            start = from;
            computeShortestPaths(from);
        }

        return distances.get(to);
    }

    protected List<T> getShortestPathSub(T from, T to){
        if(start == null || !start.equals(from)){
            start = from;
            computeShortestPaths(start);
        }

        if(previous.get(to) == null)
            return null;
        List<T> path = new LinkedList<>();
        T current = to;
        while(current != null){
            path.add(0, current);
            current = previous.get(current);
        }

        return path;
    }

    private void computeShortestPaths(T start){
        distances.clear();
        previous.clear();
        distances.put(start, 0L);
        int nboNodes = graph.size();

        for(int i = 0; i < nboNodes - 1; i++){
            for(T node : graph.getNodes()){
                for(T succ : graph.getSuccessors(node)){
                    long edgeWeight = graph.getEdgeWeight(node, succ);
                    Long nodeDist = distances.get(node);
                    Long currentDist = distances.getOrDefault(succ, Long.MAX_VALUE);
                    if(nodeDist != null && currentDist > nodeDist + edgeWeight) {
                        distances.put(succ, nodeDist + edgeWeight);
                        previous.put(succ, node);
                    }
                }
            }
        }

        //Detect negative cycles
        for(T node : graph.getNodes()){
            for(T succ : graph.getSuccessors(node)){
                long edgeWeight = graph.getEdgeWeight(node, succ);
                Long nodeDist = distances.get(node);
                Long currentDist = distances.getOrDefault(succ, Long.MAX_VALUE);
                if(nodeDist != null && currentDist > nodeDist + edgeWeight) {
                    throw new RuntimeException("The graph contains a negative cycle");
                }
            }
        }
    }
}
