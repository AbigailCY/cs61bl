import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        if (2 * index <= size && contents.get(2 * index) != null) {
            return 2 * index;
        } else {
            return -1;
        }
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        if (2 * index + 1 <= size && contents.get(2 * index + 1) != null) {
            return 2 * index + 1;
        } else {
            return -1;
        }
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        if (index / 2 >= 1 && contents.get(index) != null) {
            return index / 2;
        } else {
            return -1;
        }
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (contents.get(index1) != null && contents.get(index2) != null) {
            if (contents.get(index1).compareTo(contents.get(index2)) <= 0) {
                return index1;
            } else {
                return index2;
            }
        } else if (contents.get(index1) == null) {
            return index2;
        } else {
            return index1;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        while (getParentOf(index) != -1) {
            if (index == 1) {
                break;
            } else if (contents.get(getParentOf(index)).compareTo(contents.get(index)) > 0) {
                swap(getParentOf(index), index);
                index = getParentOf(index);
            } else {
                break;
            }
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        while (true) {
            if (getLeftOf(index) != -1 && getRightOf(index) != -1) {
                int toChild = min(getLeftOf(index), getRightOf(index));
                if (contents.get(toChild).compareTo(contents.get(index)) < 0) {
                    swap(toChild, index);
                    index = toChild;
                } else {
                    break;
                }
            } else if (getLeftOf(index) != -1) {
                if (contents.get(getLeftOf(index)).compareTo(contents.get(index)) < 0) {
                    swap(index, getLeftOf(index));
                } else {
                    break;
                }
            } else if (getRightOf(index) != -1) {
                if (contents.get(getRightOf(index)).compareTo(contents.get(index)) < 0) {
                    swap(index, getRightOf(index));
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            try {
                throw new IllegalAccessException();
            } catch (IllegalAccessException e) {
                System.out.println("Already exists!");
            }
        } else {
            contents.add(element);
            size += 1;
            bubbleUp(size);
        }
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        if (contents.get(1) == null) {
            return null;
        }
        if (getRightOf(1) == -1) {
            size -= 1;
            return contents.remove(1);
        } else {
            int right = getRightOf(1);
            while (getRightOf(right) != -1) {
                right = getRightOf(right);
            }
            swap(1, right);
            bubbleDown(1);
            size -= 1;
            return contents.remove(right);
        }
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (contains(element)) {
            for (E i : contents) {
                if (i != null && i.equals(element)) {
                    int index = contents.indexOf(i);
                    setElement(index, element);
                    if (i.compareTo(element) > 0) {
                        bubbleUp(index);
                    } else if (i.compareTo(element) < 0) {
                        bubbleDown(index);
                    }
                    break;
                }
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        for (E i : contents) {
            if (i != null && i.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
