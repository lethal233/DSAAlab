package lab9;
//拓扑排序+队列直接找最长长度，每次弹出的时候找到最大长度，时间复杂度O(n^2)
import java.io.*;
import java.util.*;

public class ftest {

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
            g.createEdge();
            g.topologicalSort();
            out.println(g.maxPath);
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

    static class Graph {
        Cube[] cube;
        int num;
        int ind;
        int maxPath;
        CubeQueue q;

        public Graph(int num) {
            this.num = num;
            cube = new Cube[num];
            for (int i = 0; i < num; i++) {
                cube[i] = new Cube();
            }
            ind = 0;
            maxPath = 0;
            q = new CubeQueue();
        }

        public void insert(int a, int b, int c) {
            cube[ind++] = new Cube(a, b, c);
        }

        public void createEdge() {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if ((cube[j].a < cube[i].a && cube[j].b < cube[i].b)
                            || (cube[j].a < cube[i].b && cube[j].b < cube[i].a)) {
                        cube[i].adj.add(cube[j]);
                        cube[j].inDegree++;
                    }
                }
            }
        }

        public void topologicalSort() {
            for (Cube x : cube) {
                if (x.inDegree == 0) {
                    q.insert(x);
                }
            }
            while (!q.isEmpty()) {
                Cube tmp = q.remove();
                for (Cube x : tmp.adj) {
                    x.height = Math.max(x.height, tmp.height + x.c);
                    if (--x.inDegree == 0) {
                        q.insert(x);
                    }
                }
                maxPath = Math.max(maxPath, tmp.height);
            }
        }

    }

    static class Cube {
        int a;
        int b;
        int c;
        ArrayList<Cube> adj;
        int inDegree;
        int height;


        public Cube(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            adj = new ArrayList<>();
            inDegree = 0;
            height = c;
        }

        public Cube() {
            this(0, 0, 0);
        }
    }

    static class CubeQueue {
        private final int SIZE = 2009;
        private Cube[] queArray;
        private int front;
        private int rear;

        public CubeQueue() {
            queArray = new Cube[SIZE];
            front = 0;
            rear = 0;
        }

        public void insert(Cube a) {
            if (rear < SIZE) {
                queArray[rear++] = a;
            }
        }

        public Cube remove() {
            Cube a = queArray[front];
            front++;
            return a;
        }

        public boolean isEmpty() {
            return front == rear;
        }
    }
}
