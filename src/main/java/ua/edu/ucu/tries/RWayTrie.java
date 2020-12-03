package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;

import java.util.ArrayList;
import java.util.List;

public class RWayTrie implements Trie {

    private static final int R = 256; // radix
    private Node root = new Node(); // root of tree

    public static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    private Node get(Node x, String key, int d) {
        // return node associated with key in the subtrie rooted at x
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c], key, d+1);
    }

    private Node put(Node x, Tuple t, int d) {
        // Change value associated with key if in subtrie rooted at x
        if (x == null) {
            x = new Node();
        }
        if (d == t.term.length()) {
            x.val = t.weight;
            return x;
        }
        char c = t.term.charAt(d); // Use dth key char to identify subtrie.
        x.next[c] = put(x.next[c], t, d+1);
        return x;
    }

    @Override
    public void add(Tuple t) {
        root = put(root, t, 0);
    }

    @Override
    public boolean contains(String word) {
        Node x = get(root, word, 0);
        return x != null;
    }

    @Override
    public boolean delete(String word) {
        root = delete(root, word, 0);
        return root != null;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        List<String> words = new ArrayList<>();
        bfs(new WordNode(get(root, s, 0), s), words);
        return words;
    }

    public void bfs(WordNode node, List<String> words) {
        Queue q = new Queue();
        q.enqueue(node);
        while (q.size() != 0) {
            WordNode current = (WordNode) q.dequeue();
            if (current != null && current.node.val != null) {
                words.add(current.word);
            }
            for (char c = 0; c < R; c++) {
                if (current != null && current.node.next[c] != null) {
                    q.enqueue(new WordNode(current.node.next[c], current.word + c));
                }
            }
        }
    }

    private static class WordNode {
        private final Node node;
        private final String word;

        public WordNode(Node node, String word) {
            this.node = node;
            this.word = word;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        int cnt = 0;
        if (x.val != null) {
            cnt += 1;
        }
        for (char c = 0; c < R; c++) {
            cnt += size(x.next[c]);
        }
        return cnt;
    }

    public static void main(String[] args) {
        RWayTrie rWayTrie = new RWayTrie();
        rWayTrie.add(new Tuple("by", 2));
        rWayTrie.add(new Tuple("sea", 3));
        rWayTrie.add(new Tuple("sells", 5));
        rWayTrie.add(new Tuple("shells", 6));
        rWayTrie.add(new Tuple("shore", 5));
        rWayTrie.add(new Tuple("the", 3));
        rWayTrie.add(new Tuple("she", 3));
        System.out.println(rWayTrie.size());
    }
}
