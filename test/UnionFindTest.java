import datastructures.UnionFind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UnionFindTest {

    private UnionFind<Integer> unionFind;

    @Before
    public void init(){
        unionFind = new UnionFind<>();
    }

    @Test
    public void testFindNewElementReturnsItselfAsRoot(){
        Assert.assertEquals((Integer) 42, unionFind.find(42));
        Assert.assertEquals((Integer) 43, unionFind.find(43));
    }
    @Test
    public void testFindExistingSingleSetElementReturnsItselfAsRoot(){
        unionFind.find(42);
        Assert.assertEquals((Integer) 42, unionFind.find(42));
    }

    @Test(expected = NullPointerException.class)
    public void testFindNullThrowsNPE()
    {
        unionFind.find(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUnionBothNullThrowsNPE()
    {
        unionFind.union(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testUnionFirstNullThrowsNPE()
    {
        unionFind.union(null, 42);
    }

    @Test(expected = NullPointerException.class)
    public void testUnionSecondNullThrowsNPE()
    {
        unionFind.union(42, null);
    }

    @Test
    public void testUnionReturnsSameRootAsFind(){
        Integer elem_1 = 1;
        Integer elem_2 = 2;
        Integer root1 = unionFind.union(elem_1, elem_2);
        Assert.assertEquals(root1, unionFind.find(elem_1));
        Assert.assertEquals(root1, unionFind.find(elem_2));

        Integer elem_3 = 3;
        Integer elem_4 = 4;
        Integer root2 = unionFind.union(elem_3, elem_4);
        Assert.assertEquals(root1, unionFind.find(elem_1));
        Assert.assertEquals(root1, unionFind.find(elem_2));
        Assert.assertEquals(root2, unionFind.find(elem_3));
        Assert.assertEquals(root2, unionFind.find(elem_4));

        Integer root = unionFind.union(elem_1, elem_4);
        Assert.assertEquals(root, unionFind.find(elem_1));
        Assert.assertEquals(root, unionFind.find(elem_2));
        Assert.assertEquals(root, unionFind.find(elem_3));
        Assert.assertEquals(root, unionFind.find(elem_4));
    }

    @Test
    public void testRootIsAlwaysElementOfWholeSet(){
        Integer elem_1 = 1;
        Integer elem_2 = 2;
        Set<Integer> set1 = new HashSet<>();
        set1.add(elem_1);
        set1.add(elem_2);
        Assert.assertTrue(set1.contains(unionFind.union(elem_1, elem_2)));

        Integer elem_3 = 3;
        Integer elem_4 = 4;
        Set<Integer> set2 = new HashSet<>();
        set2.add(elem_3);
        set2.add(elem_4);
        Assert.assertTrue(set2.contains(unionFind.union(elem_3, elem_4)));

        set1.addAll(set2);
        Assert.assertTrue(set1.contains(unionFind.union(elem_1, elem_4)));
    }
}
