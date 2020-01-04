package Lab1;

import java.util.*;

public class F {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number = Integer.parseInt(input.nextLine());
        for (int i = 0; i < number; i++) {
            boolean isExist = false;
            int num = Integer.parseInt(input.nextLine());
            int ks = 0;
            double limit = (Math.pow(1.0 + 8.0 * num, 0.5) - 1) * 0.5;
            for (int k = 2; k <= limit; k++) {
                double x = ((double) num / k - (double) k / 2 + 0.5);
                if (isInteger(x)) {
                    isExist = true;
                    ks = k;
                    break;
                }
            }
            if (isExist) {
                System.out.println(ks);
            } else {
                System.out.println("impossible");
            }
        }
    }

    public static boolean isInteger(double d) {
        return ((long) d) + 0.0 == d;
    }
}
