import java.util.*;

public class AccountIDLookup {

    static int linearSearch(String[] arr, String target) {
        int comp = 0;
        for (int i = 0; i < arr.length; i++) {
            comp++;
            if (arr[i].equals(target)) {
                System.out.println("Linear comps: " + comp);
                return i;
            }
        }
        return -1;
    }

    static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1, comp = 0;

        while (low <= high) {
            comp++;
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                System.out.println("Binary comps: " + comp);
                return mid;
            }
            else if (arr[mid].compareTo(target) < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] arr = {"accA", "accB", "accB", "accC"};

        System.out.println("Linear index: " + linearSearch(arr, "accB"));
        System.out.println("Binary index: " + binarySearch(arr, "accB"));
    }
}