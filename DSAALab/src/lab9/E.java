package lab9;
//拓扑+堆
//正常做法是正向建图+小顶堆
//此题做法是反向建图+大顶堆

import java.io.*;
import java.util.*;

public class E {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = in.nextInt();
            int m = in.nextInt();
            Graph g = new Graph(n);
            for (int j = 0; j < m; j++) {
                int a = in.nextInt();
                int b = in.nextInt();
                g.addEdge(a, b);
            }
            g.topologicalSort();
            while (!g.s.isEmpty()) {
                out.print(g.s.pop() + " ");
            }
            out.println();
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
        int V;
        priorQueue q;
        Stack<Integer> s = new Stack<>();

        public Graph(int v) {
            V = v + 1;
            vertex = new Vertex[V];
            for (int i = 1; i < V; i++) {
                vertex[i] = new Vertex(i);
            }
            q = new priorQueue();
        }

        public void addEdge(int a, int b) {
            vertex[b].adj.add(vertex[a]);
            vertex[a].inDegree++;
        }

        public void topologicalSort() {
            for (int i = 1; i < V; i++) {
                if (vertex[i].inDegree == 0) {
                    q.insertion(vertex[i].v);
                }
            }
            while (!q.isEmpty()) {
                int p = q.popMax();
                for (int i = 0; i < vertex[p].adj.size(); i++) {
                    if (--vertex[p].adj.get(i).inDegree == 0) {
                        q.insertion(vertex[p].adj.get(i).v);
                    }
                }
                s.push(p);
            }
        }
    }

    static class Vertex {
        int v;
        ArrayList<Vertex> adj;
        int inDegree;

        public Vertex(int v) {
            this.v = v;
            adj = new ArrayList<>();
            inDegree = 0;
        }
    }

    static class priorQueue {
        private final int MAX = 200009;
        int[] priQ;
        int ind = 0;

        public priorQueue() {
            priQ = new int[MAX];
        }

        public int popMax() {
            int temp = priQ[1];
            priQ[1] = priQ[ind];
            priQ[ind] = 0;
            ind--;
            downAdjustment();
            return temp;
        }

        public void insertion(int key) {
            priQ[++ind] = key;
            upAdjustment();
        }

        public void upAdjustment() {
            int c = ind;
            int parent = c / 2;
            int temp = priQ[c];
            while (c > 1 && temp > priQ[parent]) {
                if (temp <= priQ[parent]) {
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
            int temp = priQ[c];
            int child = 2 * c;
            while (child <= ind) {
                if (child + 1 <= ind && priQ[child] < priQ[child + 1]) {
                    child++;
                }
                if (temp >= priQ[child]) {
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
