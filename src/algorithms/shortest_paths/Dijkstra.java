package algorithms.shortest_paths;

import datastructures.graph.Graph;

import java.util.*;

/**
 * An Implementation of the ShortestPathCalculator that uses the Dijkstra algorithm to compute the shortest paths
 * in a graph without negative edge weights. In general, if |V| denotes the number of nodes in the graph and |E| the
 * number of edges, then the shortest paths are computed in O(|V|log(|V|) + |E|)
 * @param <T> the type of the nodes in the graph
 */
public class Dijkstra<T> extends ShortestPathCalculator<T> {

    private final Map<T, Long> distances;
    private final Map<T, T> previous;
    private T start;


    /**
     * Creates an instance for computing shortest paths in a graph via the Dijkstra algorithm. Note that the graph must
     * not contain negative edge weights. In general, if |V| denotes the number of nodes in the graph and |E| the number
     * of edges, then the shortest paths are computed in O(|V|log(|V|) + |E|)
     * @param graph the graph the shortest paths will be calculated on
     * @exception NullPointerException if the graph is null
     */
    public Dijkstra(Graph<T> graph) {
        super(graph);
        distances = new HashMap<>();
        previous = new HashMap<>();
    }

    protected Long getWeightOfShortestPathSub(T from, T to) {
        if (start == null || !start.equals(from)) {
            start = from;
            computeShortestPaths(start);
        }

        return distances.get(to);
    }

    protected List<T> getShortestPathSub(T from, T to) {
        if (start == null || !start.equals(from)) {
            start = from;
            computeShortestPaths(start);
        }

        if (previous.get(to) == null)
            return null;
        List<T> path = new LinkedList<>();
        T current = to;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        return path;
    }

    private void computeShortestPaths(T start) {
        distances.clear();
        previous.clear();
        distances.put(start, 0L);
        TreeSet<QueueElement<T>> priorityQueue = new TreeSet<>();
        for (T node : graph.getNodes()) {
            Long priority = distances.getOrDefault(node, Long.MAX_VALUE);
            priorityQueue.add(new QueueElement<>(node, priority, graph.getId(node)));
        }

        while (!priorityQueue.isEmpty()) {
            QueueElement<T> element = priorityQueue.pollFirst();
            for (T succ : graph.getSuccessors(element.node)) {
                long edgeWeight = graph.getEdgeWeight(element.node, succ);
                if (edgeWeight < 0)
                    throw new RuntimeException("Graph must not contain negative weight edges");
                long currentDist = distances.getOrDefault(succ, Long.MAX_VALUE);
                Long nodeDist = distances.get(element.node);
                if (nodeDist != null && nodeDist + edgeWeight < currentDist) {
                    distances.put(succ, nodeDist + edgeWeight);
                    priorityQueue.remove(new QueueElement<>(succ, currentDist, graph.getId(succ)));
                    priorityQueue.add(new QueueElement<>(succ, nodeDist + edgeWeight, graph.getId(succ)));
                    previous.put(succ, element.node);
                }
            }
        }
    }

    private static final class QueueElement<T> implements Comparable<QueueElement<T>> {
        public T node;
        public long priority;
        private final long nodeId;

        public QueueElement(T node, long priority, long nodeId) {
            this.node = node;
            this.priority = priority;
            this.nodeId = nodeId;
        }

        @Override
        public int compareTo(QueueElement<T> e) {

            if (priority < e.priority)
                return -1;
            if (priority > e.priority)
                return 1;
            return Long.compare(nodeId, e.nodeId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QueueElement<?> that = (QueueElement<?>) o;
            return priority == that.priority &&
                    nodeId == that.nodeId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, nodeId);
        }
    }
}
