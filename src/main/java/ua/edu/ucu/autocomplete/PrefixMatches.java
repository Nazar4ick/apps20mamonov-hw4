package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Comparator;


/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private final Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int counter = 0;
        for (String s : strings) {
            String[] words = s.split(" ");
            for (String word : words) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word, word.length()));
                    counter += 1;
                }
            }
        }
        return counter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() >= 2) {
            return trie.wordsWithPrefix(pref);
        }
        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            return null;
        }
        ArrayList<String> allWords = new ArrayList<>();
        for (String word : trie.wordsWithPrefix(pref)) {
            allWords.add(word);
        }
        allWords.sort(Comparator.comparingInt(String::length));
        int i = 0;
        int counter = 0;
        ArrayList<String> kLengthWords = new ArrayList<>();
        while (counter < k) {
            kLengthWords.add(allWords.get(i));
            i += 1;
            if (i == allWords.size()) {
                return allWords;
            }
            String nextWord = allWords.get(i);
            if (kLengthWords.get(kLengthWords.size()-1).length() != nextWord.length()) {
                counter += 1;
            }
        }
        return kLengthWords;
    }

    public int size() {
        return trie.size();
    }

    public static void main(String[] args) {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
        System.out.println(pm.wordsWithPrefix("abc", 3));
    }
}
