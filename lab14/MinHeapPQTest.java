import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ<Integer> test1 = new MinHeapPQ();
        test1.insert(2, 6);
        test1.insert(3, 2);
        test1.insert(4, 5);
        test1.insert(5, 4);

        test1.changePriority(5, 1);
        test1.changePriority(5, 9);
        System.out.println(test1.peek());
        System.out.println(test1.poll());
        System.out.println(test1.poll());
        System.out.println(test1.poll());
        System.out.println(test1.contains(4));
        System.out.println(test1.contains(5));
    }
}
