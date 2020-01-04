package lab9;
//拓扑序+加权（ai）+ path*bi
import java.io.*;
import java.util.*;

public class I {
    public static final int CONSTANT = 1000000007;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = in.nextInt();
            int m = in.nextInt();
            Graph g = new Graph(n);
            for (int j = 1; j <= n; j++) {
                g.vertex[j].a = in.nextLong();
                g.vertex[j].b = in.nextLong();
            }
            for (int j = 1; j <= m; j++) {
                int u = in.nextInt();
                int v = in.nextInt();
                g.createEdge(u, v);
            }
            g.topologicalSort();
            out.println(g.result);
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
        Vertex[] vertex;
        int num;
        vertexQueue q;
        long result;

        public Graph(int n) {
            num = n + 1;
            vertex = new Vertex[num];
            for (int i = 1; i < num; i++) {
                vertex[i] = new Vertex(i);
            }
            q = new vertexQueue(n);
            result = 0;
        }

        public void createEdge(int u, int v) {
            vertex[u].adj.add(vertex[v]);
            vertex[v].inDegree++;
        }

        public void topologicalSort() {
            for (int i = 1; i < num; i++) {
                if (vertex[i].inDegree == 0) {
                    q.insert(vertex[i]);
                }
            }
            while (!q.isEmpty()) {
                Vertex tmp = q.remove();
                for (Vertex v : tmp.adj) {
                    v.path = (((tmp.path + tmp.a) % CONSTANT)  + v.path) % CONSTANT;
                    if (--v.inDegree == 0) {
                        q.insert(v);
                    }
                }
                result = ((tmp.path * (tmp.b % CONSTANT)) % CONSTANT + result) % CONSTANT;
            }

        }
    }

    static class Vertex {
        ArrayList<Vertex> adj;
        int key;
        long a;
        long b;
        int inDegree;
        long path;

        public Vertex(int key, long a, long b) {
            this.key = key;
            adj = new ArrayList<>();
            inDegree = 0;
            path = 0;
            this.a = a;
            this.b = b;
        }

        public Vertex(int key) {
            this(key, 0, 0);
        }
    }

    static class vertexQueue {
        private int SIZE;
        private Vertex[] queArray;
        private int front;
        private int rear;

        public vertexQueue(int n) {
            SIZE = n + 9;
            queArray = new Vertex[SIZE];
            front = 0;
            rear = 0;
        }

        public void insert(Vertex a) {
            if (rear < SIZE) {
                queArray[rear++] = a;
            }
        }

        public Vertex remove() {
            Vertex a = queArray[front];
            front++;
            return a;
        }

        public boolean isEmpty() {
            return front == rear;
        }
    }
}
