package lab4;

import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            int m = input.nextInt();
            int mod = m % n;
            if (n != 1) {
                NodeD head = new NodeD(-1, 0);
                NodeD tail = head;
                for (int j = 1; j <= n; j++) {
                    NodeD in = new NodeD(input.nextInt(), j);
                    tail.next = in;
                    in.prev = tail;
                    tail = in;
                }
                head = head.next;
                tail.next = head;
                head.prev = tail;
                NodeD pointer = head;
                pointer = operationToMovePointer(pointer, mod, n);
                int temp = pointer.value;
                pointer = operationDelete(pointer);
                n--;
                while (n > 1) {
                    int mod1 = temp % n;
                    pointer = operationToMovePointer(pointer, mod1, n);
                    temp = pointer.value;
                    pointer = operationDelete(pointer);
                    n--;
                }
                System.out.println(pointer.index);
            } else {
                System.out.println(1);
            }

        }
    }

    public static NodeD operationDelete(NodeD pointer) {
        NodeD a = pointer.prev;
        NodeD b = pointer.next;
        a.next = b;
        b.prev = a;
        return b;
    }

    public static NodeD operationToMovePointer(NodeD pointer, int mod, int numberOfPeople) {
        if (mod == 0) {
            pointer = pointer.prev;
        } else {
            if (mod <= (numberOfPeople + 2) / 2) {
                for (int i = 1; i < mod; i++) {
                    pointer = pointer.next;
                }
            } else {
                for (int i = 1; i <= numberOfPeople - mod + 1; i++) {
                    pointer = pointer.prev;
                }
            }
        }
        return pointer;
    }


}

class NodeD {
    int value;
    int index;
    NodeD next;
    NodeD prev;

    public NodeD(int value, int index) {
        this.value = value;
        this.index = index;
    }
}
