package algorithms.shortest_paths;

import datastructures.graph.Graph;

import java.util.*;

/**
 * An Implementation of the ShortestPathCalculator that uses the Floyd-Warshall algorithm to compute the shortest paths
 * in a graph without negative cycles. In general, if |V| denotes the number of nodes in the graph, then the shortest
 * paths are computed in O(|V|^3)
 * @param <T> the type of the nodes in the graph
 */
public class FloydWarshall<T> extends ShortestPathCalculator<T>{

    private final Map<MapElementKey, Long> distances;
    private final Map<MapElementKey, T> intermediates;

    /**
     * Creates an instance for computing shortest paths in a graph via the Floyed-Warshall algorithm. Note that the
     * graph must not contain negative cycles. In general, if |V| denotes the number of nodes in the graph, then the
     * shortest paths are computed in O(|V|^3)
     * @param graph the graph the shortest paths will be calculated on
     * @exception NullPointerException if the graph is null
     */
    public FloydWarshall(Graph<T> graph){
        super(graph);
        distances = new HashMap<>();
        intermediates = new HashMap<>();
    }

    protected Long getWeightOfShortestPathSub(T from, T to){
        if(distances.isEmpty())
            computeShortestPaths();
        return distances.getOrDefault(getMapElementKey(from, to), null);
    }

    protected List<T> getShortestPathSub(T from, T to){
        if(distances.isEmpty())
            computeShortestPaths();

        if(getWeightOfShortestPath(from, to) == null)
            return null;

        List<T> shortestPath = new LinkedList<>();
        T intermediate = intermediates.get(getMapElementKey(from, to));
        if(intermediate == null){
            shortestPath.add(from);
            shortestPath.add(to);
            return shortestPath;
        }
        else{
            shortestPath = getShortestPath(from, intermediate);
            shortestPath.remove(intermediate);
            shortestPath.addAll(getShortestPath(intermediate, to));
            return shortestPath;
        }
    }

    private void computeShortestPaths(){
        initDistances();
        for(T start : graph.getNodes()){
            for(T end : graph.getNodes()){
                for(T intermediate : graph.getNodes()){
                    MapElementKey startEndKey = getMapElementKey(start, end);
                    MapElementKey startIntermediateKey = getMapElementKey(start, intermediate);
                    MapElementKey intermediateEndKey = getMapElementKey(intermediate, end);
                    Long currentDist = distances.getOrDefault(startEndKey, Long.MAX_VALUE);
                    Long startInterDist = distances.get(startIntermediateKey);
                    Long interEndDist = distances.get(intermediateEndKey);
                    if(startInterDist != null && interEndDist != null && currentDist > startInterDist + interEndDist){
                        distances.put(startEndKey, startInterDist + interEndDist);
                        intermediates.put(startEndKey, intermediate);
                    }
                }
            }
        }

        //Check for negative cycles
        for(T node : graph.getNodes())
            if(distances.get(getMapElementKey(node, node)) < 0)
                throw new RuntimeException("Graph contains a negative cycle");
    }

    private void initDistances(){
        for(T start : graph.getNodes()){
            distances.put(getMapElementKey(start, start), 0L);
            for(T succ : graph.getSuccessors(start))
                distances.put(getMapElementKey(start, succ), (long) graph.getEdgeWeight(start, succ));
        }
    }


    private MapElementKey getMapElementKey(T from, T to){
        return new MapElementKey(graph.getId(from), graph.getId(to));
    }

    private static class MapElementKey{

        public int fromId;
        public int toId;

        public MapElementKey(int fromId, int toId) {
            this.fromId = fromId;
            this.toId = toId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapElementKey that = (MapElementKey) o;
            return fromId == that.fromId &&
                    toId == that.toId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromId, toId);
        }
    }
}
