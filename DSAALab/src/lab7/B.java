package lab7;

import java.util.*;

public class B {
    static class TreeNode {
        int key;
        ArrayList<TreeNode> near = new ArrayList<>();
        int depth;

        public TreeNode(int key) {
            this.key = key;
            depth = -1;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }
    }

    static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = input.nextInt();
            Pair[] p = new Pair[n - 1];
            for (int j = 0; j < n - 1; j++) {
                p[j] = new Pair(input.nextInt(), input.nextInt());
            }
            TreeNode[] isFilled = new TreeNode[n + 1];
            createEdge(p, isFilled, n);
            int[] allDepth = new int[n + 1];
            isFilled[1].setDepth(0);
            NodeDepth(isFilled[1], allDepth);
            for (int j = 1; j < n + 1; j++) {
                System.out.print(allDepth[j] + " ");
            }
            System.out.println();
        }
    }

    public static int[] NodeDepth(TreeNode root, int[] alldepth) {
        for (int i = 0; i < root.near.size(); i++) {
            if (root.near.get(i).depth == -1) {
                root.near.get(i).setDepth(root.depth + 1);
                alldepth[root.near.get(i).key] = root.near.get(i).depth;
                NodeDepth(root.near.get(i), alldepth);
            }
        }
        return alldepth;
    }

    public static TreeNode[] createEdge(Pair[] p, TreeNode[] isFilled, int n) {
        for (int j = 0; j < n - 1; j++) {
            if (isFilled[p[j].a] == null) {
                TreeNode tmp1 = new TreeNode(p[j].a);
                isFilled[p[j].a] = tmp1;
            }
            if (isFilled[p[j].b] == null) {
                TreeNode tmp2 = new TreeNode(p[j].b);
                isFilled[p[j].b] = tmp2;
            }
            isFilled[p[j].a].near.add(isFilled[p[j].b]);
            isFilled[p[j].b].near.add(isFilled[p[j].a]);
        }
        return isFilled;
    }

}


