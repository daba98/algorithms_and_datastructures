import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;
import algorithms.shortest_paths.BellmanFord;
import algorithms.shortest_paths.ShortestPathCalculator;
import org.junit.Assert;
import org.junit.Test;

public class BellmanFordTest extends ShortestPathTest{

    @Override
    protected ShortestPathCalculator<Integer> getShortestPathCalculator(Graph<Integer> graph) {
        return new BellmanFord<>(graph);
    }

    @Test
    public void testCanHandleNegativeEdgesThatDoNotFormANegativeCycle(){
        /*
           ___________4__________
          /                      \
        "1" --(-3)--> "2" --2--> "3" --(-1)--> "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addDirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(1, 3, 4);
        graph.addDirectedEdge(3, 4, -1);

        ShortestPathCalculator<Integer> bellmanFord = new BellmanFord<>(graph);
        Assert.assertEquals(Long.valueOf(-1), bellmanFord.getWeightOfShortestPath(1, 3));
        Assert.assertEquals(Long.valueOf(-1), bellmanFord.getWeightOfShortestPath(1, 5));
    }

    @Test(expected = RuntimeException.class)
    public void testWeightOfShortestPathOnGraphWithReachableNegativeCycleThrowsRE(){
        /*
           _______4_________
          /                 \
        "1" --3-- "2" --2-- "3" --2-- "4" --(-1)-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(4, 5, -1);
        graph.addUndirectedEdge(1, 3, 4);
        graph.addUndirectedEdge(3, 4, 2);

        ShortestPathCalculator<Integer> bellmanFord = new BellmanFord<>(graph);
        bellmanFord.getWeightOfShortestPath(1, 3);
    }

    @Test(expected = RuntimeException.class)
    public void testWeightOfShortestPathOnGraphWithReachableNegativeDirectedCycleThrowsRE(){
        /*
           ____________4____________
          /                         \
        "1" --(-3)--> "2" --(-2)--> "3"     "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(2, 3, -2);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(1, 3, 4);

        ShortestPathCalculator<Integer> bellmanFord = new BellmanFord<>(graph);
        bellmanFord.getWeightOfShortestPath(1, 3);
    }

    @Test
    public void testWeightOfShortestPathOnGraphWithNegativeCycleNotReachableFromTheStartNodeReturnsCorrectWeight(){
        /*
           __________4_________
          /                    \
        "1" --3--> "2" --2--> "3"     "4" --(-1)-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(4, 5, -1);
        graph.addUndirectedEdge(1, 3, 4);

        ShortestPathCalculator<Integer> bellmanFord = new BellmanFord<>(graph);
        Assert.assertEquals(Long.valueOf(4), bellmanFord.getWeightOfShortestPath(1, 3));
    }
}
