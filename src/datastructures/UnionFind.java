package datastructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A datastructure to manage a collection of disjoint sets. It provides the two basic functions find and union.
 * The implementation provides worst-case constant runtime for the union operation and logarithmic runtime for the find operation.
 * Also it is guaranteed that a series of m find and n-1 union operations has a runtime in O(n + m*a(n)), where a(n) is the inverse of
 * the Ackermann-function. a(n) is incredibly slowly increasing, e.g for n < 10e80 it holds a(n) < 5.
 * @param <T> the type of the elements in the disjoint sets
 */
public class UnionFind<T> {

    private final Map<T, T> parents;
    private final Map<T, Integer> sizes;

    /**
     * Creates an empty union-find datastructure
     */
    public UnionFind(){
        parents = new HashMap<>();
        sizes = new HashMap<>();
    }

    /**
     * Returns the representative element of the set that contains the element elem.
     * If none of the disjoint sets contains the element elem, then a new set is created that contains only the element elem.
     * @param elem element whose set's representative element is to be found
     * @return Returns the representative element of the set that contains the element elem
     * @exception NullPointerException If the element elem is null
     */
    public T find(T elem){
        Objects.requireNonNull(elem);

        T parent = parents.getOrDefault(elem, null);
        if(parent == null){
            parents.put(elem, elem);
            sizes.put(elem, 1);
            return elem;
        }
        else if(parent.equals(elem)){
            return elem;
        }
        else{
            parents.put(elem, find(parent));
            return parents.get(elem);
        }
    }

    /**
     * Unites the two sets containing the elements elemA and elemB and returns the representative of that new set.
     * If none of the sets contains the element elemA, then a new set is created that contains only the element elemA.
     * Equivalently, if none of the sets contains the element elemB, then a new set is created that contains only the element elemB.
     * @param elemA element specifying the set to get united with the set containing elemB
     * @param elemB element specifying the set to get united with the set containing elemA
     * @return Returns the representative of the union of the two sets containing elemA and elemB
     * @exception NullPointerException If either elemA or elemB is null
     */
    public T union(T elemA, T elemB){
        Objects.requireNonNull(elemA);
        Objects.requireNonNull(elemB);
        T rootA = find(elemA);
        T rootB = find(elemB);

        if(rootA.equals(rootB))
            return rootA;

        Integer sizeA = sizes.getOrDefault(elemA, 1);
        Integer sizeB = sizes.getOrDefault(elemB, 1);
        if(sizeA < sizeB){
            parents.put(rootA, rootB);
            sizes.put(rootB, sizeA + sizeB);
            return rootB;
        }
        else{
            parents.put(rootB, rootA);
            sizes.put(rootA, sizeA + sizeB);
            return rootA;
        }
    }
}
