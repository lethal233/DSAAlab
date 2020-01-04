package lab3;

import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        for (int i = 0; i < number; i++) {
            int length = input.nextInt();
            int[] arr = new int[length];
            for (int j = 0; j < length; j++) {
                arr[j] = input.nextInt();
            }
            BubbleSort(arr);
            if (value(arr,arr[length-3])==1){
                System.out.println(arr[length-3]);
            }else{
                System.out.println("wa");
            }
        }
    }

    public static int[] BubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 1; j < a.length; j++) {
                if (a[j - 1] > a[j]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }

    public static int value(int[] a, int b) {
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b) {
                counter++;
            }
        }
        return counter;
    }
}
