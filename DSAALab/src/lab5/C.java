package lab5;
//1s 1e8-1e9 operations
//queue
//m个就够了
import java.util.Scanner;

public class C {
    static int rear = 0;
    static int front = 0;
    static int MAX_SIZE = 2000100;
    static queue[] S = new queue[MAX_SIZE];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int m = input.nextInt();
        int[] max = new int[2000000];

        int index = 0;
        int counter = 0;
        int result = 0;

        int a = input.nextInt();
        enQueue(new queue(index, a));
        index++;

        for (int i = 1; i < m; i++) {
            a = input.nextInt();
            while (true) {
                if (rear > front) {
                    if (S[rear - 1].v <= a) {
                        deQueueRear();
                    } else break;
                } else break;
            }
            enQueue(new queue(index, a));
            index++;
        }
        max[counter] = query();
        counter++;

        while (true) {
            a = input.nextInt();
            if (a == -1) {
                break;
            } else {
                if (index - S[front].i < m) {
                    while (true) {
                        if (rear > front) {
                            if (S[rear - 1].v <= a) {
                                deQueueRear();
                            } else break;
                        } else break;
                    }
                    enQueue(new queue(index, a));
                    index++;
                    max[counter] = query();
                    counter++;
                } else {
                    deQueueFront();
                    while (true) {
                        if (rear > front) {
                            if (S[rear - 1].v <= a) {
                                deQueueRear();
                            } else break;
                        } else break;
                    }
                    enQueue(new queue(index, a));
                    index++;
                    max[counter] = query();
                    counter++;
                }
            }
        }
        for (int i = 0; i < counter; i++) {
            result ^= max[i];
        }

        System.out.println(result);
    }

    public static void enQueue(queue n) {
        if (rear < MAX_SIZE) {
            S[rear] = n;
            rear++;
        }
    }

    public static void deQueueFront() {
        if (front < rear) {
            front++;
        }
    }

    public static void deQueueRear() {
        if (rear > front) {
            rear--;
        }
    }

    public static int query() {
        return S[front].v;
    }
}

class queue {
    int i;
    int v;

    public queue(int i, int v) {
        this.i = i;
        this.v = v;
    }
}