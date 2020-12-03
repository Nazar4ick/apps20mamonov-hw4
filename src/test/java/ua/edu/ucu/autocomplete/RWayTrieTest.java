package ua.edu.ucu.autocomplete;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

import java.util.List;


public class RWayTrieTest {
    private RWayTrie rWayTrie;

    @Before
    public void init() {
        rWayTrie = new RWayTrie();
        rWayTrie.add(new Tuple("by", 2));
        rWayTrie.add(new Tuple("sea", 3));
        rWayTrie.add(new Tuple("sells", 5));
        rWayTrie.add(new Tuple("shells", 6));
        rWayTrie.add(new Tuple("shore", 5));
        rWayTrie.add(new Tuple("the", 3));
        rWayTrie.add(new Tuple("she", 3));
    }

    @Test
    public void testTrueContains() {
        boolean contains = rWayTrie.contains("she");
        assertTrue(contains);
    }

    @Test
    public void testFalseContains() {
        boolean contains = rWayTrie.contains("abrakadabra");
        assertFalse(contains);
    }

    @Test
    public void testDelete() {
        assertTrue(rWayTrie.delete("she"));
    }

    @Test
    public void testWords() {
        List<String> words = (List<String>) rWayTrie.words();
        String[] w = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            w[i] = words.get(i);
        }
        String[] expected = new String[]{"by", "sea", "she", "the", "sells", "shore", "shells"};
        assertArrayEquals(expected, w);
    }

    @Test
    public void testWordsWithPrefix() {
        List<String> words = (List<String>) rWayTrie.wordsWithPrefix("s");
        String[] w = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            w[i] = words.get(i);
        }
        String[] expected = new String[]{"sea", "she", "sells", "shore", "shells"};
        assertArrayEquals(expected, w);
    }

    @Test
    public void testSize() {
        int expected = 7;
        assertEquals(rWayTrie.size(), expected);
    }
}
