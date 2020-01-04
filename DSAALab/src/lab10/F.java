package lab10;

import java.io.*;
import java.util.*;

public class F {
    static int[] s;
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
            g.insertVertex(x, y, r, t);
            gT.insertVertex(x, y, r, t);
        }
        gT.createReverseEdge();
        g.createEdge();
        gT.DFSFirstOnTranspose();
        g.DFSonOriginal();
        
        out.close();
    }


    static class Graph {
        Vertex[] vertex;
        int num;
        int ind;
        long maxPath;
        int start;
        int total;

        public Graph(int num) {
            this.num = num;
            total = num;
            vertex = new Vertex[num];
            for (int i = 0; i < num; i++) {
                vertex[i] = new Vertex(0, 0, 0, 0, 0);
            }
            ind = 0;
            maxPath = 0;
            start = 0;
        }

        public void DFSFirstOnTranspose(){
            for (int i = 1; i < num; i++) {
                if (vertex[i].color == 0) {
                    performDFSFirstOnTranspose(i);
                }
            }
        }
        public void performDFSFirstOnTranspose(int i) {
            vertex[i].color = 1;
            for (Vertex v : vertex[i].adj) {
                if (v.color == 0) {
                    performDFSFirstOnTranspose(v.ind);
                }
            }
            vertex[i].color = 2;
            s[--total] = i;
        }
        public void DFSonOriginal() {
            performDFSOnOriginal(s[0]);
        }

        public void performDFSOnOriginal(int i) {
            vertex[i].color = 1;
            for (Vertex x : vertex[i].adj) {
                if (x.color == 0) {
                    performDFSOnOriginal(x.ind);
                }
            }
            vertex[i].color = 2;
            //todo
        }
        public void insertVertex(long x, long y, long r, int t) {
            vertex[ind] = new Vertex(x, y, r, t, ind);
            ind++;
        }

        public void createEdge() {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if (j != i) {
                        if (isEdge(vertex[i], vertex[j])) {
                            vertex[i].adj.add(vertex[j]);
                            vertex[j].inDegree++;
                        }
                    }
                }
            }
        }

        public void createReverseEdge() {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    if (j != i) {
                        if (isEdge(vertex[i], vertex[j])) {
                            vertex[j].adj.add(vertex[i]);
                            vertex[i].inDegree++;
                        }
                    }
                }
            }
        }

        public boolean isEdge(Vertex i, Vertex j) {
            if ((i.x - j.x) * (i.x - j.x) + (i.y - j.y) * (i.y - j.y) <= i.r * i.r) {
                return true;
            } else {
                return false;
            }
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

    static class Vertex {
        long x;
        long y;
        long r;
        int t;
        int inDegree;
        int ind;
        int color;
        boolean isVisited;
        ArrayList<Vertex> adj;

        public Vertex(long x, long y, long r, int t, int ind) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.t = t;
            this.ind = ind;
            inDegree = 0;
            isVisited = false;
            color = 0;
            adj = new ArrayList<>();
        }

    }
}
