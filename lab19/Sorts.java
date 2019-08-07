import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sorts {

    // Ensures you get the same sequence of random numbers every single time.
    private static Random generator;
    private static final int RANDOM_SEED = 400;

    /* Returns the result of sorting the values in LIST using insertion sort. */
    public static void insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0 && list.get(j) < list.get(j - 1); j--) {
                int temp1 = list.get(j);
                int temp2 = list.get(j - 1);
                list.remove(j);
                list.add(j, temp2);
                list.remove(j - 1);
                list.add(j - 1, temp1);
            }
        }
    }

    /* Returns the result of sorting the values in LIST using selection sort. */
    public static void selectionSort(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int lowestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) < list.get(lowestIndex)) {
                    lowestIndex = j;
                }
            }
            list.add(i, list.remove(lowestIndex));
        }
    }

    /* Returns the result of sorting the values in this list using quicksort. */
    public static void quickSort(List<Integer> list) {
        generator = new Random(RANDOM_SEED);
        int me = generator.nextInt(list.size() - 1);
        quickSort(list, me, me);
    }

    private static void quickSort(List<Integer> list, int start, int end) {
        // Below are example of how to use the random number generator. You will
        // need to do this in your code somehow!
//        generator.nextDouble();
//        generator.nextInt(30);
        int pivot = list.get(start);
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        for (int num : list) {
            if (num < pivot) {
                list1.add(num);
            } else if (num == pivot) {
                list2.add(num);
            } else {
                list3.add(num);
            }
        }
        start = list1.size();
        end = start + list2.size() - 1;
        list.clear();
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list3);
        if (list1.size() <= 1 && list3.size() <= 1) {
            return;
        }
        if (list1.size() > 1) {
            int me1 = generator.nextInt(start - 1);
            quickSort(list.subList(0, start), me1, me1);
        }

        if (list3.size() > 1) {
        int me2 = generator.nextInt(list3.size() - 1);
        quickSort(list.subList(end + 1, list.size()), me2, me2);
        }

    }

    /* Returns the result of sorting the values in this list using merge
       sort. */
    public static void mergeSort(List<Integer> list) {

        if (list.isEmpty() || list.size() == 1) {
            return;
        }

        mergeSort(list.subList(0, (list.size() / 2)));
        mergeSort(list.subList(list.size() / 2, list.size()));
        mergeHelper(list);

    }
    public static void mergeHelper(List<Integer> list) {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < list.size() / 2; i++) {
            list1.add(list.get(i));
        }
        List<Integer> list2 = new ArrayList<>();
        for (int i = list.size() / 2; i < list.size(); i++) {
            list2.add(list.get(i));
        }
        List<Integer> newList = new ArrayList<>();

        while (list1.size()!=0 && list2.size()!=0) {
            if (list1.get(0) <= list2.get(0)) {
                newList.add(list1.remove(0));
            } else {
                newList.add(list2.remove(0));
            }
        }

        if (list1.isEmpty()) {
            newList.addAll(list2);
        }
        if (list2.isEmpty()) {
            newList.addAll(list1);
        }


        for (int n = 0; n < list.size(); n++) {
            list.remove(n);
            list.add(n, newList.get(n));
        }
    }
}
