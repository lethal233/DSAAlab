package lab8;

import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcase = in.nextInt();

        for (int i = 0; i < testcase; i++) {
            int n = in.nextInt();
            long[] array = new long[n + 10];

            for (int j = 1; j <= n; j++) {
                buildMinHeap(array, in, j);
            }
            long sum = 0;
            while (n >= 2) {
                long a = getMin(array);
                deleteMin(array, n);
                n--;
                downAdjustment(array, 1, n);
                long b = getMin(array);
                deleteMin(array, n);
                n--;
                downAdjustment(array, 1, n);
                long temp = a + b;
                sum += temp;
                n++;
                insertion(array, n, temp);
                upAdjustment(array, n);
            }

            System.out.println(sum);
        }
    }

    public static long[] buildMinHeap(long[] a, Scanner in, int n) {
        long value = in.nextLong();
        insertion(a, n, value);
        upAdjustment(a, n);
        return a;
    }

    public static long[] deleteMin(long[] a, int n) {
        a[1] = a[n];
        a[n] = 0;
        return a;
    }

    public static long[] downAdjustment(long[] a, int self, int n) {
        int c = self;
        long temp = a[c];
        int child = 2 * c;
        while (child <= n) {
            if (child + 1 <= n && a[child] > a[child + 1]) {
                child++;
            }
            if (temp <= a[child]) {
                break;
            }
            a[c] = a[child];
            c = child;
            child = 2 * child;
        }
        a[c] = temp;
        return a;
    }

    public static long[] insertion(long[] a, int n, long key) {
        a[n] = key;
        return a;
    }

    public static long[] upAdjustment(long[] a, int n) {
        int c = n;
        int parent = c / 2;
        long temp = a[c];
        while (c > 1 && temp < a[parent]) {
            if (temp >= a[parent]) {
                break;
            } else {
                a[c] = a[parent];
                c = parent;
                parent = parent / 2;
            }
        }
        a[c] = temp;
        return a;
    }

    public static long getMin(long[] a) {
        return a[1];
    }
}
