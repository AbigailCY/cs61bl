import java.util.Iterator;
import java.util.LinkedList;


public class HashMap<K, V> implements Map61BL<K, V>, Iterable<K> {

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
        if (containsKey(key) && get(key) == value) {
            remove(key);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator(size);
    }

    private class HashMapIterator implements Iterator<K> {
        private int number;
        private int currNum;
        private int[] pos;




        HashMapIterator(int number) {
            this.number = number;
            this.currNum = 0;
            this.pos = new int[]{0, 0};

        }

        @Override
        public boolean hasNext() {
            if (currNum < number) {
                return true;
            }
            return false;
        }

        @Override
        public K next() {
            while (hasNext()) {
                while (map[pos[0]] == null) {
                    pos[0] += 1;
                }
                K toReturn = map[pos[0]].get(pos[1]).key;
                currNum += 1;
                if (pos[1] < map[pos[0]].size() - 1) {
                    pos[1] += 1;
                } else {
                    pos[0] += 1;
                    pos[1] = 0;
                }

                return toReturn;
            }
            return null;
        }

        @Override
        public void remove() {
            while (map[pos[0]] == null) {
                pos[0] += 1;
            }
            map[pos[0]].remove(pos[1]);
        }
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


//    public static void main(String[] args) {
//        HashMap<String, Integer> studentIDs = new HashMap<String, Integer>();
//        studentIDs.put("christine", 12345);
//        studentIDs.put("itai", 345);
//        studentIDs.put("jon", 162);
//        studentIDs.put("zoe", 12345);
//        HashSet<String> output = new HashSet<String>();
//        for (String name : studentIDs) {
//            output.add(name);
//        }
//        HashMap<String, String> dictionary = new HashMap<String, String>();
//
//
//        // can put objects in dictionary and get them
//        dictionary.put("won", "charles");
//
//
//        // putting with existing key replaces key
//        dictionary.put("won", "brandon");
//    }
//
}
