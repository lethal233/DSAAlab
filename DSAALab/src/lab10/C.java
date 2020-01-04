package lab10;

import java.io.*;
import java.util.*;

public class C {
    public static int[] s;
    public static int number = 0;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        s = new int[n];
        Graph gT = new Graph(n);
        Graph g = new Graph(n);
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            g.createEdge(u, v);
            gT.createEdge(v, u);
        }
        gT.DFSFirstOnTranspose();
        g.DFSonOriginal();
        if (number == n) {
            out.println("Bravo");
        } else {
            out.println("ovarB");
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
        int v;
        int num;
        Vertex[] vertex;


        public Graph(int n) {
            this.v = n + 1;
            num = n;
            vertex = new Vertex[v];
            for (int i = 1; i < v; i++) {
                vertex[i] = new Vertex(i);
            }
        }

        public void createEdge(int u, int v) {
            vertex[u].adj.add(vertex[v]);
        }

        public void DFSFirstOnTranspose() {
            for (int i = 1; i < v; i++) {
                if (vertex[i].color == 0) {
                    performDFSFirstOnTranspose(i);
                }
            }
        }

        public void performDFSFirstOnTranspose(int i) {
            vertex[i].color = 1;
            for (Vertex v : vertex[i].adj) {
                if (v.color == 0) {
                    performDFSFirstOnTranspose(v.key);
                }
            }
            vertex[i].color = 2;
            s[--num] = i;
        }

        public void DFSonOriginal() {
            performDFSOnOriginal(s[0]);
        }

        public void performDFSOnOriginal(int i) {
            vertex[i].color = 1;
            for (Vertex x : vertex[i].adj) {
                if (x.color == 0) {
                    performDFSOnOriginal(x.key);
                }
            }
            vertex[i].color = 2;
            number++;
        }
    }

    static class Vertex {
        int key;
        int color;
        ArrayList<Vertex> adj;

        public Vertex(int key) {
            this.key = key;
            adj = new ArrayList<>();
            color = 0;
        }
    }
}
