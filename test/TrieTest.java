import datastructures.Trie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TrieTest {

    private Trie trie;

    @Before
    public void init(){
        trie = new Trie();
    }

    @Test
    public void testInitialTrieIsEmpty(){
        Assert.assertFalse(trie.contains(""));
        Assert.assertFalse(trie.contains("Test"));
        Assert.assertFalse(trie.contains(" "));
        Assert.assertFalse(trie.contains("word"));
    }

    @Test
    public void testNewlyAddedWordsAreContained(){
        String word = "Test";
        Assert.assertFalse(trie.contains(word));
        trie.add(word);
        Assert.assertTrue(trie.contains(word));

        word = "Word";
        Assert.assertFalse(trie.contains(word));
        trie.add(word);
        Assert.assertTrue(trie.contains(word));

        word = "word";
        Assert.assertFalse(trie.contains(word));
        trie.add(word);
        Assert.assertTrue(trie.contains(word));

        Set<String> set = new HashSet<>();
        set.add("datastructures.Trie");
        set.add("Try");
        set.add("Tree");

        for(String s : set){
            Assert.assertFalse(trie.contains(s));
        }

        trie.add(set);
        for(String s : set){
            Assert.assertTrue(trie.contains(s));
        }
    }

    @Test
    public void testExistingAddedWordsAreStillContained(){
        String word = "Test";
        Assert.assertFalse(trie.contains(word));
        trie.add(word);
        trie.add(word);
        Assert.assertTrue(trie.contains(word));

        Set<String> set = new HashSet<>();
        set.add("datastructures.Trie");
        set.add("Try");
        set.add("Tree");

        for(String s : set){
            Assert.assertFalse(trie.contains(s));
        }

        trie.add(set);
        trie.add(set);
        for(String s : set){
            Assert.assertTrue(trie.contains(s));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullThrowsNPE(){
        String s = null;
        trie.add(s);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullCollectionThrowsNPE(){
        Set<String> set = null;
        trie.add(set);
    }

    @Test(expected = NullPointerException.class)
    public void testContainsNullThrowsNPE(){
        trie.contains(null);
    }

    @Test
    public void testRemovedExistingWordIsNotContained(){
        String word = "Test";
        String word_2 = "datastructures.Trie";

        trie.add(word);
        trie.add(word_2);
        Assert.assertTrue(trie.contains(word));
        Assert.assertTrue(trie.contains(word_2));

        trie.remove(word);
        Assert.assertFalse(trie.contains(word));
        Assert.assertTrue(trie.contains(word_2));
    }

    @Test
    public void testRemovedNotExistingWordIsNotContained(){
        String word = "Test";
        String word_2 = "datastructures.Trie";

        trie.add(word);
        Assert.assertTrue(trie.contains(word));
        Assert.assertFalse(trie.contains(word_2));

        trie.remove(word_2);
        Assert.assertTrue(trie.contains(word));
        Assert.assertFalse(trie.contains(word_2));
    }

    @Test
    public void testClearRemovesAllWords(){
        Set<String> set = new HashSet<>();
        set.add("datastructures.Trie");
        set.add("Try");
        set.add("Tree");

        trie.add(set);
        for(String s : set)
            Assert.assertTrue(trie.contains(s));

        trie.clear();
        for(String s : set)
            Assert.assertFalse(trie.contains(s));
    }

    @Test
    public void testGetAllWordsStartingWithReturnsTheCorrectWords(){
        Set<String> set = new HashSet<>();
        set.add("datastructures.Trie");
        set.add("Try");
        set.add("Tree");
        set.add("Three");

        trie.add(set);
        String prefix = "Tr";
        Set<String> words = trie.getAllWordsStartingWith(prefix);
        Set<String> solutionSet = set.stream().filter(s -> s.startsWith(prefix)).collect(Collectors.toSet());

        Assert.assertEquals(solutionSet.size(), words.size());
        for(String word : words){
            Assert.assertTrue(solutionSet.contains(word));
        }

        String prefix_2 = "Tree";
        words = trie.getAllWordsStartingWith(prefix_2);
        solutionSet = set.stream().filter(s -> s.startsWith(prefix_2)).collect(Collectors.toSet());

        Assert.assertEquals(solutionSet.size(), words.size());
        for(String word : words){
            Assert.assertTrue(solutionSet.contains(word));
        }

        String prefix_3 = "Test";
        words = trie.getAllWordsStartingWith(prefix_3);
        solutionSet = set.stream().filter(s -> s.startsWith(prefix_3)).collect(Collectors.toSet());

        Assert.assertEquals(solutionSet.size(), words.size());
        for(String word : words){
            Assert.assertTrue(solutionSet.contains(word));
        }

        String prefix_4 = "";
        words = trie.getAllWordsStartingWith(prefix_4);
        solutionSet = set.stream().filter(s -> s.startsWith(prefix_4)).collect(Collectors.toSet());

        Assert.assertEquals(solutionSet.size(), words.size());
        for(String word : words){
            Assert.assertTrue(solutionSet.contains(word));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testGetAllWordsStartingWithNullThrowsNPE(){
        Set<String> set = new HashSet<>();
        set.add("datastructures.Trie");
        set.add("Try");
        set.add("Tree");
        set.add("Three");

        trie.add(set);
        trie.getAllWordsStartingWith(null);
    }
}
