package lab3;
//把int数组全换成long数组就行了

import java.util.Scanner;

public class D {
    static long counter = 0;
    static long counterB = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        int[] volume = new int[num];
        int[] volumeB = new int[num];
        for (int i = 0; i < num; i++) {
            volume[i] = input.nextInt();
        }
        for (int i = 0; i < num; i++) {
            volumeB[i] = volume[i];
        }
        BubbleSort(volumeB);
        MergeSort(volume, 0, num - 1);
        for (int i : volume) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(counter);
        System.out.println(counterB);
    }


    public static int[] MergeSort(int[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge(int[] A, int sta, int mid, int end) {
        int[] ary = new int[end - sta + 1];
        int i = sta;
        int j = mid + 1;
        for (int k = 0; k < ary.length; k++) {
            if (i <= mid && (j > end || A[i] <= A[j])) {
                ary[k] = A[i];
                counter += A[i] * (j - mid - 1);
                i++;
            } else {
                ary[k] = A[j];
                counter += A[j] * (mid + 1 - i);
                j++;
            }
        }
        for (int t = 0; t < ary.length; t++) {
            A[t + sta] = ary[t];
        }
    }

    public static int[] BubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 1; j < a.length; j++) {
                if (a[j - 1] > a[j]) {
                    //swap(a,j-1,j);
                    counterB += a[j - 1] + a[j];
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;

                }
            }
        }
        return a;
    }
}