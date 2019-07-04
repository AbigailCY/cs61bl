import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void addBeforeResize() {
        System.out.println("Running add/isEmpty/Size test.");
        ArrayDeque<String> test1 = new ArrayDeque<>();

        try {

            assertTrue(test1.isEmpty());

            test1.addFirst("first");
            assertEquals(1, test1.size());
            assertFalse(test1.isEmpty());

            test1.addLast("Second");
            test1.addFirst("3rd");
            test1.addLast("4th");
            test1.addLast("5th");
            test1.addLast("6th");
            test1.addLast("7th");
            test1.addLast("8th");
            test1.removeLast();
            test1.removeFirst();
            assertEquals(6, test1.size());


        } finally {

            System.out.println("Printing out deque: ");
            test1.printDeque();

        }
    }

    @Test
    public void Resize() {
        System.out.println("Resize test.");
        ArrayDeque<String> test2 = new ArrayDeque<>();

        try {

            test2.addFirst("first");
            test2.addLast("Second");
            test2.addLast("3rd");
            test2.addLast("4th");
            test2.addLast("5th");
            test2.addLast("6th");
            test2.addLast("7th");
            test2.addLast("8th");
            test2.addFirst("10th");
            test2.addLast("9th");
            test2.addFirst("11th");


        } finally {

            System.out.println("Printing out deque: ");
            test2.printDeque();

        }
    }

    @Test
    public void Memory() {
        System.out.println("Resize up and down test.");
        ArrayDeque<String> test2 = new ArrayDeque<>();

        try {

            test2.addFirst("first");
            test2.addLast("Second");
            test2.addLast("3rd");
            test2.addLast("4th");
            test2.addLast("5th");
            test2.addLast("6th");
            test2.addLast("7th");
            test2.addLast("8th");
            test2.addLast("9th");
            test2.addLast("10th");
            test2.addLast("first");
            test2.addLast("Second");
            test2.addLast("3rd");
            test2.addLast("4th");
            test2.addLast("5th");
            test2.addLast("6th");
            test2.addLast("7th");
            test2.addLast("8th");
            test2.addLast("9th");
            test2.addLast("10th");
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();
            test2.removeLast();


        } finally {

            System.out.println("Printing out deque: ");
            test2.printDeque();

        }
    }

}
