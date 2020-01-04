package lab5;
import java.util.Scanner;
//lab5 C outdated
public class Main {
    static int rear = 0;
    static int front = 0;
    static int MAX_SIZE = 2000100;
    static int[] S = new int[MAX_SIZE];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int m = input.nextInt();
        int result = 0;
        int a = 0;
        int max = 0;

        for (int i = 0; i < m; i++) {
            a = input.nextInt();
            if (a != -1) {
                if (a > max) {
                    max = a;
                }
                enQueue(a);
            } else break;
        }

        result ^= max;
        while (true) {
            a = input.nextInt();
            if (a == -1) {
                break;
            } else {
                enQueue(a);
                if (max == S[front]) {
                    if (a > max) {
                        max = a;
                        deQueue();
                        result ^= max;
                    } else {
                        deQueue();
                        max = 0;
                        for (int i = front; i < rear; i++) {
                            if (S[i] > max) {
                                max = S[i];
                            }
                        }
                        result ^= max;
                    }
                } else {
                    deQueue();
                    if (a > max) {
                        max = a;
                    }
                    result ^= max;
                }
            }
        }
        System.out.println(result);
    }

    public static void enQueue(int n) {
        if (rear < MAX_SIZE) {
            S[rear] = n;
            rear++;
        }
    }

    public static void deQueue() {
        if (front < rear) {
            front++;
        }
    }

}
