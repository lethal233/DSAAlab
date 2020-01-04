package lab3;
import java.util.Scanner;

public class D2 {
    static long counter = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        long[] volume = new long[num];

        for (int i = 0; i < num; i++) {
            volume[i] = input.nextLong();
        }
        MergeSort(volume, 0, num - 1);
        System.out.println(counter);
    }


    public static long[] MergeSort(long[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge(long[] A, int sta, int mid, int end) {
        long[] ary = new long[end - sta + 1];
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
}
