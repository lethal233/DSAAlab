package lab6;

import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        int counter = 0;
        for (int i = 0; i < testcase; i++) {
            String s = input.next();
            String p = input.next();
            int length;
            if (s.length() % 3 == 0) {
                length = s.length() / 3;
            } else {
                length = s.length() / 3 + 1;
            }
            String c = s.substring(0, length);
            if (KMP(p, c) != -1) {
                counter++;
            }
        }
        System.out.println(counter);
    }

    public static int[] nextArray(String p) {
        int m = p.length();
        int[] next = new int[m];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < m; ) {
            if (j == 0) {
                if (p.charAt(i) != p.charAt(j)) {
                    next[i] = 0;
                    i++;
                } else {
                    next[i] = j + 1;
                    j++;
                    i++;
                }
            } else {
                if (p.charAt(i) == p.charAt(j)) {
                    next[i] = j + 1;
                    j++;
                    i++;
                } else {
                    j = next[j - 1];
                }
            }
        }
        return next;
    }

    public static int KMP(String t, String p) {
        int n = t.length();
        int m = p.length();
        int[] next = nextArray(p);
        int i = 0, j = 0;
        for (; i < n && j < m; ) {
            if (j == 0) {
                if (t.charAt(i) == p.charAt(j)) {
                    i++;
                    j++;
                } else {
                    i++;
                }
            } else {
                if (t.charAt(i) == p.charAt(j)) {
                    i++;
                    j++;
                } else {
                    j = next[j - 1];
                }
            }
        }
        if (j < m) {
            return -1;
        } else {
            return i - m;
        }
    }
}
