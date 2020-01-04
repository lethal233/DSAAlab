package lab10;

import java.io.*;
import java.util.*;

public class E {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int p = in.nextInt();
        int k = in.nextInt();
        Graph g = new Graph(n, k);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            long w = in.nextLong();
            g.createEdge(u, v, w);
        }
        for (int i = 0; i < p; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            g.createEdge(u, v, 0L);
        }
        int s = in.nextInt();
        int t = in.nextInt();
        g.dijkstra(s);
        long minPath = Long.MAX_VALUE;
        for (int i = t; i < g.vertices.length; i += n) {
            if (g.vertices[i].distance < minPath) {
                minPath = g.vertices[i].distance;
            }
        }
        out.println(minPath);
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
        Vertex[] vertices;
        int v;
        int single;
        priorQueue q;

        public Graph(int n, int k) {
            single = n;
            this.v = n * (k + 1) + 1;
            vertices = new Vertex[v];
            for (int i = 1; i < v; i++) {
                vertices[i] = new Vertex(i);
            }
            q = new priorQueue();
        }

        public void createEdge(int i, int j, long w) {
            int is = i;
            int js = j;
            if (w == 0) {
                while (true) {
                    js += single;
                    if (js >= v) {
                        break;
                    } else {
                        vertices[is].adj.add(vertices[js]);
                        vertices[is].adjEdge.add(w);
                        is += single;
                    }
                }
            } else {
                while (true) {
                    if (is >= v || js >= v) {
                        break;
                    } else {
                        vertices[is].adj.add(vertices[js]);
                        vertices[is].adjEdge.add(w);
                        is += single;
                        js += single;
                    }
                }
            }
        }

        public void initial(int start) {
            vertices[start].distance = 0L;
            vertices[start].isVisited = true;
        }

        public void dijkstra(int start) {
            initial(start);
            for (int i = 1; i < v; i++) {
                q.insertion(vertices[i]);
            }
            while (!q.isEmpty()) {
                Vertex tmp = q.popMin();
                for (int i = 0; i < tmp.adj.size(); i++) {
                    if (!tmp.adj.get(i).isVisited) {
                        int ind = relax(tmp.key, tmp.adj.get(i).key, tmp.adjEdge.get(i));
                        if (ind != -1) {
                            vertices[tmp.adj.get(i).key].distance = tmp.distance + tmp.adjEdge.get(i);
                            q.upAdjustment(ind);
                        }
                    }
                }
                tmp.isVisited = true;
            }
        }

        public int relax(int u, int v, long w) {
            if (vertices[v].distance > vertices[u].distance + w) {
                return vertices[v].index;
            } else {
                return -1;
            }
        }
    }

    static class Vertex {
        ArrayList<Vertex> adj;
        ArrayList<Long> adjEdge;
        int key;
        boolean isVisited;
        long distance;
        int index;

        public Vertex(int key) {
            this.key = key;
            isVisited = false;
            distance = Long.MAX_VALUE;
            adj = new ArrayList<>();
            adjEdge = new ArrayList<>();
            index = key;
        }
    }

    static class priorQueue {
        private final int MAX = 600000;
        Vertex[] priQ;
        int ind = 0;

        public priorQueue() {
            priQ = new Vertex[MAX];
            for (int i = 1; i < MAX; i++) {
                priQ[i] = new Vertex(0);
            }
        }

        public Vertex popMin() {
            Vertex temp = priQ[1];
            priQ[1] = priQ[ind];
            priQ[1].index = 1;
            priQ[ind] = new Vertex(0);
            ind--;
            downAdjustment();
            return temp;
        }

        public void insertion(Vertex key) {
            priQ[++ind] = key;
            upAdjustment(ind);
        }

        public void upAdjustment(int toBeAdjust) {
            int c = toBeAdjust;
            int parent = c / 2;
            Vertex temp = priQ[c];
            while (c > 1 && temp.distance < priQ[parent].distance) {
                if (temp.distance >= priQ[parent].distance) {
                    break;
                } else {
                    Vertex tmp = priQ[c];
                    priQ[c] = priQ[parent];
                    priQ[parent] = tmp;
                    int tempInd = priQ[c].index;
                    priQ[c].index = priQ[parent].index;
                    priQ[parent].index = tempInd;
                    c = parent;
                    parent /= 2;
                }
            }
        }

        public void downAdjustment() {
            int c = 1;
            Vertex temp = priQ[c];
            int child = 2 * c;
            while (child <= ind) {
                if (child + 1 <= ind && priQ[child].distance > priQ[child + 1].distance) {
                    child++;
                }
                if (temp.distance <= priQ[child].distance) {
                    break;
                }
                Vertex tmp = priQ[c];
                priQ[c] = priQ[child];
                priQ[child] = tmp;
                int tempInd = priQ[c].index;
                priQ[c].index = priQ[child].index;
                priQ[child].index = tempInd;
                c = child;
                child *= 2;
            }
        }

        public boolean isEmpty() {
            return ind == 0;
        }
    }
}