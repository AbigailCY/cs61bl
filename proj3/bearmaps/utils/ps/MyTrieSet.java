package bearmaps.utils.ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL {

    Nodes root;
    int size;

    public MyTrieSet() {
        root = new Nodes();
        size = 0;
    }


    @Override
    public void clear() {
        root = new Nodes();
        size = 0;

    }

    @Override
    public boolean contains(String key) {
        Nodes curr = root;

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
        Nodes curr = root;

        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        List<String> keys = new ArrayList<>();
        String key = prefix;
        if (curr.isKey) {
            keys.add(prefix);
        }

        for (char in : curr.map.keySet()) {
            colHelp(key + in, keys, curr.map.get(in));
        }

        return keys;
    }


    public void colHelp(String s, List<String> x, Nodes n) {
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
        Nodes curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Nodes(c, false));
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


    private class Nodes {
        char me;
        boolean isKey;
        HashMap<Character, Nodes> map;

        Nodes(char c, boolean key) {
            me = c;
            isKey = key;
            map = new HashMap<>();
        }

        Nodes() {
            me = '\u0000';
            isKey = false;
            map = new HashMap<>();
        }
    }

//    public static void main(String[] args) {
//        MyTrieSet t = new MyTrieSet();
//        t.add("hello");
//        t.add("hi");
//        t.add("help");
//        t.add("zebra");
//    }

}
