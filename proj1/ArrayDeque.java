
public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        this.items = (T[]) new Object[8];
        this.size = 0;
        this.nextFirst = 1;
        this.nextLast = 2;
    }


    private void resize (int capacity){
        T[] a = (T[]) new Object[capacity];

        if ((nextFirst + size) < items.length) {
            System.arraycopy(items, (nextFirst + 1) % items.length, a, 0, size);
        } else {
            System.arraycopy(items, (nextFirst + 1) % items.length, a, 0, items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, items.length - nextFirst - 1, size - items.length + nextFirst + 1);
        }
        this.items = a;
    }

    public void addFirst(T item) {

        if (size == items.length) {
            resize(size * 2);
            items[items.length - 1] = item;
            this.nextFirst = items.length - 2;
            this.size += 1;
            this.nextLast = size - 1;

        } else {

        items[nextFirst] = item;
        this.size += 1;
        this.nextFirst = ((nextFirst - 1) % items.length + items.length) % items.length;
        }

    }

    public void addLast(T item) {

        if (size == items.length) {
            resize(size * 2);
            items[size] = item;
            this.nextFirst = items.length - 1;
            this.size += 1;
            this.nextLast = size;

        } else {

            items[nextLast] = item;
            this.size += 1;
            this.nextLast = (nextLast + 1) % items.length;
        }

    }

    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        if (size == 0) {
            System.out.println();
            return;
        }

        int temp = (nextFirst + 1) % items.length;
        for (int i = 1; i < size; i ++) {
            System.out.print(items[temp] + " ");
            temp = (temp + 1) % items.length;
        }
        System.out.print(items[temp]);
        System.out.println();

    }

    public T removeFirst() {

        if (this.isEmpty()) {
            return null;
        }
        int a = (nextFirst + 1) % items.length;
        T first = this.items[a];
        T[] newT = (T[]) new Object[2];
        this.items[a] = newT[0];
        this.nextFirst = a;
        size -= 1;

        if (items.length >= 16 && ((double)size/items.length) < 0.25) {
            resize(items.length/2);
        }
        return first;

    }

    public T removeLast() {

        if (this.isEmpty()) {
            return null;
        }
        int a = ((nextLast - 1) % items.length + items.length) % items.length;
        T last = this.items[a];
        T[] newT = (T[]) new Object[2];
        this.items[a] = newT[0];
        this.nextLast = a;
        size -= 1;

        if (items.length >= 16 && ((double)size/items.length) < 0.25) {
            resize(items.length/2);
        }
        return last;
    }

    public T get(int index) {

        if (index < 0 || index > size - 1) {
            return null;
        }

        return this.items[(nextFirst + 1 + index) % items.length];
    }
}
