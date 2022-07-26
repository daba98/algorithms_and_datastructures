import algorithms.maximum_flows.EdmondsKarp;
import datastructures.graph.Graph;
import algorithms.maximum_flows.MaximumFlowCalculator;

public class EdmondsKarpTest extends MaximumFlowTest{
    @Override
    protected MaximumFlowCalculator<Integer> getMaximumFlowCalculator(Graph<Integer> graph) {
        return new EdmondsKarp<>(graph);
    }
}
