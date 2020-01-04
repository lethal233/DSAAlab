package lab6;
//Think:
//1知识点：kmp算法—最小循环节
//2题意：输入一个原始字符串，选择在头部或者尾部添加别的字符，使得新的字符串为一个周期循环字符串，询问最小需要添加几个字符
//3题意分析：求最小循环节
//（1）：最小循环节：cir_len = len - next[len-1]
//（2）：如果cir_len != len && len%cir_len == 0则不需要再添加，除此之外，添加的字符数为cir_len - len%cir_len;

import java.io.*;
import java.math.*;
import java.util.*;

public class D {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {
            int testcase = in.nextInt();
            for (int i = 0; i < testcase; i++) {
                int counter;
                String necklace = in.next();
                int length = necklace.length();
                int[] next = nextArray(necklace);
                int circularLength = length - next[length - 1];
                if (circularLength != length && length % circularLength == 0) {
                    counter = 0;
                } else {
                    counter = circularLength - length % circularLength;
                }
                out.println(counter);
            }

        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }

    public static int[] nextArray(String p) {
        int m = p.length();
        int[] next = new int[m];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < m; ) {
            if (j == 0) {
                if (p.charAt(i) != p.charAt(j)) {
                    next[i] = 0;
                    i++;
                } else {
                    next[i] = j + 1;
                    j++;
                    i++;
                }
            } else {
                if (p.charAt(i) == p.charAt(j)) {
                    next[i] = j + 1;
                    j++;
                    i++;
                } else {
                    j = next[j - 1];
                }
            }
        }
        return next;
    }

}