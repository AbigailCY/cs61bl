import java.lang.reflect.Array;
import java.util.LinkedList;

public class LinkedListDeque <T> {

    private class Node {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Node(Node next, Node prev) {
            this.next = next;
            this.prev = prev;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            IntListNode that = (IntListNode) o;
//            return item == that.item;
//        }
//
//        @Override
//        public String toString() {
//            return item + "";
//        }

    }

    private Node sentinel;
    private int size;


    public LinkedListDeque(){

        sentinel = new Node(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }



    public void addFirst(T item){

        Node temp = this.sentinel.next;
        this.sentinel.next = new Node(item, temp, sentinel);
        temp.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item){

        Node temp = sentinel.prev;
        sentinel.prev = new Node(item, sentinel, temp);
        temp.next = sentinel.prev;
        size += 1;

    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return this.size;
    }

    public void changeSize(int x){
        size = x;
    }

    public void printDeque(){

        if (size == 0){
            System.out.println();
            return;
        }

        Node temp = sentinel.next;
        while (temp.next != sentinel){
            System.out.print(temp.item+" ");
            temp = temp.next;
        }
        System.out.print(temp.item);
        System.out.println();
    }

    public T removeFirst(){

        if (size == 0){
            return null;
        }

        Node temp = sentinel.next;
        sentinel.next = temp.next;
        temp.next.prev = sentinel;
        temp.next = null;
        temp.prev = null;
        size -= 1;
        return temp.item;

    }

    public T removeLast(){

        if (size == 0){
            return null;
        }

        Node temp = sentinel.prev;
        sentinel.prev = temp.prev;
        temp.prev.next = sentinel;
        temp.next = null;
        temp.prev = null;
        size -= 1;
        return temp.item;

    }

    public T get(int index){

        if (index >= size || index < 0){
            return null;
        }
        Node temp = sentinel.next;
        while (index > 0){
            temp = temp.next;
            index -= 1;
        }
        return temp.item;
    }

    public T getRecursive(int index){
        if (index >= size || index < 0){
            return null;
        }

        if (index == 0){
            return this.sentinel.next.item;
        }

        LinkedListDeque<T> temp = new LinkedListDeque<>();
        temp.sentinel.next = this.sentinel.next.next;
        temp.sentinel.prev = this.sentinel.prev;
        temp.changeSize(size-1);
        return temp.getRecursive(index-1);

    }

    public static void main(String[] args){

    }
}