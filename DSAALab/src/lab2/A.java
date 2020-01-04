package lab2;

import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long number = input.nextLong();
        for (long i = 0; i < number; i++) {
            long a = input.nextLong();
            long b = a * (a + 1) * (a + 2) / 6;
            System.out.println(b);
        }
    }
}
