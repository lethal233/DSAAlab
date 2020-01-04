package lab8;


import java.util.*;

public class A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcase = in.nextInt();
        for (int i = 0; i < testcase; i++) {
            int n = in.nextInt();
            //拿一个数组来存parent的值
            int[] parent = new int[150005];
            BinaryTree root = buildTree(n, parent, in);
            if (isCompleteBinaryTree(root, 1, n)) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }


    static class BinaryTree {
        int key;
        BinaryTree left;
        BinaryTree right;

        public BinaryTree(int key) {
            this.key = key;
        }
    }

    public static BinaryTree buildTree(int n, int[] parent, Scanner in) {
        ArrayList<BinaryTree> treeList = new ArrayList<>();
        treeList.add(new BinaryTree(0));
        for (int j = 1; j <= n; j++) {
            parent[j] = -1;
            treeList.add(new BinaryTree(j));
        }
        for (int j = 1; j <= n; j++) {
            int leftChild = in.nextInt();
            int rightChild = in.nextInt();
            if (leftChild != 0) {
                treeList.get(j).left = treeList.get(leftChild);
            }
            if (rightChild != 0) {
                treeList.get(j).right = treeList.get(rightChild);
            }
            parent[leftChild] = j;
            parent[rightChild] = j;
        }
        int i = 1;
        for (; i <= n; i++) {
            if (parent[i] == -1) {
                break;
            }
        }
        return treeList.get(i);
    }
    /*public static boolean isCompleteBinaryTree(BinaryTree root, int n) {
        ArrayList<BinaryTree> linearTree = new ArrayList<>(n + 5);
        Queue<BinaryTree> q;
        int count = 1;
        linearTree.add

    }*/

    public static boolean isCompleteBinaryTree(BinaryTree root, int index, int n) {
        if (index > n) {
            return false;
        }
        if (root.left != null) {
            if (!isCompleteBinaryTree(root.left, index * 2, n)) {
                return false;
            }
        }
        if (root.right != null) {
            if (!isCompleteBinaryTree(root.right, index * 2 + 1, n)) {
                return false;
            }
        }
        return true;
    }
}
