package lab3;

import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }

        mergeSort(arr, 0, n - 1);
        long counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                int temp = m - arr[i] - arr[j];
                if (binarySearch(arr, j + 1, n - 1, temp) != -1) {
                    int index1 = binaryLowerBoundSearch(arr, j + 1, n - 1, temp);
                    int index2 = binaryUpperBoundSearch(arr, j + 1, n - 1, temp);
                    counter += (index2 - index1 + 1);
                }
            }
        }
        System.out.println(counter);
    }

    public static int binaryLowerBoundSearch(int[] arr, int left, int right, int search) {
        int index = binarySearch(arr, left, right, search);
        for (int i = index; i >= left; i--) {
            if (arr[i] == search) {
                index = i;
            } else
                break;
        }
        return index;
    }

    public static int binaryUpperBoundSearch(int[] arr, int left, int right, int search) {
        int index = binarySearch(arr, left, right, search);
        for (int i = index; i <= right; i++) {
            if (arr[i] == search) {
                index = i;
            } else {
                break;
            }
        }
        return index;
    }

    public static int binarySearch(int[] arr, int left, int right, int search) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (search == arr[mid]) {
            return mid;
        } else if (search < arr[mid]) {
            right = mid - 1;
            return binarySearch(arr, left, right, search);
        } else {
            left = mid + 1;
            return binarySearch(arr, left, right, search);
        }
    }

    public static void mergeSort(int[] arr, int L, int R) {
        if (L < R) {
            int m = (L + R) / 2;
            mergeSort(arr, L, m);
            mergeSort(arr, m + 1, R);
            merge(arr, L, m, R);
        }
    }

    public static void merge(int[] arr, int L, int m, int R) {
        int[] temp = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;

        while (p1 <= m && p2 <= R) {
            if (arr[p1] < arr[p2]) {
                temp[i++] = arr[p1++];
            } else {
                temp[i++] = arr[p2++];
            }
        }

        while (p1 <= m) {
            temp[i++] = arr[p1++];
        }
        while (p2 <= R) {
            temp[i++] = arr[p2++];
        }
        for (i = 0; i < R - L + 1; i++) {
            arr[L + i] = temp[i];
        }
    }
}
