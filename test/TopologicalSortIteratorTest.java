import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;
import datastructures.graph.TopologicalSortIterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TopologicalSortIteratorTest {

    @Test
    public void testAllNodesAreVisited(){
        /*
        1 --> 2   -> 3 --> 4   5
        |________|
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        nodes.add(5);
        graph.addNodes(nodes);

        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);

        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
        Set<Integer> visited = new HashSet<>();
        while(iterator.hasNext())
            visited.add(iterator.next());

        for(Integer node : nodes)
            Assert.assertTrue(visited.contains(node));
    }


    @Test
    public void testNoNodeIsReturnedTwice(){
        /*
        1 --> 2   -> 3 --> 4   5
        |________|
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        nodes.add(5);
        graph.addNodes(nodes);

        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);

        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
        Set<Integer> visited = new HashSet<>();
        while(iterator.hasNext()) {
            Integer next = iterator.next();
            Assert.assertFalse(visited.contains(next));
            visited.add(next);
        }
    }

    @Test
    public void testIteratorReturnsNodesInRightOrder(){
        /*
                   _____
                  |    |
        1 --> 2 <-  -> 3 --> 4   5
        |__________|
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        nodes.add(5);
        graph.addNodes(nodes);

        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(3, 2);

        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
        Integer next = iterator.next();
        Assert.assertTrue(next.equals(1) || next.equals(5));
        if(next == 5){
            Assert.assertEquals(Integer.valueOf(1), iterator.next());
            Assert.assertEquals(Integer.valueOf(3), iterator.next());
            next = iterator.next();
            Assert.assertTrue(next.equals(4) || next.equals(2));
            next = iterator.next();
            Assert.assertTrue(next.equals(4) || next.equals(2));
        }
        else{
            next = iterator.next();
            Assert.assertTrue(next.equals(3) || next.equals(5));
            if(next == 5){
                Assert.assertEquals(Integer.valueOf(3), iterator.next());
                next = iterator.next();
                Assert.assertTrue(next.equals(4) || next.equals(2));
                next = iterator.next();
                Assert.assertTrue(next.equals(4) || next.equals(2));
            }
            else{
                next = iterator.next();
                Assert.assertTrue(next.equals(2) || next.equals(4) || next.equals(5));
                next = iterator.next();
                Assert.assertTrue(next.equals(2) || next.equals(4) || next.equals(5));
                next = iterator.next();
                Assert.assertTrue(next.equals(2) || next.equals(4) || next.equals(5));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphWithUndirectedEdgeThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        graph.addUndirectedEdge(2, 3);
        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphWithSelfLoopThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        graph.addDirectedEdge(2, 2);
        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGraphWithCycleThrowsIAE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);

        graph.addDirectedEdge(2, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(4, 2);
        TopologicalSortIterator<Integer> iterator = graph.getTopologicalSortIterator();
    }
}
