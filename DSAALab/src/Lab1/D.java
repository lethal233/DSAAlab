package Lab1;

import java.util.*;

public class D {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number = Integer.parseInt(input.nextLine());
        for (int i = 0; i < number; i++) {
            long numbers = 0L;
            String num = input.nextLine();
            long j = Long.parseLong(num);
            //找到最大的回文数
            for (; ; j--) {
                if (check(j) == 1) {
                    break;
                }
            }
            String nums = Long.toString(j);
            int length = nums.length();
            if (length % 2 == 1) {
                long temp = Long.parseLong(nums.substring(0, (length + 1) / 2))
                        - (long) Math.pow(10, (length - 1) / 2)
                        + 1;
                numbers += temp;
            } else {
                long temp = Long.parseLong(nums.substring(0, length / 2))
                        - (long) Math.pow(10, (length - 2) / 2)
                        + 1;
                numbers += temp;
            }

            length--;
            numbers += count(length);
            System.out.println(numbers);
        }
    }

    public static long count(int length) {
        if (length == 0) {
            return 1;
        } else {
            if (length % 2 == 1) {
                return 9 * (long) Math.pow(10, (length - 1) / 2)
                        + count(length - 1);
            } else {
                return 9 * (long) Math.pow(10, (length - 2) / 2)
                        + count(length - 1);
            }
        }
    }

    public static int check(long n) {
        long temp = n;
        long flag = 0;
        while (temp > 0) {
            flag = 10 * flag + temp % 10;
            temp = temp / 10;
        }
        if (flag == n) {
            return 1;
        } else
            return 0;
    }
}
