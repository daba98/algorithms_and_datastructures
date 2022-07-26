import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;
import algorithms.shortest_paths.Dijkstra;
import algorithms.shortest_paths.ShortestPathCalculator;
import org.junit.Test;

public class DijkstraTest extends ShortestPathTest{

    @Override
    protected ShortestPathCalculator<Integer> getShortestPathCalculator(Graph<Integer> graph) {
        return new Dijkstra<>(graph);
    }


    @Test(expected = RuntimeException.class)
    public void testWeightOfShortestPathOnGraphWithNegativeEdgeWeightThrowsRE(){
        /*
           _______4_________
          /                 \
        "1" --3-- "2" --2-- "3"     "4" --(-1)--> "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addDirectedEdge(4, 5, -1);
        graph.addUndirectedEdge(1, 3, 4);

        ShortestPathCalculator<Integer> dijkstra = new Dijkstra<>(graph);
        dijkstra.getWeightOfShortestPath(1, 3);
    }
}
