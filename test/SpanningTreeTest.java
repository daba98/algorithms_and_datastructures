import datastructures.graph.AdjacencyMatrixGraph;
import datastructures.graph.Graph;
import algorithms.spanning_tree.SpanningTreeCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class SpanningTreeTest {

    private Graph<Integer> graph;

    protected abstract SpanningTreeCalculator<Integer> getSpanningTreeCalculator(Graph<Integer> graph);

    @Before
    public void init(){
        graph = new AdjacencyMatrixGraph<>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
    }

    @Test
    public void testWeightOfMinimumSpanningTreeOfUnweightedGraph(){
        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(1, 3);
        graph.addUndirectedEdge(2, 4);
        graph.addUndirectedEdge(4, 5);
        graph.addUndirectedEdge(2, 5);
        graph.addUndirectedEdge(3, 5);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertEquals(graph.size() - 1, spanningTreeCalculator.getWeightOfMinimumSpanningTree());
    }

    @Test
    public void testWeightOfMaximumSpanningTreeOfUnweightedGraph(){
        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(1, 3);
        graph.addUndirectedEdge(2, 4);
        graph.addUndirectedEdge(4, 5);
        graph.addUndirectedEdge(2, 5);
        graph.addUndirectedEdge(3, 5);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertEquals(graph.size() - 1, spanningTreeCalculator.getWeightOfMaximumSpanningTree());
    }

    @Test
    public void testWeightOfMinimumSpanningTreeOfWeightedGraph(){
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(2, 4, 5);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 5, -1);
        graph.addUndirectedEdge(3, 5, 2);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertEquals(-1, spanningTreeCalculator.getWeightOfMinimumSpanningTree());
    }

    @Test
    public void testWeightOfMaximumSpanningTreeOfWeightedGraph(){
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(2, 4, 5);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 5, -1);
        graph.addUndirectedEdge(3, 5, 2);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertEquals(11, spanningTreeCalculator.getWeightOfMaximumSpanningTree());
    }

    @Test
    public void testWeightOfSpanningTreeOnEmptyGraph(){
        graph = new AdjacencyMatrixGraph<>();

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertEquals(0, spanningTreeCalculator.getWeightOfMinimumSpanningTree());
        Assert.assertEquals(0, spanningTreeCalculator.getWeightOfMaximumSpanningTree());
    }

    @Test
    public void testSpanningTreeOnEmptyGraph(){
        graph = new AdjacencyMatrixGraph<>();

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Assert.assertTrue(spanningTreeCalculator.getMinimumSpanningTree().getNodes().isEmpty());
        Assert.assertTrue(spanningTreeCalculator.getMaximumSpanningTree().getNodes().isEmpty());
    }

    @Test
    public void testAllNodesAreContainedInSpanningTree(){
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(2, 4, 5);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 5, -1);
        graph.addUndirectedEdge(3, 5, 2);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Graph<Integer> spanningTree = spanningTreeCalculator.getMinimumSpanningTree();
        Assert.assertEquals(graph.size(), spanningTree.size());
        for(Integer node : graph.getNodes())
            Assert.assertTrue(spanningTree.contains(node));

        spanningTree = spanningTreeCalculator.getMaximumSpanningTree();
        Assert.assertEquals(graph.size(), spanningTree.size());
        for(Integer node : graph.getNodes())
            Assert.assertTrue(spanningTree.contains(node));
    }

    @Test
    public void testSpanningTreeContainsOnlyEdgesOfTheGraph(){
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(2, 4, 5);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 5, -1);
        graph.addUndirectedEdge(3, 5, 2);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Graph<Integer> spanningTree = spanningTreeCalculator.getMinimumSpanningTree();
        for(Integer node : graph.getNodes()){
            for(Integer node2 : graph.getNodes()){
                if(spanningTree.containsEdge(node, node2))
                    Assert.assertTrue(graph.containsEdge(node, node2));
            }
        }

        spanningTree = spanningTreeCalculator.getMaximumSpanningTree();
        for(Integer node : graph.getNodes()){
            for(Integer node2 : graph.getNodes()){
                if(spanningTree.containsEdge(node, node2))
                    Assert.assertTrue(graph.containsEdge(node, node2));
            }
        }
    }

    @Test
    public void testSpanningTreeContainsCorrectEdges(){
        graph.addUndirectedEdge(1, 2, -3);
        graph.addUndirectedEdge(1, 5, 0);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(2, 4, 5);
        graph.addUndirectedEdge(4, 5, 1);
        graph.addUndirectedEdge(2, 5, -1);
        graph.addUndirectedEdge(3, 5, 2);

        SpanningTreeCalculator<Integer> spanningTreeCalculator = getSpanningTreeCalculator(graph);
        Graph<Integer> spanningTree = spanningTreeCalculator.getMinimumSpanningTree();
        Assert.assertTrue(spanningTree.containsEdge(2, 5));
        Assert.assertTrue(spanningTree.containsEdge(4, 5));
        Assert.assertTrue(spanningTree.containsEdge(1, 2));
        Assert.assertTrue(spanningTree.containsEdge(3, 5));
        Assert.assertFalse(spanningTree.containsEdge(1, 3));
        Assert.assertFalse(spanningTree.containsEdge(2, 4));
        Assert.assertFalse(spanningTree.containsEdge(1, 5));

        spanningTree = spanningTreeCalculator.getMaximumSpanningTree();
        Assert.assertTrue(spanningTree.containsEdge(2, 4));
        Assert.assertTrue(spanningTree.containsEdge(1, 3));
        Assert.assertTrue(spanningTree.containsEdge(3, 5));
        Assert.assertTrue(spanningTree.containsEdge(4, 5));
        Assert.assertFalse(spanningTree.containsEdge(1, 2));
        Assert.assertFalse(spanningTree.containsEdge(2, 5));
        Assert.assertFalse(spanningTree.containsEdge(1, 5));
    }
}
