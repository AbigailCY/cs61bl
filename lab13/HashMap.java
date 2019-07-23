import java.util.Iterator;
import java.util.LinkedList;
//import java.util.Map;

public class HashMap<K, V> implements Map61BL<K, V> {

    private LinkedList<Entry<K, V>>[] map;
    private double maxFactor;
    private int size;


    public HashMap() {
        map = (LinkedList<Entry<K, V>>[]) new LinkedList[16];
        maxFactor = 0.75;
        size = 0;
    }

    public HashMap(int initialCapacity) {
        map = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        maxFactor = 0.75;
        size = 0;
    }

    public HashMap(int initialCapacity, double loadFactor) {
        map = (LinkedList<Entry<K, V>>[]) new LinkedList[initialCapacity];
        maxFactor = loadFactor;
        size = 0;
    }

    public int capacity() {
        return map.length;
    }




    @Override
    public void clear() {
        map = (LinkedList<Entry<K, V>>[]) new LinkedList[map.length];
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = Math.floorMod(key.hashCode(), map.length);
        if (map[hash] != null) {
            for (Entry<K, V> temp : map[hash]) {
                if (temp.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public V get(K key) {

        int hash = Math.floorMod(key.hashCode(), map.length);
        if (containsKey(key)) {
            for (Entry<K, V> temp : map[hash]) {
                if (temp.key.equals(key)) {
                    return temp.value;
                }
            }
        }
        return null;
    }

    public void resize() {

        LinkedList<Entry<K, V>>[] oldMap = (LinkedList<Entry<K, V>>[]) new LinkedList[map.length];
        System.arraycopy(map, 0, oldMap, 0, map.length);
        map = (LinkedList<Entry<K, V>>[]) new LinkedList[map.length * 2];
        for (int i = 0; i < oldMap.length; i++) {
            if (oldMap[i] != null) {
                for (Entry<K, V> temp : oldMap[i]) {
                    int hash = Math.floorMod(temp.key.hashCode(), map.length);
                    if (map[hash] == null) {
                        map[hash] = new LinkedList<>();
                    }
                    map[hash].addLast(temp);
                }
            }
        }
    }

    @Override
    public void put(K key, V value) {

        int hash = Math.floorMod(key.hashCode(), map.length);

        if (containsKey(key)) {
            for (Entry<K, V> temp : map[hash]) {
                if (temp.key.equals(key)) {
                    temp.value = value;
                    return;
                }
            }
        } else {
            size += 1;
            if ((double) size / map.length > maxFactor) {
                resize();
            }
            if (map[hash] == null) {
                map[hash] = new LinkedList<>();
            }
            map[hash].addLast((Entry<K, V>) new Entry(key, value));
        }
    }

    @Override
    public V remove(K key) {

        int hash = Math.floorMod(key.hashCode(), map.length);
        if (containsKey(key)) {
            for (int i = 0; i < map[hash].size(); i++) {
                if (map[hash].get(i).key.equals(key)) {
                    V val = map[hash].get(i).value;
                    map[hash].remove(i);
                    size -= 1;
                    return val;
                }
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }


    public static void main(String[] args) {
//        HashMap<String, String> dictionary = new HashMap<String, String>();
//
//
//        // can put objects in dictionary and get them
//        dictionary.put("won", "charles");
//
//
//        // putting with existing key replaces key
//        dictionary.put("won", "brandon");
    }

}
