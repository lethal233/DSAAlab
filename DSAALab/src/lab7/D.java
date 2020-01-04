package lab7;

import java.io.*;
import java.util.*;

public class D {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int N = in.nextInt();
            long sum;
            if (N != 1) {
                int front = 0;
                int rear = N;
                long[] S = new long[3 * N];
                for (int j = 0; j < N; j++) {
                    S[j] = in.nextLong();
                }
                MergeSort(S, 0, N - 1);
                long[] merge = new long[N - 1];
                for (int j = 0; j < N - 1; j++) {
                    long temp = S[front] + S[front + 1];
                    merge[j] = temp;
                    if (S[rear - 1] <= temp) {
                        S[rear] = temp;
                        rear++;
                        front += 2;
                    } else {
                        int k = front + 2;
                        for (; k < rear; k++) {
                            if (S[k] > temp) {
                                break;
                            }
                        }
                        for (int l = rear; l > k; l--) {
                            S[l] = S[l - 1];
                        }
                        S[k] = temp;
                        rear++;
                        front += 2;
                    }
                }
                sum = 0;
                for (long n : merge) {
                    sum += n;
                }
                out.println(sum);
            } else {
                sum = in.nextLong();
                out.println(sum);
            }
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


    public static long[] MergeSort(long[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge(long[] A, int sta, int mid, int end) {
        long[] ary = new long[end - sta + 1];
        int i = sta;
        int j = mid + 1;
        for (int k = 0; k < ary.length; k++) {
            if (i <= mid && (j > end || A[i] <= A[j])) {
                ary[k] = A[i];
                i++;
            } else {
                ary[k] = A[j];
                j++;
            }
        }
        for (int t = 0; t < ary.length; t++) {
            A[t + sta] = ary[t];
        }
    }

}
