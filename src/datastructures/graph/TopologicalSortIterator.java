package datastructures.graph;

import java.util.*;

/**
 * An iterator that iterates over the nodes in the graph in their topological sort order if the graph is acyclic. Note
 * that the graph must not be changed during the iteration, because then te iterator might produce a wrong result.
 * @param <T> the type of the nodes in the graph
 */
public class TopologicalSortIterator<T> implements Iterator<T> {

    private final Graph<T> graph;
    private final Set<T> visited;
    private final Map<T, Integer> inDegreeMap;
    private final LinkedList<T> queue;
    private final List<T> order;


    protected TopologicalSortIterator(Graph<T> graph){
        Objects.requireNonNull(graph);
        this.graph = graph;
        visited = new HashSet<>();
        inDegreeMap = new HashMap<>();
        queue = new LinkedList<>();
        order = new LinkedList<>();
        for(T node : graph.getNodes()){
            inDegreeMap.put(node, graph.getPredecessors(node).size());
        }

        getOrder();
    }

    private void getOrder(){
        for(T node : graph.getNodes()){
            if(inDegreeMap.get(node) == 0 && !visited.contains(node))
                explore(node);
        }

        if(order.size() != graph.getNodes().size())
            throw new IllegalArgumentException("The graph has to be acyclic, but it contains a cycle");
    }

    private void explore(T node){
        queue.add(node);
        while(!queue.isEmpty()){
            T first = queue.removeFirst();
            order.add(first);
            visited.add(first);
            for(T succ : graph.getSuccessors(first)){
                inDegreeMap.put(succ, inDegreeMap.get(succ) - 1);
                if(!visited.contains(succ) && inDegreeMap.get(succ) == 0)
                    queue.add(succ);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !order.isEmpty();
    }

    @Override
    public T next() {
        return order.remove(0);
    }
}
