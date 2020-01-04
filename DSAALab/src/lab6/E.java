package lab6;

import java.io.*;
import java.math.*;
import java.util.*;
//先预处理power
public class E {
    static final long radix = 139L;//基数可选质数 131:588ms 10007:592ms 139/97:588ms
    static long power;

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
            String a = in.next();
            String b = in.next();
            int an = a.length();
            int bn = b.length();
            int result = 0;

            if (an > bn) {
                int low = 1;
                int high = bn;
                int q = bn;
                int temp = bn;
                while (low < high) {
                    power = power(q - 1);

                    long[] bArray = new long[bn - q + 1];
                    bArray[0] = hash(b.substring(0, q));
                    for (int i = 1; i < bn - q + 1; i++) {
                        bArray[i] = (bArray[i - 1] - b.charAt(i - 1) * power) * radix + b.charAt(i + q - 1);
                    }

                    long[] aArray = new long[an - q + 1];
                    aArray[0] = hash(a.substring(0, q));
                    for (int i = 1; i < an - q + 1; i++) {
                        aArray[i] = (aArray[i - 1] - a.charAt(i - 1) * power) * radix + a.charAt(i + q - 1);
                    }
                    MergeSort(aArray, 0, aArray.length - 1);

                    int counter = 0;
                    for (int i = 0; i < bArray.length; i++) {
                        if (binarySearch(aArray, 0, aArray.length - 1, bArray[i]) != -1) {
                            break;
                        } else {
                            counter++;
                        }
                    }

                    if (counter == bArray.length) {
                        high = q;
                        q = (low + high) / 2;
                        temp = high;
                    } else {
                        if (result == q) {
                            break;
                        } else {
                            result = q;
                            if (q == bn) {
                                break;
                            } else {
                                low = q;
                                high = temp;
                                q = (low + high) / 2;
                            }
                        }

                    }
                }
                out.println(result);
            } else {
                int low = 1;
                int high = an;
                int q = an;
                int temp = an;
                while (low < high) {
                    power = power(q - 1);

                    long[] aArray = new long[an - q + 1];
                    aArray[0] = hash(a.substring(0, q));
                    for (int i = 1; i < an - q + 1; i++) {
                        aArray[i] = (aArray[i - 1] - a.charAt(i - 1) * power) * radix + a.charAt(i + q - 1);
                    }

                    long[] bArray = new long[bn - q + 1];
                    bArray[0] = hash(b.substring(0, q));
                    for (int i = 1; i < bn - q + 1; i++) {
                        bArray[i] = (bArray[i - 1] - b.charAt(i - 1) * power) * radix + b.charAt(i + q - 1);
                    }
                    MergeSort(bArray, 0, bArray.length - 1);

                    int counter = 0;
                    for (int i = 0; i < aArray.length; i++) {
                        if (binarySearch(bArray, 0, bArray.length - 1, aArray[i]) != -1) {
                            break;
                        } else {
                            counter++;
                        }
                    }
                    if (counter == aArray.length) {
                        high = q;
                        q = (low + high) / 2;
                        temp = high;
                    } else {
                        if (result == q) {
                            break;
                        } else {
                            result = q;
                            if (q == an) {
                                break;
                            } else {
                                low = q;
                                high = temp;
                                q = (low + high) / 2;
                            }
                        }
                    }
                }
                out.println(result);
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

    public static long hash(String key) {
        long hashVal = 0;
        for (int i = 0; i < key.length(); i++) {
            hashVal = radix * hashVal + key.charAt(i);
        }
        return hashVal;
    }

    public static long power(int m) {
        long result = 1L;
        for (int i = 0; i < m; i++) {
            result *= radix;
        }
        return result;
    }

    public static int binarySearch(long[] arr, int left, int right, long search) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (search == arr[mid]) {
            return mid;
        } else if (search < arr[mid]) {
            right = mid - 1;
            return binarySearch(arr, left, right, search);
        } else {
            left = mid + 1;
            return binarySearch(arr, left, right, search);
        }
    }

    public static long[] MergeSort(long[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge(long[] A, int sta, int mid, int end) {
        long[] ary = new long[end - sta + 1];
        int i = sta;
        int j = mid + 1;
        for (int k = 0; k < ary.length; k++) {
            if (i <= mid && (j > end || A[i] <= A[j])) {
                ary[k] = A[i];
                i++;
            } else {
                ary[k] = A[j];
                j++;
            }
        }
        for (int t = 0; t < ary.length; t++) {
            A[t + sta] = ary[t];
        }
    }


}