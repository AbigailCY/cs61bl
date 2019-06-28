public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;

        }else{
            for (int a = pos; a < values.length-1; a++){
                values[a] = values[a+1];
            }
            values[values.length-1] = 0;
        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }

        for (int a = values.length-1; a > pos; a -= 1){
            values[a] = values[a-1];
        }
        values[pos] = newInt;
    }
}
