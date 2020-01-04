package lab9;

import java.io.*;
import java.util.*;

public class D {
    public static int n;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            n = in.nextInt();
            int m = in.nextInt();
            Graph g = new Graph(n);
            for (int j = 0; j < m; j++) {
                int u = in.nextInt();
                int v = in.nextInt();
                g.addEdge(u, v);
            }
            g.BFS();
            if (g.num <= n / 2) {
                out.println(g.num);
                for (int j = 1; j < g.count.length; j++) {
                    if (g.count[j] == 1) {
                        out.print(j + " ");
                    }
                }
                out.println();
            } else {
                out.println(n - g.num);
                for (int j = 1; j < g.count.length; j++) {
                    if (g.count[j] == 0) {
                        out.print(j + " ");
                    }
                }
                out.println();
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

    static class Graph {
        Vertex[] vertex;
        int[] count;
        int v;
        int num;
        vertexQueue q;

        public Graph(int v) {
            this.v = v + 1;
            vertex = new Vertex[this.v];
            q = new vertexQueue();
            for (int i = 1; i < this.v; i++) {
                vertex[i] = new Vertex(i);
            }
            num = 0;
            count = new int[this.v];
        }

        public void addEdge(int a, int b) {
            vertex[a].adj.add(vertex[b]);
            vertex[b].adj.add(vertex[a]);
        }

        public void BFS() {
            vertex[1].color = 1;
            vertex[1].type = 1;
            count[1] = 1;
            num++;
            q.insert(vertex[1]);
            while (!q.isEmpty()) {
                Vertex a = q.remove();
                for (Vertex p : a.adj) {
                    if (p.color == 0) {
                        p.color = 1;
                        p.type = (a.type + 1) % 2;
                        if (p.type == 1) {
                            num++;
                        }
                        count[p.key] = p.type;
                        q.insert(p);
                    }
                }
                a.color = 2;
            }
        }
    }

    static class Vertex {
        ArrayList<Vertex> adj;
        int color;
        int key;
        int type;

        public Vertex(int key) {
            this.key = key;
            color = 0;
            adj = new ArrayList<>(n - 1);
            type = 0;
        }
    }

    static class vertexQueue {
        private final int SIZE = 1000;
        private Vertex[] queArray;
        private int front;
        private int rear;

        public vertexQueue() {
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
