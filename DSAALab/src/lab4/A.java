package lab4;

import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        for (int i = 0; i < number; i++) {
            int num1 = input.nextInt();
            Node head1 = null;
            Node tail1 = null;

            Node a = new Node(input.nextLong(), input.nextLong());
            head1 = a;
            tail1 = a;
            for (int j = 1; j < num1; j++) {
                Node b = new Node(input.nextLong(), input.nextLong());
                tail1.next = b;
                tail1 = b;
            }

            int num2 = input.nextInt();
            Node head2 = null;
            Node tail2 = null;
            Node c = new Node(input.nextLong(), input.nextLong());
            head2 = c;
            tail2 = c;
            for (int j = 1; j < num2; j++) {
                Node b = new Node(input.nextLong(), input.nextLong());
                tail2.next = b;
                tail2 = b;
            }

            Node head3 = null;
            Node tail3 = null;
            Node pointer1 = head1;
            Node pointer2 = head2;
            if (pointer1.exp > pointer2.exp) {
                Node b = new Node(pointer2.co, pointer2.exp);
                head3 = b;
                tail3 = b;
                pointer2 = pointer2.next;
            } else if (pointer1.exp < pointer2.exp) {
                Node b = new Node(pointer1.co, pointer1.exp);
                head3 = b;
                tail3 = b;
                pointer1 = pointer1.next;
            } else {
                Node b = new Node((pointer1.co + pointer2.co), pointer2.exp);
                head3 = b;
                tail3 = b;
                pointer1 = pointer1.next;
                pointer2 = pointer2.next;
            }


            while (pointer1 != null && pointer2 != null) {
                if (pointer1.exp > pointer2.exp) {
                    Node b = new Node(pointer2.co, pointer2.exp);
                    tail3.next = b;
                    tail3 = b;
                    pointer2 = pointer2.next;
                } else if (pointer1.exp < pointer2.exp) {
                    Node b = new Node(pointer1.co, pointer1.exp);
                    tail3.next = b;
                    tail3 = b;
                    pointer1 = pointer1.next;
                } else {
                    Node b = new Node((pointer1.co + pointer2.co), pointer1.exp);
                    tail3.next = b;
                    tail3 = b;
                    pointer1 = pointer1.next;
                    pointer2 = pointer2.next;
                }
            }
            while (pointer1 != null) {
                Node b = new Node(pointer1.co, pointer1.exp);
                tail3.next = b;
                tail3 = b;
                pointer1 = pointer1.next;
            }
            while (pointer2 != null) {
                Node b = new Node(pointer2.co, pointer2.exp);
                tail3.next = b;
                tail3 = b;
                pointer2 = pointer2.next;
            }


            int q = input.nextInt();
            Node pointer3 = head3;
            int counter = 0;
            for (int j = 0; j < q; j++) {
                long exo = input.nextLong();
                while (pointer3 != null) {
                    if (pointer3.exp <= exo) {
                        if (pointer3.exp == exo) {
                            System.out.print(pointer3.co + " ");
                            counter++;
                            pointer3 = pointer3.next;
                            break;
                        } else {
                            pointer3 = pointer3.next;
                        }
                    } else {
                        System.out.print(0 + " ");
                        counter++;
                        break;
                    }
                }
            }
            for (int j = counter; j < q; j++) {
                System.out.print(0 + " ");
            }
            System.out.println();
        }
    }
}

class Node {
    public long co;
    public long exp;
    public Node next;

    public Node(long co, long exp) {
        this.co = co;
        this.exp = exp;
    }
}
