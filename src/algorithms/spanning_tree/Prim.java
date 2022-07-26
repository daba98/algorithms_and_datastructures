package algorithms.spanning_tree;

import datastructures.graph.AdjacencyListGraph;
import datastructures.graph.Graph;

import java.util.*;

/**
 * The class computes maximum and minimum spanning trees of UNDIRECTED and CONNECTED graphs. Let |€| be the number of
 * edges and |V| be the number of nodes in the graph, then the spanning tree is computed in O(|E| + |V|log(|V|)).
 * @param <T> the type of the nodes in the graph
 */
public class Prim<T> extends SpanningTreeCalculator<T> {

    private final Set<T> visitedNodes;
    private final Map<T, Long> cost;
    private final Map<T, T> pre;

    /**
     * Creates an instance to compute minimum and maximum spanning trees of the graph via the Prim's algorithm. Note that
     * the graph must be UNDIRECTED and CONNECTED
     * @param graph the graph the spanning tree is computed for
     * @exception NullPointerException if the graph is null
     */
    public Prim(Graph<T> graph) {
        super(graph);

        visitedNodes = new HashSet<>();
        cost = new HashMap<>();
        pre = new HashMap<>();
    }


    protected void computeSpanningTree(boolean isMinimumSpanningTree) {
        visitedNodes.clear();
        cost.clear();
        pre.clear();
        totalWeight = 0;
        spanningTree = new AdjacencyListGraph<>();

        TreeSet<QueueElement<T>> priorityQueue = new TreeSet<>();
        T start = null;
        for (T node : graph.getNodes()) {
            start = node;
            break;
        }

        if (start == null)
            return;

        spanningTree.addNode(start);
        primVisit(start, priorityQueue);

        while (!priorityQueue.isEmpty()) {
            T node = priorityQueue.pollFirst().node;
            spanningTree.addNode(node);
            T from = pre.get(node);
            int weight = graph.getEdgeWeight(from, node);
            spanningTree.addUndirectedEdge(from, node, weight);
            totalWeight += weight;
            primVisit(node, priorityQueue);
        }
    }

    private void primVisit(T node, TreeSet<QueueElement<T>> priorityQueue) {
        visitedNodes.add(node);
        for (T succ : graph.getSuccessors(node)) {
            if (!visitedNodes.contains(succ)) {
                long oldCost = cost.getOrDefault(succ, min ? Long.MAX_VALUE : Long.MIN_VALUE);
                long edgeWeight = graph.getEdgeWeight(node, succ);
                if((min && edgeWeight < oldCost) || (!min && edgeWeight > oldCost)) {
                    pre.put(succ, node);
                    cost.put(succ, edgeWeight);

                    if (oldCost != Long.MAX_VALUE && oldCost != Long.MIN_VALUE)
                        priorityQueue.remove(createQueueElement(succ, oldCost));
                    priorityQueue.add(createQueueElement(succ, edgeWeight));
                }
            }
        }
    }

    private QueueElement<T> createQueueElement(T node, long weight) {
        long priority = min ? weight : -weight;
        return new QueueElement<>(node, priority, graph.getId(node));
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
