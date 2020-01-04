package Lab1;

import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = Integer.parseInt(input.nextLine());
        int[][] store = new int[num][13];
        for (int i = 0; i < num; i++) {
            int[] temps;
            String[] a = input.nextLine().split(" ");
            temps = sort(a);
            paixu(temps);
            for (int j : temps) {
                if (j > 0 && j < 10) {
                    System.out.print("W" + j + " ");
                } else if (j < 19) {
                    System.out.print("T" + (j - 9) + " ");
                } else if (j < 28) {
                    System.out.print("Y" + (j - 18) + " ");
                } else if (j == 28) {
                    System.out.print("E ");
                } else if (j == 29) {
                    System.out.print("S ");
                } else if (j == 30) {
                    System.out.print("W ");
                } else if (j == 31) {
                    System.out.print("N ");
                } else if (j == 32) {
                    System.out.print("B ");
                } else if (j == 33) {
                    System.out.print("F ");
                } else if (j == 34) {
                    System.out.print("Z ");
                }
            }
            System.out.println();


        }

    }

    public static int[] sort(String[] num) {
        int[] temp = new int[13];
        for (int i = 0; i < num.length; i++) {
            switch (num[i]) {
                case "E":
                    temp[i] = 28;
                    break;
                case "S":
                    temp[i] = 29;
                    break;
                case "W":
                    temp[i] = 30;
                    break;
                case "N":
                    temp[i] = 31;
                    break;
                case "B":
                    temp[i] = 32;
                    break;
                case "F":
                    temp[i] = 33;
                    break;
                case "Z":
                    temp[i] = 34;
                    break;
                case "W1":
                    temp[i] = 1;
                    break;
                case "W2":
                    temp[i] = 2;
                    break;
                case "W3":
                    temp[i] = 3;
                    break;
                case "W4":
                    temp[i] = 4;
                    break;
                case "W5":
                    temp[i] = 5;
                    break;
                case "W6":
                    temp[i] = 6;
                    break;
                case "W7":
                    temp[i] = 7;
                    break;
                case "W8":
                    temp[i] = 8;
                    break;
                case "W9":
                    temp[i] = 9;
                    break;
                case "T1":
                    temp[i] = 10;
                    break;
                case "T2":
                    temp[i] = 11;
                    break;
                case "T3":
                    temp[i] = 12;
                    break;
                case "T4":
                    temp[i] = 13;
                    break;
                case "T5":
                    temp[i] = 14;
                    break;
                case "T6":
                    temp[i] = 15;
                    break;
                case "T7":
                    temp[i] = 16;
                    break;
                case "T8":
                    temp[i] = 17;
                    break;
                case "T9":
                    temp[i] = 18;
                    break;
                case "Y1":
                    temp[i] = 19;
                    break;
                case "Y2":
                    temp[i] = 20;
                    break;
                case "Y3":
                    temp[i] = 21;
                    break;
                case "Y4":
                    temp[i] = 22;
                    break;
                case "Y5":
                    temp[i] = 23;
                    break;
                case "Y6":
                    temp[i] = 24;
                    break;
                case "Y7":
                    temp[i] = 25;
                    break;
                case "Y8":
                    temp[i] = 26;
                    break;
                case "Y9":
                    temp[i] = 27;
                    break;
            }
        }
        return temp;
    }

    public static void paixu(int[] io) {
        for (int i = 0; i < io.length; i++) {
            for (int j = i + 1; j < io.length; j++) {
                if (io[i] > io[j]) {
                    int temp = io[i];
                    io[i] = io[j];
                    io[j] = temp;
                }
            }
        }
    }
}
