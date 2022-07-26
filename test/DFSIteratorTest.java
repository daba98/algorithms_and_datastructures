import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.DFSIterator;
import datastructures.graph.Graph;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class DFSIteratorTest {

    @Test
    public void testAllNodesAreVisitedInStronglyConnectedGraph(){
        /*
          _______________
         /               \
        1 --- 2 --- 3 --- 4
             /_\
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        graph.addNodes(nodes);

        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(2, 3);
        graph.addUndirectedEdge(3, 4);
        graph.addUndirectedEdge(1, 4);
        graph.addUndirectedEdge(2, 2);

        DFSIterator<Integer> iterator = graph.getDFSIterator(1);
        Set<Integer> visited = new HashSet<>();
        while(iterator.hasNext())
            visited.add(iterator.next());

        for(Integer node : nodes)
            Assert.assertTrue(visited.contains(node));
    }


    @Test
    public void testNodesNotReachableFromTheStartingNodeAreNotVisited(){
        /*
          _________________
         /                 \
        1 --- 2 <--- 3 ---> 4
             /_\
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        graph.addNodes(nodes);

        graph.addUndirectedEdge(1, 2);
        graph.addDirectedEdge(3, 2);
        graph.addDirectedEdge(3, 4);
        graph.addUndirectedEdge(1, 4);
        graph.addUndirectedEdge(2, 2);

        DFSIterator<Integer> iterator = graph.getDFSIterator(1);
        while(iterator.hasNext())
            Assert.assertNotEquals(Integer.valueOf(3), iterator.next());

        /*
        1 --- 2     3 --- 4
             /_\
        */
        graph = new AdjacencyMatrixGraph<>();
        graph.addNodes(nodes);

        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(3, 4);
        graph.addUndirectedEdge(2, 2);

        iterator = graph.getDFSIterator(1);
        while(iterator.hasNext()) {
            Assert.assertNotEquals(Integer.valueOf(3), iterator.next());
            Assert.assertNotEquals(Integer.valueOf(4), iterator.next());
        }
    }


    @Test
    public void testNoNodeIsReturnedTwice(){
        /*
          _______________
         /               \
        1 --- 2 --- 3 --- 4
             /_\
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        graph.addNodes(nodes);

        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(2, 3);
        graph.addUndirectedEdge(3, 4);
        graph.addUndirectedEdge(1, 4);
        graph.addUndirectedEdge(2, 2);

        DFSIterator<Integer> iterator = graph.getDFSIterator(1);
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
                 /--- 3 --- 5
         1 --- 2
         ^        \--- 4
         |_____________|
        */
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        nodes.add(5);
        graph.addNodes(nodes);

        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(2, 3);
        graph.addUndirectedEdge(2, 4);
        graph.addUndirectedEdge(3, 5);
        graph.addDirectedEdge(4, 1);

        DFSIterator<Integer> iterator = graph.getDFSIterator(1);
        Assert.assertEquals(Integer.valueOf(1), iterator.next());
        Integer next = iterator.next();
        Assert.assertEquals(Integer.valueOf(2), next);
        next = iterator.next();
        Assert.assertTrue(next.equals(3) || next.equals(4));
        if(next == 3){
            Assert.assertEquals(Integer.valueOf(5), iterator.next());
            Assert.assertEquals(Integer.valueOf(4), iterator.next());
        }
        else{
            Assert.assertEquals(Integer.valueOf(3), iterator.next());
            Assert.assertEquals(Integer.valueOf(5), iterator.next());
        }
    }

    @Test(expected = NullPointerException.class)
    public void testNullStartNodeThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);

        DFSIterator<Integer> iterator = graph.getDFSIterator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartingNodeNotInTheGraphThrowsNPE(){
        Graph<Integer> graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);

        DFSIterator<Integer> iterator = graph.getDFSIterator(2);
    }
}
