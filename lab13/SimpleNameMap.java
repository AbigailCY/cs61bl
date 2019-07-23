import java.util.LinkedList;

public class SimpleNameMap {

    private LinkedList<Entry>[] arr;
    private int size;


    public SimpleNameMap() {
        arr =  new LinkedList[26];
        size = 0;
    }

    /* Returns the number of items contained in this map. */
    public int size() {
        return size;
    }

    private int hashcode(String key) {
        int hash = (key.charAt(0) - 'A');
        return Math.floorMod(hash, arr.length);
    }

    public void resize() {
        LinkedList<Entry>[] oldArr = new LinkedList[arr.length];
        System.arraycopy(arr, 0, oldArr, 0, arr.length);
        arr = new LinkedList[arr.length * 2];
        for (int i = 0; i < oldArr.length; i++) {
            if (oldArr[i] != null) {
                for (Entry temp : oldArr[i]) {
                    if (arr[hashcode(temp.key)] == null) {
                        arr[hashcode(temp.key)] = new LinkedList<>();
                    }
                    arr[hashcode(temp.key)].addLast(temp);
                }
            }
        }
    }

    /* Returns true if the map contains the KEY. */
    public boolean containsKey(String key) {
        if (arr[hashcode(key)] != null) {
            for (Entry temp : arr[hashcode(key)]) {
                if (temp.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }


    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public String get(String key) {
        if (containsKey(key)) {
            for (Entry temp : arr[hashcode(key)]) {
                if (temp.key.equals(key)) {
                    return temp.value;
                }
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(String key, String value) {
        if (containsKey(key)) {
            for (Entry temp : arr[hashcode(key)]) {
                if (temp.key.equals(key)) {
                    temp.value = value;
                }
            }
        } else {
            if (arr[hashcode(key)] == null) {
                arr[hashcode(key)] = new LinkedList<>();
            }
            arr[hashcode(key)].addLast(new Entry(key, value));
        }
        size += 1;

    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public String remove(String key) {
        if (containsKey(key)) {
            for (int i = 0; i < arr[hashcode(key)].size(); i++) {
                if (arr[hashcode(key)].get(i).key.equals(key)) {
                    String val = arr[hashcode(key)].get(i).value;
                    arr[hashcode(key)].remove(i);
                    size -= 1;
                    return val;
                }
            }
        }
        return null;
    }

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
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
//        LinkedList<Entry>[] arr = new LinkedList[26];
//        arr[0] = new LinkedList<>();
//        Entry aee = new Entry("Awa", "1");
//        arr[0].addLast(aee);


        SimpleNameMap newsm = new SimpleNameMap();
        newsm.put("Awa", "1");
        newsm.put("Ana","2");
        newsm.put("ana","2");
        System.out.println(newsm.get("Ana"));
        newsm.remove("Awa");
    }

}
