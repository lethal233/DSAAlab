package lab2;

import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int T = Integer.parseInt(input.nextLine());
        int N = Integer.parseInt(input.nextLine());
        int[] money = new int[T];
        int[] fishPrices = new int[N];
        String moneyStr = input.nextLine();
        String[] moneyArr = moneyStr.split(" ");
        String fishPricesStr = input.nextLine();
        String[] fpArr = fishPricesStr.split(" ");

        for (int i = 0; i < T; i++) {
            money[i] = Integer.parseInt(moneyArr[i]);
        }
        for (int i = 0; i < N; i++) {
            fishPrices[i] = Integer.parseInt(fpArr[i]);
        }


        for (int i = 0; i < T; i++) {
            if (money[i] < fishPrices[0]) {
                System.out.println(money[i]);
            } else {
                int a = biSearch(fishPrices, 0, (N - 1), money[i]);
                int j = a;
                while (true) {
                    if (fishPrices[j] <= money[i]) {
                        break;
                    }
                    j--;
                }
                int k = money[i] - fishPrices[j];
                if (k > 0) {
                    System.out.println(k);
                } else {
                    System.out.println("Meow");
                }
            }
        }
    }


    public static int biSearch(int[] arr, int left, int right, int search) {
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > search) {
                right = mid - 1;
            } else if (arr[mid] < search) {
                left = mid + 1;
            } else return mid;
        }
        return right;
    }

}
