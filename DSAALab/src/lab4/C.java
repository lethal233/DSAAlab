package lab4;
//光标的位置很重要

import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int length = input.nextInt();
            input.nextLine();

            NodeC head = new NodeC(-2);
            NodeC tail = new NodeC(-1);
            head.next = tail;
            tail.prev = head;
            NodeC cursor = tail;
            String s = input.nextLine();
            for (int j = 0; j < length; ) {
                if (s.charAt(j) == 'r') {
                    j++;
                    if (j == length) {
                        break;
                    } else {
                        NodeC sc = new NodeC((s.charAt(j) - '0'));
                        cursor = operationR(cursor, sc);
                        j++;
                    }
                } else if (s.charAt(j) == 'I') {
                    cursor = operationI(head);
                    j++;
                } else if (s.charAt(j) == 'H') {
                    cursor = operationH(cursor);
                    j++;
                } else if (s.charAt(j) == 'L') {
                    cursor = operationL(cursor);
                    j++;
                } else if (s.charAt(j) == 'x') {
                    cursor = operationX(cursor);
                    j++;
                } else {
                    NodeC sc = new NodeC((s.charAt(j) - '0'));
                    operationAdd(cursor, sc);
                    j++;
                }
            }

            NodeC pointer = head.next;
            while (pointer.v != -1) {
                System.out.print(pointer.v);
                pointer = pointer.next;
            }
            System.out.println();
        }
    }

    public static NodeC operationH(NodeC pointer) {
        if (pointer.prev.v != -2) {
            return pointer.prev;
        } else {
            return pointer;
        }
    }

    public static NodeC operationR(NodeC pointer, NodeC input) {
        if (pointer.v != -1) {
            NodeC a = pointer.prev;
            NodeC c = pointer.next;
            a.next = input;
            input.next = c;
            c.prev = input;
            input.prev = a;
        } else {
            NodeC a = pointer.prev;
            NodeC b = pointer;
            input.next = b;
            a.next = input;
            b.prev = input;
            input.prev = a;
        }
        return input;
    }

    public static NodeC operationI(NodeC head) {
        return head.next;
    }

    public static NodeC operationL(NodeC pointer) {
        if (pointer.v != -1) {
            return pointer.next;
        } else {
            return pointer;
        }
    }

    public static NodeC operationX(NodeC pointer) {
        if (pointer.v != -1) {
            NodeC a = pointer.prev;
            NodeC b = pointer;
            NodeC c = pointer.next;
            a.next = c;
            c.prev = a;
            return c;
        } else {
            return pointer;
        }
    }

    public static NodeC operationAdd(NodeC pointer, NodeC input) {
        NodeC a = pointer.prev;
        NodeC b = pointer;
        input.next = b;
        a.next = input;
        b.prev = input;
        input.prev = a;
        if (b.next == null) {
            return b;
        } else {
            return input;
        }
    }
}

class NodeC {
    int v;
    NodeC next;
    NodeC prev;

    public NodeC(int v) {
        this.v = v;
    }
}
