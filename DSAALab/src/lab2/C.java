package lab2;
// tips: (a * b) % m=(a % m * b % m) % m
// 0,1,2 are special cases
// when n>=4, there is no need to consider
// O(1)

import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        long a = input.nextLong();
        long b = input.nextLong();
        if (a == 0 || a == 1) {
            if (b == 1) {
                System.out.println(0);
            } else {
                System.out.println(1);
            }
        } else if (a == 2) {
            if (b == 1 || b == 2) {
                System.out.println(0);
            } else {
                System.out.println(2);
            }
        } else if (a == 3) {
            long mod = ((720 % b) * (719 % b)) % b;
            for (long i = 718; i > 0; i -= 2) {
                mod = ((mod % b) * ((i % b) * ((i - 1) % b))) % b;
            }
            System.out.println(mod);
        } else {
            System.out.println(0);
        }
    }
}
