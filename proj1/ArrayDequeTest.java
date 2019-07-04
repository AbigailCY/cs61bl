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
    public void resize() {
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
    public void memory() {
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

    @Test
    public void debug() {
        System.out.println("Resize up and down test.");
        ArrayDeque<Integer> test2 = new ArrayDeque<>();

        try {

            test2.addFirst(1);
            test2.addLast(2);
            test2.addLast(3);
            test2.addFirst(5);
            test2.addFirst(6);
            test2.addLast(7);
            test2.addLast(8);
            test2.addLast(10);





            System.out.println(test2.removeFirst());



        } finally {

            System.out.println("Printing out deque: ");
            test2.printDeque();

        }
    }

}
