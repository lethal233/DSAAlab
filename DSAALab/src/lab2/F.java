package lab2;

import java.util.Scanner;

public class F {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long number = input.nextLong();
        for (long i = 0; i < number; i++) {
            long a = input.nextLong();
            if (a < 5) {
                System.out.println(0);
            } else {
                System.out.println(numb(a));
            }
        }

    }

    public static long numb(long a) {
        if (a < 5) {
            return 0;
        } else return a / 5 + numb(a / 5);
    }
}
