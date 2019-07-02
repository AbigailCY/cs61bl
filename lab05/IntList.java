
/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */

public class IntList {

    /** The integer stored by this node. */
    public int item;
    /** The next node in this IntList. */
    public IntList next;

    /** Constructs an IntList storing ITEM and next node NEXT. */
    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an IntList storing ITEM and no next node. */
    public IntList(int item) {
        this(item, null);
    }

    /** Returns an IntList consisting of the elements in ITEMS.
     * IntList L = IntList.list(1, 2, 3);
     * System.out.println(L.toString()) // Prints 1 2 3 */
    public static IntList of(int... items) {
        /** Check for cases when we have no element given. */
        if (items.length == 0) {
            return null;
        }
        /** Create the first element. */
        IntList head = new IntList(items[0]);
        IntList last = head;
        /** Create rest of the list. */
        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }
        return head;
    }

    public int size() {
        if (next == null) {
            return 1;
        }
        return 1 + this.next.size();
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        // YOUR CODE HERE
        if (position < 0){
            throw new IllegalArgumentException("Negative Position.");
        } else if (position >= this.size()){
            throw new IllegalArgumentException("Position Out Of Range.");
        }
        IntList P = this;
        int pos = this.item;

        for (int i = 1; i <= position; i++){
            P = P.next;
            pos = P.item;
        }

        return pos;
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        // YOUR CODE HERE
        String S = "";
        IntList P = this;

        for (int i = 0; i < this.size(); i++){
            S += Integer.toString(P.item);
            P = P.next;
            if (P != null){
                S += " ";
            }
        }

        return S;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        // YOUR CODE HERE
        if (obj instanceof IntList){
            if (obj.toString().equals(this.toString())){
                return true;
            }
        }

        return false;
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(int value) {
        // YOUR CODE HERE
        IntList P = this;
        for (int i = 1; i < this.size(); i++){
            P = P.next;
        }
        P.next = new IntList(value);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        // YOUR CODE HERE
        int a = this.get(0);
        for (int i = 1; i < this.size(); i++){
            if (a > this.get(i)){
                a = this.get(i);
            }
        }

        return a;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        // YOUR CODE HERE
        int sum = 0;
        for (int i = 0; i < size(); i++){
            sum += get(i) * get(i);
        }

        return sum;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {

        if (A == null){return B;}
        if (B == null){return A;}

        IntList P = A;
        for (int i = 1; i < A.size(); i++){
            P = P.next;
        }
        P.next = B;

        return A;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {

         if (A == null){return B;}
         if (B == null){return A;}

         IntList PP = new IntList(A.item);
         IntList P = PP;

         String AB = A.toString() + " " + B.toString();
         String[] sAB = AB.split(" ");

         for (int i = 1; i < sAB.length; i++){
             P.next = new IntList(Integer.valueOf(sAB[i]));
             P = P.next;
         }

         return PP;
     }
}
