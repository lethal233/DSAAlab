package lab5;

import java.util.Scanner;

public class B {
    static int top = -1;
    static char[] S;
    static int MAX_SIZE;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcase = in.nextInt();
        outer:
        for (int i = 0; i < testcase; i++) {
            MAX_SIZE = in.nextInt();
            in.nextLine();
            String str = in.nextLine();
            if (MAX_SIZE % 2 == 1) {
                System.out.println("NO");
            } else {
                S = new char[MAX_SIZE];
                inner:
                for (int j = 0; j < MAX_SIZE; j++) {
                    char c = str.charAt(j);
                    if (c == '(' || c == '[' || c == '{') {
                        push(c);
                    } else {
                        if (c == ')') {
                            if (top != -1) {
                                if (S[top] == '(') {
                                    pop();
                                } else {
                                    System.out.println("NO");
                                    top = -1;
                                    continue outer;
                                }
                            } else {
                                System.out.println("NO");
                                top = -1;
                                continue outer;
                            }
                        } else if (c == ']') {
                            if (top != -1) {
                                if (S[top] == '[') {
                                    pop();
                                } else {
                                    System.out.println("NO");
                                    top = -1;
                                    continue outer;
                                }
                            } else {
                                System.out.println("NO");
                                top = -1;
                                continue outer;
                            }
                        } else if (c == '}') {
                            if (top != -1) {
                                if (S[top] == '{') {
                                    pop();
                                } else {
                                    System.out.println("NO");
                                    top = -1;
                                    continue outer;
                                }
                            } else {
                                System.out.println("NO");
                                top = -1;
                                continue outer;
                            }
                        }
                    }
                }
                if (isEmpty()) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                    top = -1;
                }
            }
        }
    }

    public static void push(char c) {
        if (top != MAX_SIZE - 1) {
            top++;
            S[top] = c;
        }
    }

    public static void pop() {
        if (top != -1) {
            top--;
        }
    }

    public static boolean isEmpty() {
        return top == -1;
    }
}
