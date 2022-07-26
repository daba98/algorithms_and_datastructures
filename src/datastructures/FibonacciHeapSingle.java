package datastructures;

import java.util.*;

/**
 * A priority-queue implementation that provides tha basic operations insert, contains, size, isEmpty, min, deleteMIn, and decreaseKey for
 * elements that represent their own priority/key.
 * The operations insert, contains, size, isEmpty, and min have constant runtime, the decreaseKey operation has amortized constant runtime
 * and the deleteMin operation has an amortized runtime in O(log(n)). Note that the implementation of the hash function of the elements should
 * be consistent with the equals implementation.
 * @param <E> the type of the elements to be stored in the Fibonacci-Heap
 */
public class FibonacciHeapSingle<E extends Comparable<E>> {

    private Node min;
    private int size;
    private final List<Node> rootList;
    private final Map<E, List<Node>> nodeMap;


    /**
     * Creates an empty Fibonacci-Heap.
     */
    public FibonacciHeapSingle(){
        min = null;
        size = 0;
        rootList = new LinkedList<>();
        nodeMap = new HashMap<>();
    }

    /**
     * Inserts the element into the Fibonacci-Heap.
     * @param element the element to be inserted
     * @exception NullPointerException if the element to be inserted is null
     */
    public void insert(E element){
        Objects.requireNonNull(element);

        Node node = new Node(element);
        rootList.add(node);
        size++;
        if(min == null || node.compareTo(min) < 0){
            min = node;
        }
        List<Node> list = nodeMap.getOrDefault(element, new LinkedList<>());
        list.add(node);
        nodeMap.put(element, list);
    }

    /**
     * Returns the minimum element currently in the Fibonacci-Heap or null if it is empty
     * @return Returns the minimum element currently in the Fibonacci-Heap or null if it is empty
     */
    public E min(){
        if(min == null)
            return null;
        else
            return min.elem;
    }

    /**
     * Returns true if there is no element in the Fibonacci-Heap
     * @return true if no element is in the Fibonacci-Heap
     */
    public boolean isEmpty(){return size == 0;}

    /**
     * Deletes and returns the minimum element that is in the Fibonacci-Heap.
     * @return Returns the minimum element that is in the Fibonacci-Heap
     * @exception NullPointerException if the Fibonacci-Heap is empty
     */
    public E deleteMin(){
        E minValue = min();
        Objects.requireNonNull(minValue);
        List<Node> list = nodeMap.get(minValue);
        list.remove(0);
        if(list.isEmpty())
            nodeMap.remove(minValue);
        else
            nodeMap.put(minValue, list);
        size--;
        rootList.remove(min);
        if(min.childList != null){
            rootList.addAll(min.childList);
        }
        consolidate();

        return minValue;
    }

    private void consolidate(){
        Map<Integer, Node> m = new HashMap<>();
        while(!rootList.isEmpty()){
            Node node = rootList.remove(0);
            while(m.getOrDefault(node.degree, null) != null){
                Node nodeWithSameDegree = m.get(node.degree);
                m.remove(node.degree);
                node = node.link(nodeWithSameDegree);
            }
            m.put(node.degree, node);
        }

        Node min = null;
        for(Node node : m.values()){
            node.parent = null;
            rootList.add(node);
            if(min == null || min.compareTo(node) > 0){
                min = node;
            }
        }
        this.min = min;
    }

    /**
     * If newElem is smaller than oldElem, then oldElem is removed from the Fibonacci-Heap and newElem is inserted
     * @param oldElem the element to be removed
     * @param newElem the element to be inserted
     * @exception NullPointerException if oldElem or newElem is null
     * @exception IllegalArgumentException if oldElem is not in the Fibonacci-Heap
     */
    public void decreaseKey(E oldElem, E newElem){
        Objects.requireNonNull(oldElem);
        Objects.requireNonNull(newElem);

        if(!nodeMap.containsKey(oldElem))
            throw new IllegalArgumentException(oldElem + " is not in the heap!");

        List<Node> list = nodeMap.get(oldElem);
        Node node = list.remove(0);
        if(list.isEmpty())
            nodeMap.remove(oldElem);
        else
            nodeMap.put(oldElem, list);
        if(node.elem.compareTo(newElem) <= 0) {
            list.add(node);
            nodeMap.put(oldElem, list);
            return;
        }

        node.elem = newElem;
        list = nodeMap.getOrDefault(newElem, new LinkedList<>());
        list.add(node);
        nodeMap.put(newElem, list);
        if(min.compareTo(node) > 0)
            min = node;

        if(isInRootList(node) || node.parent.elem.compareTo(node.elem) <= 0)
            return;

        do{
            Node parent = node.parent;
            cut(node);
            node = parent;
        }while(node.marked && !isInRootList(node));

        if(!isInRootList(node))
            node.mark();
    }

    private void cut(Node node){
        if(!isInRootList(node)){
            node.parent.degree--;
            node.parent.childList.remove(node);
            node.parent = null;
            rootList.add(node);
        }
    }

    private boolean isInRootList(Node node){
        return node.parent == null;
    }

    /**
     * Returns the number of elements currently in the Fibonacci-Heap.
     * @return Returns the number of elements currently in the Fibonacci-Heap.
     */
    public int size(){return size;}

    /**
     * Returns true if the element elem is currently in the Fibonacci-Heap
     * @param elem the element whose presence in the Fibonacci-Heap is checked
     * @return Returns true if the element elem is currently in the Fibonacci-Heap
     * @exception NullPointerException if elem is null
     */
    public boolean contains(E elem){
        Objects.requireNonNull(elem);
        return nodeMap.containsKey(elem);}

    public class Node implements Comparable<Node>{

        private E elem;
        private List<Node> childList;
        private Node parent;
        private boolean marked;
        private int degree;

        private Node(E elem){
            this.elem = elem;
            childList = null;
            parent = null;
            marked = false;
            degree = 0;
        }

        @Override
        public int compareTo(Node node) {
            return elem.compareTo(node.elem);
        }

        private void mark(){
            marked = true;
        }

        private void unmark(){marked = false;}

        private Node link(Node node){
            if(this.compareTo(node) < 0){
                if(childList == null){
                    childList = new LinkedList<>();
                }
                childList.add(node);
                node.parent = this;
                node.unmark();
                degree++;
                return this;
            }
            else{
                if(node.childList == null){
                    node.childList = new LinkedList<>();
                }
                node.childList.add(this);
                parent = node;
                this.unmark();
                node.degree++;
                return node;
            }
        }
    }
}
