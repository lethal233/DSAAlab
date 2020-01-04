package lab7;

import java.util.*;

public class C {
    static class TreeNodeC {
        int key;
        TreeNodeC left;
        TreeNodeC right;

        public TreeNodeC(int key) {
            this.key = key;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        for (int i = 0; i < testcase; i++) {
            int N = input.nextInt();
            int[] preOrder = new int[N];
            int[] inOrder = new int[N];
            for (int j = 0; j < N; j++) {
                preOrder[j] = input.nextInt();
            }
            for (int j = 0; j < N; j++) {
                inOrder[j] = input.nextInt();
            }
            TreeNodeC root = recoverTree(preOrder, 0, N - 1, inOrder, 0, N - 1);
            printPostOrder(root);
            System.out.println();
        }
    }

    public static TreeNodeC recoverTree(int[] preOrder, int sta, int end, int[] inOrder, int low, int high) {
        if (sta > end && low > high) {
            return null;
        }
        TreeNodeC root = new TreeNodeC(preOrder[sta]);
        int idx;
        int pre;
        idx = findInOrder(inOrder, low, high, preOrder[sta]);
        pre = sta + idx - low;
        root.left = recoverTree(preOrder, sta + 1, pre, inOrder, low, idx - 1);
        root.right = recoverTree(preOrder, pre + 1, end, inOrder, idx + 1, high);
        return root;
    }

    public static int findInOrder(int[] inOrder, int begin, int end, int search) {
        int i = begin;
        int idx = -1;
        for (; i <= end; i++) {
            if (search == inOrder[i]) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    public static void printPostOrder(TreeNodeC root) {
        if (root.left != null) {
            printPostOrder(root.left);
        }
        if (root.right != null) {
            printPostOrder(root.right);
        }
        System.out.print(root.key + " ");
    }
}


