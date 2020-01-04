package lab8;
//建一个大小为k的小顶堆
//要long
import java.util.*;

public class D {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        int k = input.nextInt();
        long s = input.nextLong();
        long[] maxHeap = new long[k + 1];
        int loop = t / 100;
        for (int i = 1; i <= loop; i++) {
            for (int j = i * 100 - 99; j <= i * 100; j++) {
                long value = an(j, s);
                if (isFull(maxHeap, k)) {
                    if (getTop(maxHeap) < value) {
                        maxHeap[1] = value;
                        downAdjustment(maxHeap, 1, k);
                    }
                } else {
                    insertion(maxHeap, j, value);
                    upAdjustment(maxHeap, j);
                }
            }
            s = getTop(maxHeap);
            System.out.printf("%d ", s);
        }
    }

    public static long getTop(long[] a) {
        return a[1];
    }

    public static long[] downAdjustment(long[] a, int self, int n) {
        int c = self;
        long temp = a[c];
        int child = 2 * c;
        while (child < n) {
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

    public static long an(int second, long lastAns) {
        return ((lastAns + second) + sumDigits(lastAns + second));
    }

    public static long sumDigits(long n) {
        long result = 0;
        long count = getDigit(n);
        for (int i = 0; i < count; i++) {
            long temp = n % 10;
            result += temp;
            n /= 10;
        }
        return result;
    }

    public static long getDigit(long n) {
        long count = 0;
        while (n > 0) {
            n /= 10;
            count++;
        }
        return count;
    }

    public static boolean isFull(long[] a, int n) {
        return a[n] != 0;
    }
}
