package lab4;

import java.util.Scanner;

//新建一个数组存地址  Node[] list
public class B {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testCase = input.nextInt();
        for (int i = 0; i < testCase; i++) {
            int N = input.nextInt();
            int M = input.nextInt();
            NodeB head = new NodeB(-1);
            NodeB tail = head;
            NodeB[] list = new NodeB[N];
            for (int j = 0; j < N; j++) {
                int v = input.nextInt();
                NodeB a = new NodeB(v);
                tail.next = a;
                a.prev = tail;
                tail = a;
                list[v] = a;
            }
            NodeB end = new NodeB(-2);
            tail.next = end;
            end.prev = tail;
            tail = end;

            for (int k = 0; k < M; k++) {
                int x1 = input.nextInt();
                int y1 = input.nextInt();
                int x2 = input.nextInt();
                int y2 = input.nextInt();
                if (list[y1].next == list[x2]) {
                    NodeB q = list[x1].prev;
                    NodeB w = list[x1];
                    NodeB r = list[y1];
                    NodeB y = list[x2];
                    NodeB u = list[y2].next;
                    NodeB o = list[y2];

                    q.next = y;
                    list[y1].next = u;
                    list[y2].next = w;
                    u.prev = r;
                    y.prev = q;
                    w.prev = o;

                } else {
                    NodeB q = list[x1].prev;
                    NodeB w = list[x1];
                    NodeB e = list[y1].next;
                    NodeB r = list[y1];
                    NodeB t = list[x2].prev;
                    NodeB y = list[x2];
                    NodeB u = list[y2].next;
                    NodeB o = list[y2];

                    q.next = y;
                    r.next = u;
                    t.next = w;
                    o.next = e;

                    u.prev = r;
                    y.prev = q;
                    e.prev = o;
                    w.prev = t;
                }
            }
            NodeB pointer = head.next;
            while (pointer.next != null) {
                System.out.print(pointer.v + " ");
                pointer = pointer.next;
            }
            System.out.println();
        }
    }

}

class NodeB {
    int v;
    NodeB next;
    NodeB prev;

    public NodeB(int v) {
        this.v = v;
    }
}
