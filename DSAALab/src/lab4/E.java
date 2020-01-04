package lab4;

import java.util.Scanner;

public class E {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            int m = input.nextInt();
            if (m != 0) {
                int max = m;
                int temp1 = m;
                int temp2 = m;
                int temp = 0;
                long multiple1 = 0L;
                long multiple2 = 0L;
                long multiple3 = 0L;
                int counter =0;
                while (true) {
                    multiple1 = (long) temp1 * (long) temp1;
                    temp1 = value(multiple1, n);
                    if (temp1 > max) {
                        max = temp1;
                    }
                    multiple2 = (long) temp2 * (long) temp2;
                    temp = value(multiple2, n);
                    multiple3 = (long) temp * (long) temp;
                    temp2 = value(multiple3, n);
                    if (temp1 == temp2) {
                        counter++;
                        if (counter == 2) {//慢指针在循环口或者循环的中部碰上了快指针，所以需要2次判断
                            break;
                        }
                    }
                }
                System.out.println(max);
            } else {
                System.out.println(0);
            }

        }
    }

    public static int value(long a, int n) {
        if (String.valueOf(a).length() < n) {
            return (int) a;
        } else {
            return Integer.parseInt(String.valueOf(a).substring(0, n));
        }
    }
}


