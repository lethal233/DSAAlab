package lab7;

import java.util.*;

public class E {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            PairE[] p = new PairE[n - 1];
            for (int j = 0; j < n - 1; j++) {
                p[j] = new PairE(input.nextInt(), input.nextInt());
            }
            NodeE[] isFilled = new NodeE[n + 1];
            createEdge(p, isFilled, n);
            assignRedAndBlue(isFilled, input, n);
            isFilled[1].depth = 0;
            NodeDepth(isFilled[1], isFilled);
            countRedAndBlue(isFilled[1], isFilled);
            int count = counter(isFilled, n);
            System.out.println(count);
        }
    }
    static class NodeE {
        int key;
        ArrayList<NodeE> near = new ArrayList<NodeE>(1);
        int red;
        int blue;
        int depth;

        public NodeE(int key) {
            this.key = key;
            depth = -1;
        }
    }

    static class PairE {
        int a;
        int b;

        public PairE(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static int counter(NodeE[] isFilled, int n) {
        int count = 0;
        for (int j = 2; j < n + 1; j++) {
            if ((isFilled[j].red == isFilled[1].red && isFilled[j].blue == 0)
                    || (isFilled[j].blue == isFilled[1].blue && isFilled[j].red == 0)) {
                count++;
            }
        }
        return count;
    }

    public static NodeE[] assignRedAndBlue(NodeE[] isFilled, Scanner input, int n) {
        for (int j = 1; j < n + 1; j++) {
            int c = input.nextInt();
            if (c == 1) {
                isFilled[j].red = 1;
            }
            if (c == 2) {
                isFilled[j].blue = 1;
            }
        }
        return isFilled;
    }

    public static NodeE[] NodeDepth(NodeE root, NodeE[] b) {
        for (int i = 0; i < root.near.size(); i++) {
            if (root.near.get(i).depth == -1) {
                root.near.get(i).depth = root.depth + 1;
                NodeDepth(root.near.get(i), b);
            }
        }
        return b;
    }

    public static NodeE[] countRedAndBlue(NodeE root, NodeE[] b) {
        for (int i = 0; i < root.near.size(); i++) {
            if (root.near.get(i).depth > root.depth) {
                countRedAndBlue(root.near.get(i), b);
                root.red += root.near.get(i).red;
                root.blue += root.near.get(i).blue;
            }
        }
        return b;
    }

    public static NodeE[] createEdge(PairE[] p, NodeE[] isFilled, int n) {
        for (int j = 0; j < n - 1; j++) {
            if (isFilled[p[j].a] == null) {
                NodeE tmp1 = new NodeE(p[j].a);
                isFilled[p[j].a] = tmp1;
            }
            if (isFilled[p[j].b] == null) {
                NodeE tmp2 = new NodeE(p[j].b);
                isFilled[p[j].b] = tmp2;
            }
            isFilled[p[j].a].near.add(isFilled[p[j].b]);
            isFilled[p[j].b].near.add(isFilled[p[j].a]);
        }
        return isFilled;
    }
}


