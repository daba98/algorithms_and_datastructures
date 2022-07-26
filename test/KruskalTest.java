import datastructures.graph.Graph;
import algorithms.spanning_tree.Kruskal;
import algorithms.spanning_tree.SpanningTreeCalculator;

public class KruskalTest extends SpanningTreeTest{
    @Override
    protected SpanningTreeCalculator<Integer> getSpanningTreeCalculator(Graph<Integer> graph) {
        return new Kruskal<>(graph);
    }
}
