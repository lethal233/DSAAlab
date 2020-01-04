package lab10;
//不要弄邻接矩阵，因为存在1 2 3 2 1 5的情况（多个路径）

import java.io.*;
import java.util.*;

public class A {

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
        int i = in.nextInt();
        int j = in.nextInt();
        g.dijkstra(i);
        if (g.vertex[j].distance != Long.MAX_VALUE) {
            out.println(g.vertex[j].distance);
        } else {
            out.println(-1);
        }
        /*for (int k = 1; k <= n; k++) {
            System.out.println(g.vertex[k].distance);
        }*/
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
        //long[][] weight;
        priorQueue q;

        public Graph(int n) {
            this.v = n + 1;
            vertex = new Vertex[v];
            for (int i = 1; i < v; i++) {
                vertex[i] = new Vertex(i);
            }
            //weight = new long[v][v];
            q = new priorQueue();

        }

        public void createEdge(int i, int j, long w) {
            vertex[i].adj.add(vertex[j]);
            //weight[i][j] = w;
            vertex[i].adjEdge.add(w);
            vertex[j].adj.add(vertex[i]);
            //weight[j][i] = w;
            vertex[j].adjEdge.add(w);
        }

        public void initial() {
            for (int i = 1; i < v; i++) {
                vertex[i].distance = Long.MAX_VALUE;
            }
        }

        public void dijkstra(int start) {
            initial();
            vertex[start].isExtracted = true;
            vertex[start].distance = 0;
            for (int i = 1; i < v; i++) {
                q.insertion(vertex[i]);
            }
            while (!q.isEmpty()) {
                Vertex tmp = q.popMin();
                for (int i = 0; i < tmp.adj.size(); i++) {
                    if (!tmp.adj.get(i).isExtracted) {
                        int ind = relax(tmp.key, tmp.adj.get(i).key, tmp.adjEdge.get(i));//weight[tmp.key][tmp.adj.get(i).key]);
                        if (ind != -1) {
                            vertex[tmp.adj.get(i).key].distance = tmp.distance + tmp.adjEdge.get(i);//weight[tmp.key][tmp.adj.get(i).key];
                            q.upAdjustment(ind);
                        }
                    }
                }
                tmp.isExtracted = true;
            }
        }

        public int relax(int u, int v, long w) {
            if (vertex[v].distance > vertex[u].distance + w) {
                //vertex[v].distance = vertex[u].distance + w;
                //vertex[v].parent = vertex[u];
                return vertex[v].index;
            } else {
                return -1;
            }
        }
    }

    static class Vertex {
        ArrayList<Vertex> adj;
        ArrayList<Long> adjEdge;
        long distance;
        boolean isExtracted;
        int key;
        int index;
        Vertex parent;

        public Vertex(int key) {
            this.key = key;
            isExtracted = false;
            distance = -1;
            parent = null;
            index = key;
            adj = new ArrayList<>();
            adjEdge = new ArrayList<>();
        }
    }

    static class priorQueue {
        private final int MAX = 1010;
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
            priQ[1].index =1;
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
                    //priQ[c] = priQ[parent];
                    c = parent;
                    parent /= 2;
                }
            }
            //priQ[c] = temp;
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
                //priQ[c] = priQ[child];
                c = child;
                child *= 2;
            }
            //priQ[c] = temp;
        }

        public boolean isEmpty() {
            return ind == 0;
        }
    }
}
