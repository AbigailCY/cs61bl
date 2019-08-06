import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL {

    Node root;
    int size;

    MyTrieSet() {
        root = new Node();
        size = 0;
    }


    @Override
    public void clear() {
        root = new Node();
        size = 0;

    }

    @Override
    public boolean contains(String key) {
        Node curr = root;

        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        if (curr == null) {
            return false;
        }
        return curr.isKey;
    }


    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node curr = root;

        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        List<String> keys = new ArrayList<>();
        String key = prefix;

        for (char in : curr.map.keySet()) {
            colHelp(key + in, keys, curr.map.get(in));
        }

        return keys;
    }


    public void colHelp(String s, List<String> x, Node n) {
        if (n == null) {
            return;
        }
        if (n.isKey) {
            x.add(s);
        }
        for (char in : n.map.keySet()) {
            colHelp(s + in, x, n.map.get(in));
        }

    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
        size += 1;
    }




    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }


    private class Node {
        char me;
        boolean isKey;
        HashMap<Character, Node> map;

        Node(char c, boolean key) {
            me = c;
            isKey = key;
            map = new HashMap<>();
        }

        Node() {
            me = '\u0000';
            isKey = false;
            map = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
    }

}
