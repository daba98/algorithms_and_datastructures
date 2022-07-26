import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;
import algorithms.shortest_paths.ShortestPathCalculator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public abstract class ShortestPathTest {

    protected abstract ShortestPathCalculator<Integer> getShortestPathCalculator(Graph<Integer> graph);

    @Test
    public void testWeightOfShortestPathInConnectedGraph(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 4, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        Assert.assertEquals(Long.valueOf(13), shortestPathCalculator.getWeightOfShortestPath(1, 5));
        Assert.assertEquals(Long.valueOf(5), shortestPathCalculator.getWeightOfShortestPath(1, 3));
        Assert.assertEquals(Long.valueOf(0), shortestPathCalculator.getWeightOfShortestPath(1, 1));
        Assert.assertEquals(Long.valueOf(9), shortestPathCalculator.getWeightOfShortestPath(3, 5));
        Assert.assertEquals(Long.valueOf(9), shortestPathCalculator.getWeightOfShortestPath(2, 4));
    }

    @Test
    public void testWeightOfShortestPathInUnconnectedGraph(){
        /*
           _______4_________
          /                 \
        "1" --3-- "2" --2-- "3"     "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(1, 3, 4);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        Assert.assertNull(shortestPathCalculator.getWeightOfShortestPath(1, 5));
        Assert.assertEquals(Long.valueOf(4), shortestPathCalculator.getWeightOfShortestPath(1, 3));
        Assert.assertEquals(Long.valueOf(0), shortestPathCalculator.getWeightOfShortestPath(1, 1));
        Assert.assertEquals(Long.valueOf(1), shortestPathCalculator.getWeightOfShortestPath(4, 5));
        Assert.assertNull(shortestPathCalculator.getWeightOfShortestPath(5, 3));
    }

    @Test
    public void testWeightOfShortestPathInGraphWithDirectedEdges(){
        /*
                     |---------9--------\
                    \/                   \
        "1" --3--> "2" --2--> "3" --8--> "4" --1--> "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addDirectedEdge(1, 2, 3);
        graph.addDirectedEdge(2, 3, 2);
        graph.addDirectedEdge(3, 4, 8);
        graph.addDirectedEdge(4, 5, 1);
        graph.addDirectedEdge(4, 2, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        Assert.assertEquals(Long.valueOf(14), shortestPathCalculator.getWeightOfShortestPath(1, 5));
        Assert.assertEquals(Long.valueOf(5), shortestPathCalculator.getWeightOfShortestPath(1, 3));
        Assert.assertEquals(Long.valueOf(0), shortestPathCalculator.getWeightOfShortestPath(1, 1));
        Assert.assertEquals(Long.valueOf(9), shortestPathCalculator.getWeightOfShortestPath(3, 5));
        Assert.assertEquals(Long.valueOf(17), shortestPathCalculator.getWeightOfShortestPath(3, 2));
        Assert.assertNull(shortestPathCalculator.getWeightOfShortestPath(5, 4));
    }

    @Test
    public void testShortestPathUsesOnlyEdgesInTheGraph(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 4, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        List<Integer> shortestPath = shortestPathCalculator.getShortestPath(1, 5);
        int current = shortestPath.remove(0);
        while(!shortestPath.isEmpty()){
            int next = shortestPath.remove(0);
            Assert.assertTrue(graph.containsEdge(current, next));
            current = next;
        }
    }

    @Test
    public void testShortestPathToUnreachableNodeIsNull(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" <--1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(2, 4, 9);
        graph.addDirectedEdge(5, 4, 1);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        Assert.assertNull(shortestPathCalculator.getShortestPath(1, 5));
    }

    @Test
    public void testShortestPathStartsAtTheStartNode(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 4, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        List<Integer> shortestPath = shortestPathCalculator.getShortestPath(1, 5);
        Assert.assertEquals(Integer.valueOf(1), shortestPath.get(0));
    }

    @Test
    public void testShortestPathEndsAtTheEndNode(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 4, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        List<Integer> shortestPath = shortestPathCalculator.getShortestPath(1, 5);
        Assert.assertEquals(Integer.valueOf(5), shortestPath.get(shortestPath.size() - 1));
    }

    @Test
    public void testSumOfTheEdgeWeightsOfTheShortestPathEqualsTheWeightOfTheShortestPath(){
        /*
                     _______9_________
                    /                 \
        "1" --3-- "2" --2-- "3" --8-- "4" --1-- "5"
         */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addUndirectedEdge(1, 2, 3);
        graph.addUndirectedEdge(2, 3, 2);
        graph.addUndirectedEdge(3, 4, 8);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 4, 9);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        List<Integer> shortestPath = shortestPathCalculator.getShortestPath(1, 5);
        long sum = 0;
        int current = shortestPath.remove(0);
        while(!shortestPath.isEmpty()){
            int next = shortestPath.remove(0);
            sum += graph.getEdgeWeight(current, next);
            current = next;
        }
        Assert.assertEquals(shortestPathCalculator.getWeightOfShortestPath(1, 5), Long.valueOf(sum));

        shortestPath = shortestPathCalculator.getShortestPath(3, 5);
        sum = 0;
        current = shortestPath.remove(0);
        while(!shortestPath.isEmpty()){
            int next = shortestPath.remove(0);
            sum += graph.getEdgeWeight(current, next);
            current = next;
        }
        Assert.assertEquals(shortestPathCalculator.getWeightOfShortestPath(3, 5), Long.valueOf(sum));

        shortestPath = shortestPathCalculator.getShortestPath(1, 3);
        sum = 0;
        current = shortestPath.remove(0);
        while(!shortestPath.isEmpty()){
            int next = shortestPath.remove(0);
            sum += graph.getEdgeWeight(current, next);
            current = next;
        }
        Assert.assertEquals(shortestPathCalculator.getWeightOfShortestPath(1, 3), Long.valueOf(sum));
    }

    @Test(expected = NullPointerException.class)
    public void testWeightOfShortestPathWithNullStartThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2, 2);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getWeightOfShortestPath(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testWeightOfShortestPathWithNullTargetThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2, 2);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getWeightOfShortestPath(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWeightOfShortestPathWithStartNodeNotInTheGraphThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getWeightOfShortestPath(8, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWeightOfShortestPathWithTargetNodeNotInTheGraphThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getWeightOfShortestPath(1, 8);
    }

    @Test(expected = NullPointerException.class)
    public void testShortestPathWithNullStartThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2, 2);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getShortestPath(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testShortestPathWithNullTargetThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2, 2);

        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getShortestPath(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathWithStartNodeNotInTheGraphThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getShortestPath(8, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShortestPathWithTargetNodeNotInTheGraphThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        ShortestPathCalculator<Integer> shortestPathCalculator = getShortestPathCalculator(graph);
        shortestPathCalculator.getShortestPath(1, 8);
    }
}
