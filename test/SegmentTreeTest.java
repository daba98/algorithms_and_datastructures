import datastructures.SegmentTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.BinaryOperator;

public class SegmentTreeTest {

    private SegmentTree<Integer> segmentTree;
    private Integer[] values;

    @Before
    public void init(){
        values = new Integer[10];
        for(int i = 0; i < values.length; i++)
            values[i] = i+1;

        BinaryOperator<Integer> add = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        };

        segmentTree = new SegmentTree<Integer>(values, add);
    }

    @Test
    public void testRangeQueryReturnsCorrectSumWithoutUpdateOperation(){
        for(int lowerBound = 0; lowerBound < values.length; lowerBound++){
            Integer correctSum = 0;
            for(int upperBound = lowerBound; upperBound < values.length; upperBound++){
                correctSum += values[upperBound];
                Assert.assertEquals(correctSum, segmentTree.query(lowerBound, upperBound));
            }
        }
    }

    @Test
    public void testUpdateChangesTheValue(){
        Integer newValue = 12;
        int position = 3;
        segmentTree.update(position, newValue);

        Assert.assertEquals(newValue, segmentTree.query(position, position));
    }

    @Test
    public void testRangeQueryReturnsCorrectSumAfterUpdateOperation(){
        Integer newValue = 12;
        int position = 7;
        segmentTree.update(position, newValue);
        values[position] = newValue;

        for(int lowerBound = 0; lowerBound < values.length; lowerBound++){
            Integer correctSum = 0;
            for(int upperBound = lowerBound; upperBound < values.length; upperBound++){
                correctSum += values[upperBound];
                Assert.assertEquals(correctSum, segmentTree.query(lowerBound, upperBound));
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNullThrowsNPE(){
        segmentTree.update(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdateNegativePositionThrowsIOOBE(){
        segmentTree.update(-1, 42);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testUpdatePositionLargerOrEqualThanArrayLengthThrowsIOOBE(){
        segmentTree.update(values.length, 42);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryNegativeLowerBoundThrowsIAE(){
        segmentTree.query(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryLargerOrEqualThanArrayLengthUpperBoundThrowsIAE(){
        segmentTree.query(1, values.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueryLargerLowerBoundThanUpperBoundThrowsIAE(){
        segmentTree.query(3, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullElementInArrayThrowsNPE(){
        Integer[] values = new Integer[2];
        values[0] = 1;
        values[1] = null;

        segmentTree = new SegmentTree<>(values, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullArrayThrowsNPE(){
        segmentTree = new SegmentTree<>(null, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullFuncThrowsNPE(){
        Integer[] values = new Integer[2];
        values[0] = 1;
        values[1] = null;

        segmentTree = new SegmentTree<>(values, null);
    }
}
