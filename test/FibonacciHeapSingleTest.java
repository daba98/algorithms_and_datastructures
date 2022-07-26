import datastructures.FibonacciHeapSingle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FibonacciHeapSingleTest {

    private FibonacciHeapSingle<Integer> heap;

    @Before
    public void init(){
        heap = new FibonacciHeapSingle<>();
    }

    @Test
    public void testHeapIsEmptyAfterConstruction(){
        Assert.assertTrue(heap.isEmpty());
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testHeapIsNotEmptyAfterInserting(){
        heap.insert(1);
        Assert.assertFalse(heap.isEmpty());

        heap.insert(2);
        Assert.assertFalse(heap.isEmpty());
    }

    @Test
    public void testSizeIncreasesByOneAfterEachInsertion(){
        int size = 0;
        Assert.assertEquals(size, heap.size());

        heap.insert(1);
        Assert.assertEquals(++size, heap.size());

        heap.insert(2);
        Assert.assertEquals(++size, heap.size());

        heap.insert(2);
        Assert.assertEquals(++size, heap.size());
    }

    @Test
    public void testHeapContainsElementAfterInsertion(){
        int element = 1;
        Assert.assertFalse(heap.contains(element));

        heap.insert(element);
        Assert.assertTrue(heap.contains(element));
    }

    @Test
    public void testMinIsNullForEmptyHeap(){
        Assert.assertTrue(heap.isEmpty());
        Assert.assertNull(heap.min());

        heap.insert(1);
        heap.deleteMin();

        Assert.assertNull(heap.min());
    }

    @Test
    public void testAfterFirstInsertionMinEqualsTheInsertedElement(){
        Integer element = 1;
        heap.insert(element);
        Assert.assertEquals(element, heap.min());
    }

    @Test
    public void testMinChangesAfterInsertionOfSmallerElement(){
        Integer element = 1;
        heap.insert(element);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 0;
        heap.insert(element_2);
        Assert.assertEquals(element_2, heap.min());
    }

    @Test
    public void testMinDoesNotChangeAfterInsertionOfGreaterElement(){
        Integer element = 1;
        heap.insert(element);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        heap.insert(element_2);
        Assert.assertEquals(element, heap.min());
    }

    @Test
    public void testDeleteMinReturnsTheMinimumElement(){
        Integer element = 1;
        heap.insert(element);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        heap.insert(element_2);
        Assert.assertEquals(element, heap.deleteMin());

        Assert.assertEquals(element_2, heap.deleteMin());
    }

    @Test
    public void testDeleteMinReducesTheSizeByOne(){
        heap.insert(1);
        heap.insert(2);
        heap.insert(2);

        int size = 3;
        Assert.assertEquals(size, heap.size());

        heap.deleteMin();
        Assert.assertEquals(--size, heap.size());

        heap.deleteMin();
        Assert.assertEquals(--size, heap.size());

        heap.deleteMin();
        Assert.assertEquals(--size, heap.size());
    }

    @Test
    public void testMinimumElementIsNotContainedAfterDeleteMin(){
        int element_1 = 1;
        int element_2 = 2;
        int element_3 = 3;
        heap.insert(element_1);
        heap.insert(element_2);
        heap.insert(element_3);

        Assert.assertTrue(heap.contains(element_1));
        Assert.assertTrue(heap.contains(element_2));
        Assert.assertTrue(heap.contains(element_3));

        heap.deleteMin();
        Assert.assertFalse(heap.contains(element_1));
        Assert.assertTrue(heap.contains(element_2));
        Assert.assertTrue(heap.contains(element_3));

        heap.deleteMin();
        Assert.assertFalse(heap.contains(element_1));
        Assert.assertFalse(heap.contains(element_2));
        Assert.assertTrue(heap.contains(element_3));

        heap.deleteMin();
        Assert.assertFalse(heap.contains(element_1));
        Assert.assertFalse(heap.contains(element_2));
        Assert.assertFalse(heap.contains(element_3));
    }

    @Test
    public void testDeleteMinOfDuplicateMinRemovesOnlyOneInstance(){
        heap.insert(1);
        heap.insert(1);
        heap.insert(1);

        Assert.assertTrue(heap.contains(1));

        heap.deleteMin();
        Assert.assertTrue(heap.contains(1));

        heap.deleteMin();
        Assert.assertTrue(heap.contains(1));

        heap.deleteMin();
        Assert.assertFalse(heap.contains(1));
    }

    @Test
    public void testMinPointsToTheElementThatisRemovedByTheNextDeleteMinOperation(){
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(2);

        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
    }

    @Test
    public void testDecreaseKeyWithSmallerNewKeyChangesTheElement(){
        heap.insert(1);
        heap.insert(2);

        heap.decreaseKey(1, 0);
        Assert.assertTrue(heap.contains(0));
        Assert.assertFalse(heap.contains(1));

        heap.decreaseKey(2, 1);
        Assert.assertTrue(heap.contains(1));
        Assert.assertFalse(heap.contains(2));
    }

    @Test
    public void testDecreaseKeyWithGreaterOrEqualNewKeyDoesNotChangesTheElement(){
        heap.insert(1);
        heap.insert(2);

        heap.decreaseKey(1, 3);
        Assert.assertTrue(heap.contains(1));
        Assert.assertFalse(heap.contains(3));

        heap.decreaseKey(2, 2);
        Assert.assertTrue(heap.contains(2));
    }

    @Test
    public void testDecreaseKeyOnDuplicateElementChangesOnlyOneElement(){
        heap.insert(1);
        heap.insert(1);

        heap.decreaseKey(1, 0);
        Assert.assertTrue(heap.contains(0));
        Assert.assertTrue(heap.contains(1));
    }

    @Test
    public void testDecreaseKeyToNewMinimumChangesTheMinimum(){
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);

        Integer min = heap.min();
        Integer newMin = min - 1;
        heap.decreaseKey(min, newMin);
        Assert.assertEquals(newMin, heap.min());

        newMin = newMin - 1;
        heap.decreaseKey(3, newMin);
        Assert.assertEquals(newMin, heap.min());
    }


    @Test(expected = NullPointerException.class)
    public void testContainsNullThrowsNPE(){
        heap.insert(1);
        heap.contains(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteMinOnEmptyHeapThrowsNPE(){
        heap.deleteMin();
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullThrowsNPE(){
        heap.insert(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDecreaseKeyWithNullKeyThrowsNPE(){
        heap.decreaseKey(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testDecreaseKeyWithNullNewKeyThrowsNPE(){
        heap.insert(2);
        heap.decreaseKey( 2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyOnNonExistingKeyThrowsIAE(){
        heap.decreaseKey( 2, 1);
    }
}
