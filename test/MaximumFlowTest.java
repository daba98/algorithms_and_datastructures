import datastructures.graph.AdjacencyListGraph;
import datastructures.graph.Graph;
import algorithms.maximum_flows.MaximumFlowCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class MaximumFlowTest {

    private Graph<Integer> graph;

    protected abstract MaximumFlowCalculator<Integer> getMaximumFlowCalculator(Graph<Integer> graph);

    @Before
    public void init(){
        graph = new AdjacencyListGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
    }

    @Test
    public void testMaxFlowReturnsCorrectFlowInConnectedGraph(){
        /*
          ___2___> "2" ___6___
         |         ^         |
        "1"        5         -> "4"
         |         |             ^
         |--10--> "3" ---- 2 ---/
         */

        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(1, 3, 10);
        graph.addDirectedEdge(3, 2, 5);
        graph.addDirectedEdge(2, 4, 6);
        graph.addDirectedEdge(3, 4, 2);

        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        Assert.assertEquals(8, maximumFlowCalculator.getMaximumFlow(1, 4));
        Assert.assertEquals(7, maximumFlowCalculator.getMaximumFlow(1, 2));
        Assert.assertEquals(10, maximumFlowCalculator.getMaximumFlow(1, 3));
    }

    @Test
    public void testMaxFlowReturnsCorrectFlowInDisconnectedGraph(){
        /*
          ___2___> "2"
         |         ^
        "1"        5     "4"
         |         |
         |--10--> "3"
         */

        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(1, 3, 10);
        graph.addDirectedEdge(3, 2, 5);

        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        Assert.assertEquals(0, maximumFlowCalculator.getMaximumFlow(1, 4));
        Assert.assertEquals(7, maximumFlowCalculator.getMaximumFlow(1, 2));
        Assert.assertEquals(10, maximumFlowCalculator.getMaximumFlow(1, 3));
    }

    @Test
    public void testMaxFlowReturnsCorrectFlowWithSourceHavingIncomingEdges(){
        /*
              _2_ "2" ___6___
             |     ^         |
        "1"<-      5         -> "4"
         |         |             ^
         |--10--> "3" ---- 2 ---/
         */

        graph.addDirectedEdge(2, 1, 2);
        graph.addDirectedEdge(1, 3, 10);
        graph.addDirectedEdge(3, 2, 5);
        graph.addDirectedEdge(2, 4, 6);
        graph.addDirectedEdge(3, 4, 2);

        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        Assert.assertEquals(7, maximumFlowCalculator.getMaximumFlow(1, 4));
        Assert.assertEquals(5, maximumFlowCalculator.getMaximumFlow(1, 2));
        Assert.assertEquals(10, maximumFlowCalculator.getMaximumFlow(1, 3));
    }

    @Test(expected = NullPointerException.class)
    public void testNullGraphThrowsNPE(){
        getMaximumFlowCalculator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphWithNegativeEdgeWeightsThrowsIAE(){
        graph.addDirectedEdge(1, 2, -1);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        getMaximumFlowCalculator(graph);
    }

    @Test(expected = NullPointerException.class)
    public void testGetMaxFlowWithNullSourceThrowsNPE(){
        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        maximumFlowCalculator.getMaximumFlow(null, 4);
    }

    @Test(expected = NullPointerException.class)
    public void testGetMaxFlowWithNullTargetThrowsNPE(){
        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        maximumFlowCalculator.getMaximumFlow(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMaxFlowWithSourceNodeNotInTheGraphThrowsIAE(){
        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        maximumFlowCalculator.getMaximumFlow(0, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMaxFlowWithTargetNodeNotInTheGraphThrowsIAE(){
        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        maximumFlowCalculator.getMaximumFlow(1, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMaxFlowWithSourceEqualsTargetThrowsIAE(){
        graph.addDirectedEdge(1, 2, 2);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 4, 3);
        MaximumFlowCalculator<Integer> maximumFlowCalculator = getMaximumFlowCalculator(graph);
        maximumFlowCalculator.getMaximumFlow(1, 1);
    }
}
