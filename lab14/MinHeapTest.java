import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void test1() {
        MinHeap<Integer> test1 = new MinHeap();
        test1.insert(5);
        test1.insert(6);
        test1.insert(3);
        test1.removeMin();
        test1.removeMin();
        test1.insert(8);
        test1.insert(1);
        test1.insert(2);
        test1.removeMin();
        test1.update(1);
        assertEquals(false, test1.contains(6));
    }
}
