package Lab1;
import java.util.Scanner;

public class B {
    public static int[][] soduku = new int[9][9];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < 11; i++) {
            int r = i;
            if (i == 3 || i == 7) {
                input.nextLine();
            } else {
                if (r > 3 && r < 7) {
                    r -= 1;
                } else if (r > 6) {
                    r -= 2;
                }
                String a = input.nextLine();
                char[] b = a.toCharArray();
                int k = 0;
                for (char c : b) {
                    if (Character.isDigit(c)) {
                        int num = c - '0';
                        soduku[r][k] = num;
                        k++;
                    } else if (c == 'x') {
                        soduku[r][k] = 0;
                        k++;
                    } else {
                        ;
                    }
                }
            }
        }

        if (isFilledFully()) {
            System.out.println("The test data is incorrect!");
        } else {
            changeSoduku();
            if (isFilledFully()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (j == 2 || j == 5 || j == 8) {
                            System.out.print(soduku[i][j] + " | ");
                        } else {
                            System.out.print(soduku[i][j] + " ");
                        }
                    }
                    System.out.println();
                    if (i == 2 || i == 5) {
                        System.out.println();
                    }
                }
            } else {
                System.out.println("The test data is incorrect!");
            }
        }
    }

    public static int findRow(int i, int j) {
        int num = 45;
        int count = 0;
        for (int b = 0; b < 9; b++) {
            if (soduku[i][b] == 0) {
                count++;
            }
        }
        if (count == 1) {
            for (int b = 0; b < 9; b++) {
                num -= soduku[i][b];
            }
        } else {
            num = 0;
        }
        return num;
    }

    public static int findColumn(int i, int j) {
        int num = 45;
        int count = 0;
        for (int a = 0; a < 9; a++) {
            if (soduku[a][j] == 0) {
                count++;
            }
        }
        if (count == 1) {
            for (int a = 0; a < 9; a++) {
                num -= soduku[a][j];
            }
        } else {
            num = 0;
        }
        return num;
    }

    public static int findSoduku(int i, int j) {
        int num = 45;
        int count = 0;
        int h = i % 3;
        int l = j % 3;
        for (int a = i - h; a <= i + 2 - h; a++) {
            for (int b = j - l; b <= j + 2 - l; b++) {
                if (soduku[a][b] == 0) {
                    count++;
                }
            }
        }
        if (count == 1) {
            for (int a = i - h; a <= i + 2 - h; a++) {
                for (int b = j - l; b <= j + 2 - l; b++) {
                    if (soduku[a][b] != 0) {
                        num -= soduku[a][b];
                    }
                }
            }
        } else {
            num = 0;
        }
        return num;
    }

    public static void changeSoduku() {
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (soduku[i][j] == 0) {
                    if (findRow(i, j) != 0) {
                        soduku[i][j] = findRow(i, j);
                        counter++;
                        break;
                    } else if (findColumn(i, j) != 0) {
                        soduku[i][j] = findColumn(i, j);
                        counter++;
                        break;
                    } else if (findSoduku(i, j) != 0) {
                        soduku[i][j] = findSoduku(i, j);
                        counter++;
                        break;
                    }
                }
            }
        }
        if (counter != 0) {
            changeSoduku();
        }
    }

    public static boolean isFilledFully() {
        boolean sta4 = true;
        for (int[] a : soduku) {
            for (int b : a) {
                if (b == 0) {
                    sta4 = false;
                    break;
                }
            }
        }
        return sta4;
    }
}
