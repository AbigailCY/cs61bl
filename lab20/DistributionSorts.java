
public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        Integer[] counts = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] starts = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i : arr) {
            counts[i] += 1;
        }
        for (int i = 1; i < 10; i++) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }

        for (int i = 0; i < 10; i++) {
            for (int j = counts[i]; j > 0; j--) {
                arr[starts[i] + j - 1] = i;
            }
        }

    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }
    private static Integer digit(int a, int b) {
        return a % (int) (Math.pow(10, b + 1)) / (int) (Math.pow(10, b));
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {

        int[] counts = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] starts = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] output = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            counts[digit(arr[i], digit)] += 1;
//            output[i] = arr[i];
        }
        for (int i = 1; i < 10; i++) {
            starts[i] = starts[i - 1] + counts[i - 1];
        }

        int[] output = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            output[i] = arr[i];
        }

        for (int i : output) {
            int num = digit(i, digit);
            arr[starts[num]] = i;
            starts[num] += 1;
        }



    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }
}
