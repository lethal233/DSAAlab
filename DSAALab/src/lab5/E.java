package lab5;

import java.io.*;
import java.util.*;

public class E {
    static int rear = 0;
    static int front = 0;
    static int MAX_SIZE;
    static int frontR = 0;
    static int rearR = 0;
    static queueE[] S;
    static queueE[] R;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 1; i <= testcase; i++) {
            MAX_SIZE = in.nextInt();
            S = new queueE[MAX_SIZE];
            R = new queueE[MAX_SIZE];
            queueE[] number = new queueE[MAX_SIZE];

            for (int j = 0; j < MAX_SIZE; j++) {
                number[j] = new queueE(j + 1, in.nextInt());
            }

            //right
            for (int j = 0; j < MAX_SIZE; ) {
                if (rear == front) {
                    enQueueS(number[j]);
                    j++;
                } else {
                    if (number[j].v > S[front].v) {
                        while (rear > front + 1) {
                            number[S[rear - 2].i - 1].right = S[rear - 1].i;
                            deQueueRear();
                        }
                        deQueueFront();
                        enQueueS(number[j]);
                        j++;
                    } else {
                        if (number[j].v < S[rear - 1].v) {
                            enQueueS(number[j]);
                            j++;
                        } else {
                            while (rear > front + 1 && number[j].v > S[rear - 1].v) {
                                number[S[rear - 2].i - 1].right = S[rear - 1].i;
                                deQueueRear();

                            }
                            enQueueS(number[j]);
                            j++;
                        }
                    }
                }
            }

            for (int j = front; front < rear && j <= rear - 1; j++) {
                if (j == rear - 1) {
                    number[S[j].i - 1].right = 0;
                } else {
                    number[S[j].i - 1].right = S[j + 1].i;
                }
            }

            //left
            for (int j = MAX_SIZE - 1; j >= 0; ) {
                if (rearR == frontR) {
                    enQueueR(number[j]);
                    j--;
                } else {
                    if (number[j].v > R[frontR].v) {
                        while (rearR > frontR + 1) {
                            number[R[rearR - 2].i - 1].left = R[rearR - 1].i;
                            deQueueRearR();
                        }
                        deQueueFrontR();
                        enQueueR(number[j]);
                        j--;
                    } else {
                        if (number[j].v < R[rearR - 1].v) {
                            enQueueR(number[j]);
                            j--;
                        } else {
                            while (rearR > frontR + 1 && number[j].v > R[rearR - 1].v) {
                                number[R[rearR - 2].i - 1].left = R[rearR - 1].i;
                                deQueueRearR();
                            }
                            enQueueR(number[j]);
                            j--;
                        }
                    }
                }
            }

            for (int j = frontR; frontR < rearR && j <= rearR - 1; j++) {
                if (j == rearR - 1) {
                    number[R[j].i - 1].left = 0;
                } else {
                    number[R[j].i - 1].left = R[j + 1].i;
                }
            }

            out.println("Case " + i + ":");
            for (int j = 0; j < MAX_SIZE; j++) {
                out.println(number[j].left + " " + number[j].right);
            }
            front = 0;
            rear = 0;
            frontR = 0;
            rearR = 0;
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

    public static void enQueueS(queueE n) {
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

    public static void enQueueR(queueE n) {
        if (rearR < MAX_SIZE) {
            R[rearR] = n;
            rearR++;
        }
    }


    public static void deQueueFrontR() {
        if (frontR < rearR) {
            frontR++;
        }
    }


    public static void deQueueRearR() {
        if (rearR > frontR) {
            rearR--;
        }
    }
}

class queueE {
    int i;
    int v;
    int right;
    int left;

    public queueE(int i, int v) {
        this.i = i;
        this.v = v;
    }

}
