import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;

public class AdjacencyMatrixGraphTest extends GraphTest{
    @Override
    protected Graph<Integer> getGraph() {
        return new AdjacencyMatrixGraph<>();
    }
}
