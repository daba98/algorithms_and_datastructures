import algorithms.maximum_flows.FordFulkerson;
import datastructures.graph.Graph;
import algorithms.maximum_flows.MaximumFlowCalculator;

public class FordFulkersonTest extends MaximumFlowTest{
    @Override
    protected MaximumFlowCalculator<Integer> getMaximumFlowCalculator(Graph<Integer> graph) {
        return new FordFulkerson<>(graph);
    }
}
