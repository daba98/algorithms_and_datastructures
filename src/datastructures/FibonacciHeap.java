package datastructures;

import java.util.*;

/**
 * A priority-queue implementation that provides tha basic operations insert, contains, size, isEmpty, min, deleteMin, and decreaseKey.
 * The operations insert, contains, size, isEmpty, and min have constant runtime, the decreaseKey operation has amortized constant runtime
 * and the deleteMin operation has an amortized runtime in O(log(n)). Note that it is not possible to add duplicates.
 * @param <V> the type of the elements to be stored in the Fibonacci-Heap
 * @param <K> the type of the priority
 */
public class FibonacciHeap<K extends Comparable<K>, V>{

    private Node min;
    private int size;
    private final List<Node> rootList;
    private final Map<V, Node> nodeMap;


    /**
     * Creates an empty Fibonacci-Heap
     */
    public FibonacciHeap(){
        min = null;
        size = 0;
        rootList = new LinkedList<>();
        nodeMap = new HashMap<>();
    }

    /**
     * Inserts the element elem with the priority into the Fibonacci-Heap, if it is not already in the Fibonacci-Heap.
     * If the element is already in the Fibonacci-Heap, then nothing changes, even if the priority is different from the
     * priority of the element in the Fibonacci-Heap.
     * @param elem the element to be inserted
     * @param priority the priority of the element that is to be inserted
     * @exception NullPointerException if the element to be inserted is null
     */
    public void insert(V elem, K priority){
        Objects.requireNonNull(elem);
        if(contains(elem))
            return;
        Node node = new Node(elem, priority);
        rootList.add(node);
        size++;
        if(min == null || node.compareTo(min) < 0){
            min = node;
        }
        nodeMap.put(elem, node);
    }

    /**
     * Returns the minimum element currently in the Fibonacci-Heap or null if it is empty
     * @return Returns the minimum element currently in the Fibonacci-Heap or null if it is empty
     */
    public V min(){
        if(min == null || isEmpty())
            return null;
        else
            return min.value;
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
    public V deleteMin(){
        V minValue = min();
        Objects.requireNonNull(minValue);
        nodeMap.remove(minValue);
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
     * If the new priority is smaller than the current priority of the element elem, then the priority of the element
     * is decreased to the new priority
     * @param elem the element whose priotity is to be decreased
     * @param priority the target priority
     * @exception NullPointerException if elem is null
     * @exception IllegalArgumentException if elem is not in the Fibonacci-Heap
     */
    public void decreaseKey(V elem, K priority){
        Objects.requireNonNull(elem);
        Objects.requireNonNull(priority);

        if(!contains(elem))
            throw new IllegalArgumentException(elem + " is not in the heap!");
        Node node = nodeMap.getOrDefault(elem, null);
        if(node == null || node.key.compareTo(priority) <= 0)
            return;

        node.key = priority;
        if(min.compareTo(node) > 0)
            min = node;

        if(isInRootList(node) || node.parent.key.compareTo(node.key) <= 0)
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

    /**
     * Returns the number of elements currently in the Fibonacci-Heap.
     * @return Returns the number of elements currently in the Fibonacci-Heap.
     */
    private boolean isInRootList(Node node){
        return node.parent == null;
    }

    public int size(){return size;}

    /**
     * Returns true if the element elem is currently in the Fibonacci-Heap
     * @param elem the element whose presence in the Fibonacci-Heap is checked
     * @return Returns true if the element elem is currently in the Fibonacci-Heap
     * @exception NullPointerException if elem is null
     */
    public boolean contains(V elem){
        Objects.requireNonNull(elem);
        return nodeMap.containsKey(elem);}

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("min: " + (min == null ? "null\n" : "(" + min.key + ", " + min.value + ")\n"));
        for(Node n : rootList){
            res.append(n.toString()).append("\n\n");
        }
        return res.toString();
    }

    public class Node implements Comparable<Node>{

        private K key;
        private final V value;
        private List<Node> childList;
        private Node parent;
        private boolean marked;
        private int degree;

        private Node(V value, K key){
            this.key = key;
            this.value = value;
            childList = null;
            parent = null;
            marked = false;
            degree = 0;
        }

        @Override
        public int compareTo(Node node) {
            return key.compareTo(node.key);
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

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder("(" + key + ", " + value + ")\nmarked: " + marked + ", degree: " + degree + ", parent key: "
                    + (parent == null ? "---" : parent.key) + "\nChildren:\n");
            if(childList == null)
                res.append("---");
            else{
                for(Node n : childList)
                    res.append(n.toString());
            }
            return res + "\n\n";
        }
    }
}
