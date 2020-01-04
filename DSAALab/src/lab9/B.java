package lab9;

import java.io.*;
import java.util.*;

public class B {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int N = in.nextInt();
            int M = in.nextInt();
            int K = in.nextInt();
            Graph g = new Graph(K);
            g.addVertex(0, M, 0);
            for (int j = 1; j <= K; j++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int s = in.nextInt();
                g.addVertex(x, y, s);
            }
            g.addVertex(N, 0, 0);
            g.addBoarderEdge(N, M);
            g.addTotalEdge();
            g.BFS();
            if (g.vertex[K + 1].parent == null) {
                out.println("Yes");
            } else {
                out.println("No");
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
        int[][] adjacentMatrix;
        Vertex[] vertex;
        int v;
        int num;
        vertexQueue q;

        public Graph(int v) {
            this.v = v + 2;
            vertex = new Vertex[this.v];
            adjacentMatrix = new int[this.v][this.v];
            q = new vertexQueue();
        }

        public void addBoarderEdge(int n, int m) {
            for (int i = 1; i < v - 1; i++) {
                if (vertex[i].s - vertex[i].x >= 0 || vertex[i].s + vertex[i].y >= m) {
                    adjacentMatrix[0][i] = 1;
                    adjacentMatrix[i][0] = 1;
                }
                if (vertex[i].s + vertex[i].x >= n || vertex[i].s - vertex[i].y >= 0) {
                    adjacentMatrix[i][v - 1] = 1;
                    adjacentMatrix[v - 1][i] = 1;
                }
            }
        }

        public void addEdge(Vertex a, Vertex b) {
            if (Math.pow(a.s + b.s, 2) >= Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)) {
                adjacentMatrix[a.ind][b.ind] = 1;
                adjacentMatrix[b.ind][a.ind] = 1;
            }
        }

        public void addTotalEdge() {
            for (int i = 1; i < v - 1; i++) {
                for (int j = i + 1; j < v - 1; j++) {
                    addEdge(vertex[i], vertex[j]);
                }
            }
        }

        public void addVertex(int x, int y, int s) {
            vertex[num] = new Vertex(x, y, s, num);
            num++;
        }

        public void BFS() {
            vertex[0].color = 1;
            q.insert(vertex[0]);
            while (!q.isEmpty()) {
                Vertex a = q.remove();
                for (int i = 0; i < v; i++) {
                    if (adjacentMatrix[a.ind][i] == 1 && vertex[i].color == 0) {
                        vertex[i].color = 1;
                        vertex[i].parent = a;
                        q.insert(vertex[i]);
                    }
                }
                a.color = 2;
            }
        }
    }

    static class Vertex {
        int x;
        int y;
        int s;
        int ind;
        Vertex parent;
        int color;//0 = white, 1 = yellow, 2 = red

        public Vertex(int x, int y, int s, int ind) {
            this.x = x;
            this.y = y;
            this.s = s;
            this.ind = ind;
            color = 0;
        }
    }

    static class vertexQueue {
        private final int SIZE = 1010;
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
