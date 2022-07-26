package datastructures;

import java.util.*;

/**
 * A datastructure to efficiently store a set a strings. It provides basic functions like add, remove, contains and clear.
 * Additionally, it provides a method to retrieve all strings in the datastructure that start with a given prefix.
 */
public class Trie {

    private Node root;

    /**
     * Creates an empty trie.
     */
    public Trie(){
        root = new Node();
    }

    /**
     * Adds the string word to the trie if it is not contained yet.
     * @param word the string to be added to the trie
     * @exception NullPointerException If the string word is null
     */
    public void add(String word){
        Objects.requireNonNull(word);

        Node currentNode = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            Node next = currentNode.getNextNode(c);
            if(next == null){
                next = new Node();
                currentNode.addEdge(c, next);
            }
            currentNode = next;
        }

        currentNode.mark();
    }

    /**
     * Adds all the strings in the collection words to the trie if they are not contained yet.
     * @param words the collection of strings to be added to the trie
     * @exception NullPointerException If the collection words is null
     */
    public void add(Collection<String> words){
        Objects.requireNonNull(words);

        for(String word : words)
            add(word);
    }

    /**
     * Removes the string word from the trie if it is contained.
     * @param word the string to be removed from the trie
     * @exception NullPointerException If the string word is null
     */
    public void remove(String word){
        Objects.requireNonNull(word);

        Node node = getNode(word);
        if(node != null)
            node.unmark();
    }

    /**
     * Clears the trie such that it is completely empty afterwards.
     */
    public void clear(){
        root = new Node();
    }

    /**
     * Returns true if the trie contains the string word
     * @param word string whose presence in the trie is to be tested
     * @return true if the trie contains the string word
     * @exception NullPointerException If the string word is null
     */
    public boolean contains(String word){
        Objects.requireNonNull(word);

        Node node = getNode(word);
        return node != null && node.isMarked();
    }

    private Node getNode(String word){
        Node currentNode = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            Node next = currentNode.getNextNode(c);
            if(next == null){
                return null;
            }
            currentNode = next;
        }

        return currentNode;
    }

    /**
     * Returns all strings in the trie starting with the specified prefix
     * @param prefix the string specifying the begining of the words
     * @return a set of all strings in the trie starting with the specified prefix
     * @exception NullPointerException If the string prefix is null
     */
    public Set<String> getAllWordsStartingWith(String prefix){
        Objects.requireNonNull(prefix);

        Set<String> words = new HashSet<>();
        Node node = getNode(prefix);
        if(node != null) {
            for (String affix : getAllWordsStartingAt(node))
                words.add(prefix + affix);
        }

        return words;
    }

    private Set<String> getAllWordsStartingAt(Node startingNode){
        Set<String> words = new HashSet<>();
        if(startingNode.isMarked())
            words.add("");
        for(Map.Entry<Character, Node> entry : startingNode.edges.entrySet()){
            char c = entry.getKey();
            Node node = entry.getValue();
            if(node.isMarked())
                words.add(String.valueOf(c));
            for(String affix : getAllWordsStartingAt(node))
                words.add(c + affix);
        }

        return words;
    }


    private static class Node{

        private final Map<Character, Node> edges;
        private boolean marked;

        public Node(){
            edges = new HashMap<>();
            marked = false;
        }

        public void mark(){
            marked = true;
        }

        public void unmark(){
            marked = false;
        }

        public boolean isMarked(){ return marked;}

        public Node getNextNode(char c){
            return edges.getOrDefault(c, null);
        }

        public void addEdge(char c, Node succ){
            edges.put(c, succ);
        }

    }
}
