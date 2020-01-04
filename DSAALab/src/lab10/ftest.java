package lab10;
//强连通图缩点
import java.io.*;
import java.util.*;

public class ftest {
    static int[] s;
    static ArrayList<Vertex> ver;
    static int idr = 0;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int bomb = in.nextInt();
        Graph g = new Graph(bomb);
        Graph gT = new Graph(bomb);
        s = new int[bomb];

        for (int i = 0; i < bomb; i++) {
            long x = in.nextLong();
            long y = in.nextLong();
            long r = in.nextLong();
            int t = in.nextInt();
            g.insertVertices(x, y, r, t);
            gT.insertVertices(x, y, r, t);
        }

        for (int i = 0; i < bomb; i++) {
            for (int j = 0; j < bomb; j++) {
                if (j != i) {
                    if (Math.pow((g.vertices[i].x - g.vertices[j].x), 2) + Math.pow((g.vertices[i].y - g.vertices[j].y), 2) <= Math.pow(g.vertices[i].r, 2)) {
                        g.vertices[i].adj.add(g.vertices[j]);
                        gT.vertices[j].adj.add(gT.vertices[i]);
                    }
                }
            }
        }
        ver = new ArrayList<>();
        gT.DFSTranspose();
        g.DFSRight();

        for (int i = 0; i < g.vertices.length; i++) {
            for (Vertex x : g.vertices[i].adj) {
                if (x.color != g.vertices[i].color) {
                    ver.get(g.vertices[i].color).adj.add(ver.get(x.color));
                    ver.get(x.color).inDegree++;
                }
            }
        }
        long sum = 0;
        for (Vertex x : ver) {
            if (x.inDegree == 0) {
                sum += x.t;
            }
        }
        out.println(sum);
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
        int V;
        int ind;
        int num;
        int k;
        int max;
        int idx;

        public Graph(int v) {
            V = v;
            num = v - 1;
            vertices = new Vertex[V];
            for (int i = 0; i < V; i++) {
                vertices[i] = new Vertex();
            }
            ind = 0;
            k = 0;
            max = Integer.MAX_VALUE;
            idx = -1;
        }

        void insertVertices(long x, long y, long r, int t) {
            vertices[ind] = new Vertex(x, y, r, t, ind);
            ind++;
        }

        void DFSTranspose() {
            for (int i = 0; i < V; i++) {
                if (!vertices[i].isVisited) {
                    DFSTransposeVisit(i);
                }
            }
        }

        void DFSTransposeVisit(int v) {
            vertices[v].isVisited = true;
            for (Vertex x : vertices[v].adj) {
                if (!x.isVisited) {
                    DFSTransposeVisit(x.index);
                }
            }
            s[num--] = v;
        }

        void DFSRight() {
            for (int i = 0; i < V; i++) {
                if (!vertices[s[i]].isVisited) {
                    DFSVisit(s[i], k++);
                    ver.add(new Vertex(vertices[idx].x, vertices[idx].y, vertices[idx].r, vertices[idx].t, idr++));
                    idx = -1;
                    max = Integer.MAX_VALUE;
                }

            }
        }

        void DFSVisit(int v, int IND) {
            vertices[v].isVisited = true;
            if (vertices[v].t < max) {
                max = vertices[v].t;
                idx = v;
            }
            for (Vertex x : vertices[v].adj) {
                if (!x.isVisited) {
                    DFSVisit(x.index, IND);
                }
            }
            vertices[v].color = IND;
        }


    }

    static class Vertex {
        long x;
        long y;
        long r;
        int t;
        ArrayList<Vertex> adj;
        int index;
        int color;//SCC index
        boolean isVisited;
        int inDegree;

        public Vertex(long x, long y, long r, int t, int index) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.t = t;
            this.index = index;
            adj = new ArrayList<>();
            color = 0;
            isVisited = false;
            inDegree = 0;
        }

        public Vertex() {
            this(0L, 0L, 0L, 0, 0);
        }
    }
}
