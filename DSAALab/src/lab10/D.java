package lab10;

import java.io.*;
import java.util.*;

public class D {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        Graph g = new Graph(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                long weight = in.nextLong();
                g.createVertex(weight);
            }
        }
        g.createAllEdge();
        g.primMax();
        out.println(g.MaxST());
        out.close();
    }

    static class Graph {
        Vertex[] vertex;
        int start;
        priorQueue q;
        long maxWeight;
        int v;
        int n;
        int m;
        int ind;

        public Graph(int n, int m) {
            this.n = n;
            this.m = m;
            this.v = n * m;
            vertex = new Vertex[v];
            for (int i = 0; i < v; i++) {
                vertex[i] = new Vertex(0, i);
            }
            q = new priorQueue();
            maxWeight = 0;
            start = 0;
            ind = 0;
        }

        public void createVertex(long weight) {
            vertex[ind] = new Vertex(weight, ind);
            ind++;
        }

        public void createOneEdge(int i) {
            if (i % m + 1 < m) {
                vertex[i].adj.add(vertex[i + 1]);
                long d = vertex[i].weight * vertex[i + 1].weight;
                if (d > maxWeight) {
                    maxWeight = d;
                    start = i;
                }
                vertex[i].adjEdge.add(d);
            }
            if (i % m - 1 >= 0) {
                vertex[i].adj.add(vertex[i - 1]);
                long d = vertex[i].weight * vertex[i - 1].weight;
                if (d > maxWeight) {
                    maxWeight = d;
                    start = i;
                }
                vertex[i].adjEdge.add(d);
                //vertex[i].adjEdge.add(vertex[i].weight * vertex[i - 1].weight);
            }
            if (i / m + 1 < n) {
                vertex[i].adj.add(vertex[i + m]);
                long d = vertex[i].weight * vertex[i + m].weight;
                if (d > maxWeight) {
                    maxWeight = d;
                    start = i;
                }
                vertex[i].adjEdge.add(d);
                //vertex[i].adjEdge.add(vertex[i].weight * vertex[i + m].weight);
            }
            if (i / m - 1 >= 0) {
                vertex[i].adj.add(vertex[i - m]);
                long d = vertex[i].weight * vertex[i - m].weight;
                if (d > maxWeight) {
                    maxWeight = d;
                    start = i;
                }
                vertex[i].adjEdge.add(d);
                //vertex[i].adjEdge.add(vertex[i].weight * vertex[i - m].weight);
            }
        }

        public void createAllEdge() {
            for (int i = 0; i < v; i++) {
                createOneEdge(i);
            }
        }

        public void primMax() {
            vertex[start].known = true;
            for (int i = 0; i < vertex[start].adj.size(); i++) {
                Edge e = new Edge(start, vertex[start].adj.get(i).key, vertex[start].adjEdge.get(i));
                q.insertion(e);
                vertex[start].adj.get(i).distance = vertex[start].adjEdge.get(i);
            }
            while (!q.isEmpty()) {
                Edge tmp = q.popMax();
                if (!vertex[tmp.v].known) {
                    if (tmp.w > vertex[tmp.v].distance) {
                        vertex[tmp.v].distance = tmp.w;
                    }
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

        public long MaxST() {
            long sum = 0;
            for (int i = 0; i < v; i++) {
                sum += vertex[i].distance;
            }
            return sum;
        }
    }

    static class Vertex {
        long weight;
        ArrayList<Vertex> adj;
        ArrayList<Long> adjEdge;
        long distance;
        boolean known;
        int key;

        public Vertex(long weight, int key) {
            this.weight = weight;
            adj = new ArrayList<>();
            adjEdge = new ArrayList<>();
            distance = 0;
            known = false;
            this.key = key;
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
        int index = 0;

        public priorQueue() {
            priQ = new Edge[MAX];
            for (int i = 1; i < MAX; i++) {
                priQ[i] = new Edge();
            }
        }

        public Edge popMax() {
            Edge temp = priQ[1];
            priQ[1] = priQ[index];
            priQ[index] = new Edge();
            index--;
            downAdjustment();
            return temp;
        }

        public void insertion(Edge key) {
            priQ[++index] = key;
            upAdjustment();
        }

        public void upAdjustment() {
            int c = index;
            int parent = c / 2;
            Edge temp = priQ[c];
            while (c > 1 && temp.w > priQ[parent].w) {
                if (temp.w <= priQ[parent].w) {
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
            while (child <= index) {
                if (child + 1 <= index && priQ[child].w < priQ[child + 1].w) {
                    child++;
                }
                if (temp.w >= priQ[child].w) {
                    break;
                }
                priQ[c] = priQ[child];
                c = child;
                child *= 2;
            }
            priQ[c] = temp;
        }

        public boolean isEmpty() {
            return index == 0;
        }
    }
}
