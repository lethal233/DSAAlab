package lab3;

import java.util.*;

public class B {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        int luckyNumber = input.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = input.nextInt();
        }
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(arr[luckyNumber - 1]);
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
