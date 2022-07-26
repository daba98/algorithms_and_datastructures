import datastructures.graph.AdjacencyListGraph;
import datastructures.graph.Graph;

public class AdjacencyListGraphTest extends GraphTest{
    @Override
    protected Graph<Integer> getGraph() {
        return new AdjacencyListGraph<>();
    }
}
