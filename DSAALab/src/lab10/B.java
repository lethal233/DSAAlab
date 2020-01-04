package lab10;

import java.io.*;
import java.util.*;

public class B {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long w = in.nextLong();
            g.createEdge(u, v, w);
        }
        g.prim();
        out.println(g.MST());
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
        int v;
        priorQueue q;
        long minWeight;
        int start;

        public Graph(int n) {
            this.v = n + 1;
            vertex = new Vertex[v];
            for (int i = 1; i < v; i++) {
                vertex[i] = new Vertex(i);
            }
            q = new priorQueue();
            minWeight = Long.MAX_VALUE;
            start = 0;
        }

        public void createEdge(int i, int j, long w) {
            if (w < minWeight) {
                minWeight = w;
                start = i;
            }
            vertex[i].adj.add(vertex[j]);
            vertex[i].adjEdge.add(w);
            vertex[j].adj.add(vertex[i]);
            vertex[j].adjEdge.add(w);
        }

        public void initial() {
            for (int i = 1; i < v; i++) {
                vertex[i].distance = Long.MAX_VALUE;
            }
            vertex[start].distance = 0;
        }

        public void prim() {
            initial();
            vertex[start].known = true;
            for (int i = 0; i < vertex[start].adj.size(); i++) {
                Edge e = new Edge(vertex[start].key, vertex[start].adj.get(i).key, vertex[start].adjEdge.get(i));
                q.insertion(e);
                vertex[start].adj.get(i).distance = vertex[start].adjEdge.get(i);
                vertex[start].adj.get(i).parent = vertex[start];
            }
            while (!q.isEmpty()) {
                Edge tmp = q.popMin();
                if (!vertex[tmp.v].known) {
                    if (tmp.w < vertex[tmp.v].distance) {
                        vertex[tmp.v].distance = tmp.w;
                        vertex[tmp.v].parent = vertex[tmp.u];
                    }
                    //扫描弹出最小堆的v的邻接表，让所有边进最小堆
                    for (int i = 0; i < vertex[tmp.v].adj.size(); i++) {
                        if (!vertex[tmp.v].adj.get(i).known) {
                            Edge e = new Edge(tmp.v, vertex[tmp.v].adj.get(i).key, vertex[tmp.v].adjEdge.get(i));
                            q.insertion(e);
                        }
                    }
                    vertex[tmp.v].known = true;
                }
            }
        }

        public long MST() {
            long sum = 0;
            for (int i = 1; i < v; i++) {
                sum += vertex[i].distance;
            }
            return sum;
        }
    }

    static class Vertex {
        ArrayList<Vertex> adj;
        ArrayList<Long> adjEdge;
        long distance;
        boolean known;
        int key;
        Vertex parent;//这个题parent没什么用

        public Vertex(int key) {
            this.key = key;
            known = false;
            distance = -1;
            parent = null;
            adj = new ArrayList<>();
            adjEdge = new ArrayList<>();
        }
    }

    static class Edge {
        int u;
        int v;
        long w;

        public Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public Edge() {
            this(0, 0, 0);
        }
    }

    static class priorQueue {
        private final int MAX = 100010;
        Edge[] priQ;
        int ind = 0;

        public priorQueue() {
            priQ = new Edge[MAX];
            for (int i = 1; i < MAX; i++) {
                priQ[i] = new Edge();
            }
        }

        public Edge popMin() {
            Edge temp = priQ[1];
            priQ[1] = priQ[ind];
            priQ[ind] = new Edge();
            ind--;
            downAdjustment();
            return temp;
        }

        public void insertion(Edge key) {
            priQ[++ind] = key;
            upAdjustment();
        }

        public void upAdjustment() {
            int c = ind;
            int parent = c / 2;
            Edge temp = priQ[c];
            while (c > 1 && temp.w < priQ[parent].w) {
                if (temp.w >= priQ[parent].w) {
                    break;
                } else {
                    priQ[c] = priQ[parent];
                    c = parent;
                    parent /= 2;
                }
            }
            priQ[c] = temp;
        }

        public void downAdjustment() {
            int c = 1;
            Edge temp = priQ[c];
            int child = 2 * c;
            while (child <= ind) {
                if (child + 1 <= ind && priQ[child].w > priQ[child + 1].w) {
                    child++;
                }
                if (temp.w <= priQ[child].w) {
                    break;
                }
                priQ[c] = priQ[child];
                c = child;
                child *= 2;
            }
            priQ[c] = temp;
        }

        public boolean isEmpty() {
            return ind == 0;
        }
    }
}
