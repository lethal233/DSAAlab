package lab7;

import java.util.*;

public class A {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int node = input.nextInt();
            int k = input.nextInt();
            int h;
            long sum = 0, tmp = 1;
            for (h = 0; h <= node; h++) {
                sum += tmp;
                tmp *= k;
                if (sum > node) {
                    break;
                }
            }
            long leaf = (tmp - (node - sum)) / k + node - sum;
            System.out.println(leaf);
        }
    }
}
