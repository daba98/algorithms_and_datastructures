import datastructures.graph.Graph;
import algorithms.spanning_tree.Prim;
import algorithms.spanning_tree.SpanningTreeCalculator;

public class PrimTest extends SpanningTreeTest{
    @Override
    protected SpanningTreeCalculator<Integer> getSpanningTreeCalculator(Graph<Integer> graph) {
        return new Prim<>(graph);
    }
}
