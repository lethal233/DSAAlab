package lab7;

import java.util.*;

public class F {
    public static int ddf = 0;
    public static int dds = 0;
    public static int indf = 0;
    public static int inds = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            int k = input.nextInt();
            PairE[] p = new PairE[n - 1];
            for (int j = 0; j < n - 1; j++) {
                p[j] = new PairE(input.nextInt(), input.nextInt());
            }
            NodeE[] isFilled = new NodeE[n + 1];
            createEdge(p, isFilled, n);
            int[] initialStage = new int[k];
            for (int j = 0; j < k; j++) {
                initialStage[j] = input.nextInt();
                isFilled[k].hasPerson = true;
            }
            isFilled[initialStage[0]].depthF = 0;
            NodeDepthF(isFilled[initialStage[0]], isFilled);
            //int index1 = counterDepthF(isFilled, n);
            isFilled[indf].depthS = 0;
            NodeDepthS(isFilled[indf], isFilled);
            //int index2 = counterDepthS(isFilled, n);
            int result = (dds + 1) / 2;
            System.out.println(result);
        }
    }

    /*public static int counterDepthS(NodeE[] b, int n) {
        int max = b[1].depthS;
        int index = 1;
        for (int i = 2; i < n + 1; i++) {
            if (b[i].depthS > max) {
                max = b[i].depthS;
                index = i;
            }
        }
        return index;
    }*/

    /*public static int counterDepthF(NodeE[] b, int n) {
        int max = b[1].depthF;
        int index = 1;
        for (int i = 2; i < n + 1; i++) {
            if (b[i].depthF > max) {
                max = b[i].depthF;
                index = i;
            }
        }
        return index;
    }*/

    public static NodeE[] NodeDepthF(NodeE root, NodeE[] b) {
        for (int i = 0; i < root.near.size(); i++) {
            if (root.near.get(i).depthF == -1) {
                root.near.get(i).depthF = root.depthF + 1;
                if (root.near.get(i).hasPerson && ddf < root.near.get(i).depthF) {
                    ddf = root.near.get(i).depthF;
                    indf = root.near.get(i).key;
                }
                NodeDepthF(root.near.get(i), b);
            }
        }
        return b;
    }

    public static NodeE[] NodeDepthS(NodeE root, NodeE[] b) {
        for (int i = 0; i < root.near.size(); i++) {
            if (root.near.get(i).depthS == -1) {
                root.near.get(i).depthS = root.depthS + 1;
                if (root.near.get(i).hasPerson && dds < root.near.get(i).depthS) {
                    dds = root.near.get(i).depthS;
                    inds = root.near.get(i).key;
                }
                NodeDepthS(root.near.get(i), b);
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

    static class PairE {
        int a;
        int b;

        public PairE(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static class NodeE {
        int key;
        ArrayList<NodeE> near = new ArrayList<NodeE>(1);
        int depthF;
        int depthS;
        boolean hasPerson;

        public NodeE(int key) {
            this.key = key;
            depthF = -1;
            depthS = -1;
            hasPerson = false;
        }
    }
}
