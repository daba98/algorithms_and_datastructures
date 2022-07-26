
import datastructures.graph.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class GraphTest {

    private Graph<Integer> graph;

    protected abstract Graph<Integer> getGraph();

    @Before
    public void init() {
        graph = getGraph();
    }

    @Test
    public void testNodeIsNotContainedBeforeItIsAdded() {
        Assert.assertFalse(graph.contains(1));
        Assert.assertFalse(graph.contains(0));

        graph.addNode(1);
        Assert.assertFalse(graph.contains(0));
        Assert.assertFalse(graph.contains(-1));
    }

    @Test
    public void testNodeIsContainedAfterAdding() {
        int node = 1;
        graph.addNode(node);
        Assert.assertTrue(graph.contains(node));

        node = 42;
        graph.addNode(node);
        Assert.assertTrue(graph.contains(node));

        graph.addNode(node);
        Assert.assertTrue(graph.contains(node));

        Set<Integer> nodes = new HashSet<>();
        nodes.add(3);
        nodes.add(1);

        graph.addNodes(nodes);
        for (Integer i : nodes)
            Assert.assertTrue(graph.contains(i));
    }

    @Test
    public void testSizeOfEmptyGraphIsZero() {
        Assert.assertEquals(0, graph.size());
    }

    @Test
    public void testSizeIncreasesByOneWhenAddingNewNode() {
        int size = graph.size();
        graph.addNode(1);
        Assert.assertEquals(++size, graph.size());

        graph.addNode(3);
        Assert.assertEquals(++size, graph.size());
    }

    @Test
    public void testSizeDoesNotChangeWhenAddingExistingNode() {
        graph.addNode(1);
        int size = graph.size();

        graph.addNode(1);
        Assert.assertEquals(size, graph.size());

        graph.addNode(3);
        graph.addNode(4);
        size = graph.size();
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(1);
        Assert.assertEquals(size, graph.size());
    }

    @Test
    public void testSizeIncreasesByTheNumberOfNewNodesWhenAddingMultipleNodes() {
        int size = graph.size();
        List<Integer> list = new LinkedList<>();
        Set<Integer> set = new HashSet<>();

        list.add(1);
        set.add(1);

        list.add(2);
        set.add(2);

        list.add(2);
        set.add(2);

        graph.addNodes(list);

        Assert.assertEquals(size + set.size(), graph.size());
    }

    @Test
    public void testGetNodesReturnsASetContainingAllAddedNodes() {
        Set<Integer> set = new HashSet<>();

        graph.addNode(1);
        set.add(1);

        graph.addNode(4);
        set.add(4);

        Set<Integer> nodes = graph.getNodes();
        for (Integer node : set)
            Assert.assertTrue(nodes.contains(node));

        set.add(3);
        set.add(5);
        graph.addNodes(set);
        nodes = graph.getNodes();
        for (Integer node : set)
            Assert.assertTrue(nodes.contains(node));
    }

    @Test
    public void testGetNodesReturnsASetContainingOnlyPreviouslyAddedNodes() {
        Set<Integer> set = new HashSet<>();

        graph.addNode(1);
        set.add(1);

        graph.addNode(4);
        set.add(4);

        Set<Integer> nodes = graph.getNodes();
        for (Integer node : nodes)
            Assert.assertTrue(set.contains(node));

        set.add(3);
        set.add(5);
        graph.addNodes(set);
        nodes = graph.getNodes();
        for (Integer node : nodes)
            Assert.assertTrue(set.contains(node));
    }

    @Test
    public void testDirectedEdgeIsContainedAfterAddingDirectedEdge() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(1, 2));

        graph.addDirectedEdge(2, 1);
        Assert.assertTrue(graph.containsEdge(2, 1));
        Assert.assertTrue(graph.containsEdge(1, 2));

        graph.addDirectedEdge(1, 1);
        Assert.assertTrue(graph.containsEdge(1, 1));
        Assert.assertTrue(graph.containsEdge(2, 1));
        Assert.assertTrue(graph.containsEdge(1, 2));

        graph.addDirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(1, 2));
        Assert.assertTrue(graph.containsEdge(2, 1));
        Assert.assertTrue(graph.containsEdge(1, 1));
    }

    @Test
    public void testDirectedEdgeInOppositeDirectionIsNotContainedAfterAddingDirectedEdge() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, 2);
        Assert.assertFalse(graph.containsEdge(2, 1));
    }

    @Test
    public void testDirectedEdgeInBothDirectionsAreContainedAfterAddingUndirectedEdge() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(1, 2));
        Assert.assertTrue(graph.containsEdge(2, 1));
    }

    @Test
    public void testRemoveUndirectedEdgeRemovesAllEdgesBetweenTheNodes() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);

        graph.removeUndirectedEdge(1, 2);
        Assert.assertFalse(graph.containsEdge(1, 2));
        Assert.assertFalse(graph.containsEdge(2, 1));

        graph.addDirectedEdge(1, 2);
        graph.removeUndirectedEdge(2, 1);
        Assert.assertFalse(graph.containsEdge(1, 2));
        Assert.assertFalse(graph.containsEdge(2, 1));

        graph.addUndirectedEdge(1, 2);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(2, 1);
        graph.addUndirectedEdge(2, 1);
        graph.removeUndirectedEdge(1, 2);
        Assert.assertFalse(graph.containsEdge(1, 2));
        Assert.assertFalse(graph.containsEdge(2, 1));
    }

    @Test
    public void testRemoveUndirectedEdgeOnlyRemovesTheEdgesBetweenTheSpecifiedNodes() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        graph.addUndirectedEdge(1, 2);
        graph.addDirectedEdge(2, 3);
        graph.addUndirectedEdge(1, 3);

        graph.removeUndirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(2, 3));
        Assert.assertTrue(graph.containsEdge(1, 3));
        Assert.assertTrue(graph.containsEdge(3, 1));
    }

    @Test
    public void testRemoveDirectedEdgeOnlyRemovesTheSpecifiedEdge() {
        graph.addNode(1);
        graph.addNode(2);

        graph.addDirectedEdge(1, 2);
        graph.removeDirectedEdge(1, 2);
        Assert.assertFalse(graph.containsEdge(1, 2));

        graph.addUndirectedEdge(1, 2);
        graph.removeDirectedEdge(1, 2);
        Assert.assertFalse(graph.containsEdge(1, 2));
    }

    @Test
    public void testRemoveDirectedEdgeOnlyRemovesTheSpecifiedDirection() {
        graph.addNode(1);
        graph.addNode(2);

        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(2, 1);
        graph.removeDirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(2, 1));

        graph.addUndirectedEdge(1, 2);
        graph.removeDirectedEdge(1, 2);
        Assert.assertTrue(graph.containsEdge(2, 1));
    }

    @Test
    public void testRemoveDirectedEdgeRemovesBothDirectionsIfFromEqualsTo() {
        graph.addNode(1);
        graph.addNode(2);

        graph.addDirectedEdge(1, 1);
        graph.addUndirectedEdge(2, 2);

        graph.removeDirectedEdge(1, 1);
        Assert.assertFalse(graph.containsEdge(1, 1));

        graph.removeDirectedEdge(2, 2);
        Assert.assertFalse(graph.containsEdge(2, 2));
    }

    @Test
    public void testUndirectedEdgeAddsTheNodeToTheOtherNodesSuccessors() {
        graph.addNode(1);
        graph.addNode(2);

        Assert.assertTrue(graph.getSuccessors(1).isEmpty());
        Assert.assertTrue(graph.getSuccessors(2).isEmpty());

        graph.addUndirectedEdge(1, 2);
        Assert.assertTrue(graph.getSuccessors(1).contains(2));
        Assert.assertTrue(graph.getSuccessors(2).contains(1));
    }

    @Test
    public void testDirectedEdgeOnlyAddsTheToNodeToTheSuccessorsOfTheFromNode() {
        graph.addNode(1);
        graph.addNode(2);

        Assert.assertTrue(graph.getSuccessors(1).isEmpty());
        Assert.assertTrue(graph.getSuccessors(2).isEmpty());

        graph.addDirectedEdge(1, 2);
        Assert.assertTrue(graph.getSuccessors(1).contains(2));
        Assert.assertFalse(graph.getSuccessors(2).contains(1));
    }

    @Test
    public void testUndirectedEdgeAddsTheNodeToTheOtherNodesPredecessors() {
        graph.addNode(1);
        graph.addNode(2);

        Assert.assertTrue(graph.getPredecessors(1).isEmpty());
        Assert.assertTrue(graph.getPredecessors(2).isEmpty());

        graph.addUndirectedEdge(1, 2);
        Assert.assertTrue(graph.getPredecessors(1).contains(2));
        Assert.assertTrue(graph.getPredecessors(2).contains(1));
    }

    @Test
    public void testDirectedEdgeOnlyAddsTheFromNodeToThePredecessorsOfTheToNode() {
        graph.addNode(1);
        graph.addNode(2);

        Assert.assertTrue(graph.getSuccessors(1).isEmpty());
        Assert.assertTrue(graph.getSuccessors(2).isEmpty());

        graph.addDirectedEdge(1, 2);
        Assert.assertTrue(graph.getPredecessors(2).contains(1));
        Assert.assertFalse(graph.getPredecessors(1).contains(2));
    }

    @Test
    public void testInDegreeEqualsTheNumberOfPredecessors() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);

        graph.addUndirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(4, 1);
        graph.addUndirectedEdge(1, 1);
        graph.addUndirectedEdge(3, 4);

        Assert.assertEquals(graph.inDegree(1), graph.getPredecessors(1).size());
        Assert.assertEquals(graph.inDegree(2), graph.getPredecessors(2).size());
        Assert.assertEquals(graph.inDegree(3), graph.getPredecessors(3).size());
        Assert.assertEquals(graph.inDegree(4), graph.getPredecessors(4).size());
    }

    @Test
    public void testOutDegreeEqualsTheNumberOfSuccessors() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);

        graph.addUndirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(4, 1);
        graph.addUndirectedEdge(1, 1);
        graph.addUndirectedEdge(3, 4);

        Assert.assertEquals(graph.outDegree(1), graph.getSuccessors(1).size());
        Assert.assertEquals(graph.outDegree(2), graph.getSuccessors(2).size());
        Assert.assertEquals(graph.outDegree(3), graph.getSuccessors(3).size());
        Assert.assertEquals(graph.outDegree(4), graph.getSuccessors(4).size());
    }

    @Test
    public void testEdgeIsNotConatinedBeforeAdded(){
        graph.addNode(1);
        graph.addNode(2);
        Assert.assertFalse(graph.containsEdge(1, 2));
        Assert.assertFalse(graph.containsEdge(2, 1));
        Assert.assertFalse(graph.containsEdge(1, 1));
        Assert.assertFalse(graph.containsEdge(2, 2));
    }

    @Test
    public void testOnlyEdgeInTheRightDirectionIsContainedAfterAddingDirectedEdge(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3, 9);
        graph.addDirectedEdge(3, 2, 0);
        Assert.assertTrue(graph.containsEdge(1, 2));
        Assert.assertFalse(graph.containsEdge(2, 1));
        Assert.assertTrue(graph.containsEdge(1, 3));
        Assert.assertFalse(graph.containsEdge(3, 1));
        Assert.assertTrue(graph.containsEdge(3, 2));
        Assert.assertFalse(graph.containsEdge(2, 3));
    }

    @Test
    public void testEdgeInBothDirectionsIsContainedAfterAddingUndirectedEdge(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addUndirectedEdge(1, 2);
        graph.addUndirectedEdge(1, 3, 9);
        graph.addUndirectedEdge(3, 2, 0);
        Assert.assertTrue(graph.containsEdge(1, 2));
        Assert.assertTrue(graph.containsEdge(2, 1));
        Assert.assertTrue(graph.containsEdge(1, 3));
        Assert.assertTrue(graph.containsEdge(3, 1));
        Assert.assertTrue(graph.containsEdge(3, 2));
        Assert.assertTrue(graph.containsEdge(2, 3));
    }

    @Test
    public void testEdgeWeightOfEdgeWithDefaultWeightIsOne(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addDirectedEdge(1, 2);
        graph.addUndirectedEdge(2, 3);
        graph.addDirectedEdge(1, 1);

        Assert.assertEquals(1, graph.getEdgeWeight(1, 2));
        Assert.assertEquals(1, graph.getEdgeWeight(2, 3));
        Assert.assertEquals(1, graph.getEdgeWeight(3, 2));
        Assert.assertEquals(1, graph.getEdgeWeight(1, 1));
    }

    @Test
    public void testEdgeWeightOfEdgeWithWeightEqualsTheSpecifiedWeight(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        int weightEdge_1_2 = 3;
        graph.addDirectedEdge(1, 2, weightEdge_1_2);
        int weightEdge_2_3 = -1;
        graph.addUndirectedEdge(2, 3, weightEdge_2_3);
        int weightEdge_1_1 = 0;
        graph.addDirectedEdge(1, 1, weightEdge_1_1);

        Assert.assertEquals(weightEdge_1_2, graph.getEdgeWeight(1, 2));
        Assert.assertEquals(weightEdge_2_3, graph.getEdgeWeight(2, 3));
        Assert.assertEquals(weightEdge_2_3, graph.getEdgeWeight(3, 2));
        Assert.assertEquals(weightEdge_1_1, graph.getEdgeWeight(1, 1));
    }

    @Test(expected = NullPointerException.class)
    public void testContainsNullThrowsNPE(){
        graph.contains(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullNodeThrowsNPE(){
        graph.addNode(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullCollectionNodesThrowsNPE(){
        graph.addNodes(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNodesCollectionContainingNullThrowsNPE(){
        Set<Integer> nodes = new HashSet<>();
        nodes.add(1);
        nodes.add(null);
        graph.addNodes(nodes);
    }

    @Test(expected = NullPointerException.class)
    public void testAddDirectedEdgeWithDefaultWeightAndNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddDirectedEdgeWithDefaultWeightAndNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddDirectedEdgeWithWeightAndNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(null, 2, 8);
    }

    @Test(expected = NullPointerException.class)
    public void testAddDirectedEdgeWithWeightAndNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, null, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectedEdgeWithDefaultWeightAndSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectedEdgeWithDefaultWeightAndTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectedEdgeWithWeightAndSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(3, 2, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectedEdgeWithWeightAndTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(1, 3, 8);
    }

    @Test(expected = NullPointerException.class)
    public void testAddUndirectedEdgeWithDefaultWeightAndNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddUndirectedEdgeWithDefaultWeightAndNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddUndirectedEdgeWithWeightAndNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(null, 2, 8);
    }

    @Test(expected = NullPointerException.class)
    public void testAddUndirectedEdgeWithWeightAndNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, null, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUndirectedEdgeWithDefaultWeightAndSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUndirectedEdgeWithDefaultWeightAndTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUndirectedEdgeWithWeightAndSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(3, 2, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUndirectedEdgeWithWeightAndTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 3, 8);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveUndirectedEdgeWithNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.removeUndirectedEdge(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveUndirectedEdgeWithNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.removeUndirectedEdge(1, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveDirectedEdgeWithNullSourceNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.removeDirectedEdge(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveDirectedEdgeWithNullTargetNodeThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.removeDirectedEdge(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveUndirectedEdgeWithSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.removeUndirectedEdge(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveUndirectedEdgeWithTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.removeUndirectedEdge(1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDirectedEdgeWithSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.removeDirectedEdge(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDirectedEdgeWithTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.removeDirectedEdge(1, 3);
    }

    @Test(expected = NullPointerException.class)
    public void testGetSuccessorsOfNullThrowsNPE(){
        graph.getSuccessors(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSuccessorsOfNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.getSuccessors(2);
    }

    @Test(expected = NullPointerException.class)
    public void testGetPredecessorsOfNullThrowsNPE(){
        graph.getPredecessors(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPredecessorsOfNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.getPredecessors(2);
    }

    @Test(expected = NullPointerException.class)
    public void testInDegreeOfNullThrowsNPE(){
        graph.inDegree(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInDegreeOfNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.inDegree(2);
    }

    @Test(expected = NullPointerException.class)
    public void testOutDegreeOfNullThrowsNPE(){
        graph.outDegree(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutDegreeOfNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.outDegree(2);
    }

    @Test(expected = NullPointerException.class)
    public void testGetEdgeWeightOfNullSourceThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.getEdgeWeight(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testGetEdgeWeightOfNullTargetThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.getEdgeWeight(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEdgeWeightWithSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.getEdgeWeight(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEdgeWeightWithTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.getEdgeWeight(1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEdgeWeightOfEdgeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.getEdgeWeight(1, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testContainsEdgetOfNullSourceThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.containsEdge(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testContainsEdgeOfNullTargetThrowsNPE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.containsEdge(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsEdgeWithSourceNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.containsEdge(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsEdgeWithTargetNodeNotInTheGraphThrowsIAE(){
        graph.addNode(1);
        graph.addNode(2);
        graph.addUndirectedEdge(1, 2);
        graph.containsEdge(1, 3);
    }
}
