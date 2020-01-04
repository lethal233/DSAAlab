package lab5;

import java.util.Scanner;

public class A {
    static int rear = 0;
    static int front = 0;
    static int MAX_SIZE;
    static int[] S;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MAX_SIZE = in.nextInt();
        S = new int[MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            String a = in.next();
            if (a.equals("E")) {
                enQueue(in.nextInt());
            } else if (a.equals("D")) {
                deQueue();
            } else if (a.equals("A")) {
                query();
            }
        }
        if (!isEmpty()) {
            output();
        }
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

    public static void output() {
        for (int i = front; i < rear; i++) {
            System.out.print(S[i] + " ");
        }
    }

    public static void query() {
        System.out.println(S[front]);
    }

    public static boolean isEmpty() {
        return front == rear;
    }
}
