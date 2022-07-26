import datastructures.FibonacciHeap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FibonacciHeapTest {

    private FibonacciHeap<Integer, Integer> heap;

    @Before
    public void init(){
        heap = new FibonacciHeap<>();
    }

    @Test
    public void testHeapIsEmptyAfterConstruction(){
        Assert.assertTrue(heap.isEmpty());
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testHeapIsNotEmptyAfterInserting(){
        heap.insert(1, 1);
        Assert.assertFalse(heap.isEmpty());

        heap.insert(2, 2);
        Assert.assertFalse(heap.isEmpty());
    }

    @Test
    public void testSizeIncreasesByOneAfterEachNewInsertion(){
        int size = 0;
        Assert.assertEquals(size, heap.size());

        heap.insert(1, 1);
        Assert.assertEquals(++size, heap.size());

        heap.insert(2, 1);
        Assert.assertEquals(++size, heap.size());

        heap.insert(3, 2);
        Assert.assertEquals(++size, heap.size());
    }

    @Test
    public void testSizeIsNotIncreasedForInsertionOfExistingElement(){
        int size = 0;
        Assert.assertEquals(size, heap.size());

        heap.insert(1, 1);
        Assert.assertEquals(++size, heap.size());

        heap.insert(1, 1);
        Assert.assertEquals(size, heap.size());

        heap.insert(1, 0);
        Assert.assertEquals(size, heap.size());

        heap.insert(1, 5);
        Assert.assertEquals(size, heap.size());
    }

    @Test
    public void testHeapContainsElementAfterInsertion(){
        int element = 1;
        Assert.assertFalse(heap.contains(element));

        heap.insert(element, 1);
        Assert.assertTrue(heap.contains(element));

        int element_2 = 2;
        Assert.assertFalse(heap.contains(element_2));
        heap.insert(element_2, 0);
        Assert.assertTrue(heap.contains(element_2));

        heap.insert(element, 3);
        Assert.assertTrue(heap.contains(element));
    }

    @Test
    public void testMinIsNullForEmptyHeap(){
        Assert.assertTrue(heap.isEmpty());
        Assert.assertNull(heap.min());

        heap.insert(1, 1);
        heap.deleteMin();

        Assert.assertNull(heap.min());
    }

    @Test
    public void testAfterFirstInsertionMinEqualsTheInsertedElement(){
        Integer element = 1;
        heap.insert(element, 1);
        Assert.assertEquals(element, heap.min());
    }

    @Test
    public void testMinChangesAfterInsertionOfNewElementWithLowerPriority(){
        Integer element = 1;
        Integer priority = 2;
        heap.insert(element, priority);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        Integer priority_2 = 1;
        heap.insert(element_2, priority_2);
        Assert.assertEquals(element_2, heap.min());
    }

    @Test
    public void testMinDoesNotChangeAfterInsertionOfElementWithGreaterPriority(){
        Integer element = 1;
        Integer priority = 2;
        heap.insert(element, priority);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        Integer priority_2 = 4;
        heap.insert(element_2, priority_2);
        Assert.assertEquals(element, heap.min());
    }

    @Test
    public void testMinDoesNotChangeAfterInsertionOfExistingElement(){
        Integer element = 1;
        Integer priority = 2;
        heap.insert(element, priority);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        Integer priority_2 = 1;
        heap.insert(element_2, priority_2);
        Assert.assertEquals(element_2, heap.min());

        heap.insert(element, 0);
        Assert.assertEquals(element_2, heap.min());

        heap.insert(element, 5);
        Assert.assertEquals(element_2, heap.min());
    }

    @Test
    public void testDeleteMinReturnsTheElementWithMinimalPriority(){
        Integer element = 1;
        Integer priority = 2;
        heap.insert(element, priority);
        Assert.assertEquals(element, heap.min());

        Integer element_2 = 2;
        Integer priority_2 = 3;
        heap.insert(element_2, priority_2);
        Assert.assertEquals(element, heap.deleteMin());

        Assert.assertEquals(element_2, heap.deleteMin());
    }

    @Test
    public void testDeleteMinReducesTheSizeByOne(){
        heap.insert(1, 1);
        heap.insert(2,2);
        heap.insert(3, 2);

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
        int priority_1 = 1;
        int element_2 = 2;
        int priority_2 = 2;
        int element_3 = 3;
        int priority_3 = 3;
        heap.insert(element_1, priority_1);
        heap.insert(element_2, priority_2);
        heap.insert(element_3, priority_3);

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
    public void testMinPointsToTheElementThatIsRemovedByTheNextDeleteMinOperation(){
        heap.insert(1, 1);
        heap.insert(2, 2);
        heap.insert(3, 3);
        heap.insert(4, 2);

        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
        Assert.assertEquals(heap.min(), heap.deleteMin());
    }

    @Test
    public void testDecreaseKeyDoesNotChangeTheElement(){
        heap.insert(1, 1);
        heap.insert(2, 2);

        heap.decreaseKey(1, 0);
        Assert.assertFalse(heap.contains(0));
        Assert.assertTrue(heap.contains(1));

        heap.decreaseKey(2, 4);
        Assert.assertTrue(heap.contains(2));
        Assert.assertFalse(heap.contains(4));
    }

    @Test
    public void testDecreaseKeyToNewMinimumChangesTheMinimum(){
        heap.insert(1, 1);
        heap.insert(2, 2);
        heap.insert(3, 3);

        heap.decreaseKey(2, 0);
        Assert.assertEquals(Integer.valueOf(2), heap.min());
    }


    @Test(expected = NullPointerException.class)
    public void testContainsNullThrowsNPE(){
        heap.insert(1, 1);
        heap.contains(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteMinOnEmptyHeapThrowsNPE(){
        heap.deleteMin();
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullThrowsNPE(){
        heap.insert(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testDecreaseKeyWithNullElementThrowsNPE(){
        heap.decreaseKey(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testDecreaseKeyWithNullPriorityThrowsNPE(){
        heap.insert(2, 2);
        heap.decreaseKey( 2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyOnNonExistingElementThrowsIAE(){
        heap.decreaseKey( 2, 1);
    }
}
