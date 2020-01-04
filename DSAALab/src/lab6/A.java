package lab6;

import java.util.*;

public class A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            String s = input.next();
            int length = s.length();
            long counter = length * (length + 1) / 2;
            System.out.println(counter);
        }
    }
}

