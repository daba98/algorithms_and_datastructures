package datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * A datastructure to efficiently handle range queries on a changing array. It provides the two basic operations update and query.
 * While you can modify the array with the update operation, you can perform range queries via the query operation. Both operations
 * have a runtime in O(log(n)) where n is the length of the array.
 * @param <T> the type of the elements in the segment tree
 */
public class SegmentTree<T> {

    private final int nboValues;
    private final ArrayList<Node<T>> nodes;
    private final BinaryOperator<T> func;

    /**
     * Initializes the segment tree with the initial values specified in the array values.
     * Note that a segment tree only works if (T, func) is a monoid.
     * @param values the initial values
     * @param func the operation to be applied to aggregate the values in a range query
     * @exception NullPointerException if values or func is null or if an element in values is null
     */
    public SegmentTree(T[] values, BinaryOperator<T> func){
        Objects.requireNonNull(values);
        Objects.requireNonNull(func);

        nboValues = values.length;
        int height = (int) Math.ceil(Math.log(nboValues) / Math.log(2));
        int nboNodes = (int) Math.pow(2, height + 1) - 1;
        nodes = new ArrayList<>(Collections.nCopies(nboNodes, null));
        this.func = getFunc(func);
        build(values, 0, 0, nboValues - 1);
    }

    private BinaryOperator<T> getFunc(BinaryOperator<T> func){
        return (t, t2) -> {
            if(t == null)
                return t2;
            if(t2 == null)
                return t;
            return func.apply(t, t2);
        };
    }

    private T build(T[] values, int idx, int lowerBound, int upperBound){
        Node<T> node = new Node<>(lowerBound, upperBound);
        nodes.set(idx, node);
        if(lowerBound == upperBound){
            T value = values[lowerBound];
            Objects.requireNonNull(value);
            node.value = value;
            return node.value;
        }
        int mid = (lowerBound + upperBound) / 2;
        node.value = func.apply(build(values, getIdxOfLeftChild(idx), lowerBound, mid), build(values, getIdxOfRightChild(idx), mid + 1, upperBound));
        return node.value;
    }

    private int getIdxOfLeftChild(int idx){
        return 2 * idx + 1;
    }

    private int getIdxOfRightChild(int idx){
        return 2 * idx + 2;
    }

    /**
     * Performs a range query in the range [lowerBound, upperBound| using the function func specified at construction time
     * @param lowerBound the index to be used as the lower bound in the range query
     * @param upperBound the index to be used as the upper bound in the range query
     * @return returns the aggregated value of the specified range using the function func specified at construction time
     * @exception IllegalArgumentException if lowerBound < 0 or upperBound >= the length of the array or upperBound < lowerBound
     */
    public T query(int lowerBound, int upperBound){
        if(lowerBound < 0)
            throw new IllegalArgumentException("lowerbound must not be less than zero!");
        if(upperBound >= nboValues)
            throw new IllegalArgumentException("upperbound must be at most " + (nboValues - 1));
        if(lowerBound > upperBound)
            throw new IllegalArgumentException("lowerbound must be smaller or equal to upperbound!");
        return query(0, lowerBound, upperBound);
    }

    private T query(int currentNodeIdx, int lowerBound, int upperBound){
        Node<T> node = nodes.get(currentNodeIdx);
        if(lowerBound > node.upperBound || upperBound < node.lowerBound)
            return null;

        if(lowerBound <= node.lowerBound && upperBound >= node.upperBound)
            return node.value;

        return func.apply(query(getIdxOfLeftChild(currentNodeIdx), lowerBound, upperBound), query(getIdxOfRightChild(currentNodeIdx), lowerBound, upperBound));
    }


    /**
     * Sets the value of the array at the index position to the specified new value
     * @param position the index of the value in the array that should be updated
     * @param value the new value
     * @exception NullPointerException if the new value is null
     * @exception IndexOutOfBoundsException if the index position is not in the bounds of the array
     */
    public void update(int position, T value){
        Objects.requireNonNull(value);
        if(position < 0 || position >= nboValues)
            throw new IndexOutOfBoundsException("position must be in the interval 0-" + (nboValues - 1) + ", but got " + position);
        update(position, value, 0, 0, nboValues - 1);
    }

    private T update(int position, T value, int currentNodeIdx, int lowerBound, int upperBound){
        Node<T> node = nodes.get(currentNodeIdx);
        if(position < lowerBound || position > upperBound)
            return node.value;
        else if(lowerBound == upperBound){
            node.value = value;
            return node.value;
        }
        else{
            int mid = (lowerBound + upperBound) / 2;
            T leftUpdate = update(position, value, getIdxOfLeftChild(currentNodeIdx), lowerBound, mid);
            T rightUpdate = update(position, value, getIdxOfRightChild(currentNodeIdx), mid + 1, upperBound);
            node.value = func.apply(leftUpdate, rightUpdate);
            return node.value;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(Node<T> node : nodes)
            s.append(node != null ? node + ", " : "DUMMY, ");
        return s + "]";
    }

    private static class Node<T> {
        T value;
        int lowerBound;
        int upperBound;

        public Node(int lowerBound, int upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        @Override
        public String toString() {
            return "(" + value + ", [" + lowerBound + ", " + upperBound + "]";
        }
    }
}
