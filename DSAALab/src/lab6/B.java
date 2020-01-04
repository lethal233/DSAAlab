package lab6;

import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            int m = input.nextInt();
            String s = input.next();
            String t = input.next();
            int index = s.indexOf('*');
            if (n == m) {
                if (index == -1) {
                    if (s.equals(t)) {
                        System.out.println("YES");
                    } else {
                        System.out.println("NO");
                    }
                } else {
                    String s1 = s.substring(0, index).concat(s.substring(index + 1, n));
                    String s2 = t.substring(0, index).concat(t.substring(index + 1, n));
                    if (s1.equals(s2)){
                        System.out.println("YES");
                    }else{
                        System.out.println("NO");
                    }
                }
            } else if (n > m) {
                if (n == m + 1 && index != -1) {
                    String temp = s.substring(0, index).concat(s.substring(index + 1, n));
                    if (temp.equals(t)) {
                        System.out.println("YES");
                    } else {
                        System.out.println("NO");
                    }
                } else {
                    System.out.println("NO");
                }
            } else {
                if (index != -1) {
                    String a = s.substring(0, index);
                    String b = t.substring(0, index);
                    String c = s.substring(index + 1, n);
                    String d = t.substring(index + m - n + 1, m);
                    if (a.equals(b) && c.equals(d)) {
                        System.out.println("YES");
                    } else {
                        System.out.println("NO");
                    }
                } else {
                    System.out.println("NO");
                }
            }

        }
    }
}
