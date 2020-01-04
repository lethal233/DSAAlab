package lab5;

import java.io.*;
import java.util.*;

public class D {
    static int top = -1;
    static int size;
    static int[] S;
    static boolean[] isIn;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);

        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            size = in.nextInt();
            S = new int[size];
            int counter;
            isIn = new boolean[size + 1];
            int a;

            for (counter = 1; counter <= size; ) {
                if (check(counter)) {
                    if (topStack() <= counter) {
                        while (top != -1 && topStack() <= counter) {
                            out.print(topStack() + " ");
                            if (topStack() == counter) {
                                counter++;
                            }
                            pop();
                        }
                    } else {//*******
                        counter++;
                    }
                } else {
                    a = in.nextInt();
                    while (a != counter) {
                        push(a);
                        isIn[a] = true;
                        a = in.nextInt();
                    }
                    out.print(counter + " ");
                    counter++;
                }
            }

            while (!isEmpty()) {
                out.print(S[top] + " ");
                pop();
            }
            out.println();
        }


        out.close();
    }


    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    public static void push(int n) {
        top++;
        S[top] = n;
    }

    public static void pop() {
        if (top != -1) {
            top--;
        }
    }

    public static int topStack() {
        return S[top];
    }

    public static boolean isEmpty() {
        return top == -1;
    }

    public static boolean check(int n) {
        return isIn[n];
    }
}
