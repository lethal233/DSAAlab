package lab9;
//排序+动态规划（最长递增子序列的例题）时间复杂度O(n^2)
import java.io.*;
import java.util.*;

public class F {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = in.nextInt();
            Graph g = new Graph(n);
            for (int j = 0; j < n; j++) {
                int a = in.nextInt();
                int b = in.nextInt();
                int c = in.nextInt();
                g.insert(a, b, c);
            }
            MergeSort(g.b, 0, g.b.length - 1);
            System.out.println(g.dpSolve());
        }

        out.close();
    }

    public static Cube[] MergeSort(Cube[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge(Cube[] A, int sta, int mid, int end) {
        Cube[] ary = new Cube[end - sta + 1];
        int i = sta;
        int j = mid + 1;
        for (int k = 0; k < ary.length; k++) {
            if (i <= mid && (j > end || (A[i].a < A[j].a || (A[i].a == A[j].a && A[i].b <= A[j].b)))) {
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

    static class Graph {
        public Cube[] b;
        int num;
        int ind;

        public Graph(int num) {
            this.num = num * 2;
            b = new Cube[this.num];
            ind = -1;
        }

        public void insert(int x, int y, int z) {
            ind++;
            b[ind] = new Cube(x, y, z);
            ind++;
            b[ind] = new Cube(y, x, z);
            /*b[ind].a = x;
            b[ind].b = y;
            b[ind].c = z;
            dp[ind] = z;
            ind++;
            b[ind].a = y;
            b[ind].b = x;
            b[ind].c = z;
            dp[ind] = z;*/
        }

        public int dpSolve() {
            int max = 0;
            for (int i = 0; i < num; i++) {
                int maxHeight = 0;
                for (int j = 0; j < i; j++) {
                    if (b[j].a < b[i].a && b[j].b < b[i].b) {
                        maxHeight = Math.max(maxHeight, b[j].sum);
                    }
                }
                b[i].sum += maxHeight;
                max = Math.max(b[i].sum, max);
            }
            return max;
        }
    }

    static class Cube {
        int a;
        int b;
        int c;
        int sum;

        public Cube(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            sum = c;
        }
    }

}
