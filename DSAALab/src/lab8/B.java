package lab8;

import java.util.*;

public class B {
    static class BinaryTree {
        int key;
        BinaryTree left;
        BinaryTree right;

        public BinaryTree(int key) {
            this.key = key;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcase = in.nextInt();
        for (int i = 1; i <= testcase; i++) {
            int n = in.nextInt();
            BinaryTree root = buildTree(in, n);
            if (root == null) {
                System.out.println("Case #" + i + ": NO");
            } else {
                boolean flag = isCompleteBinaryTree(root, 1, n) && (isHeapMin(root) || isHeapMax(root));
                if (flag) {
                    System.out.println("Case #" + i + ": YES");
                } else {
                    System.out.println("Case #" + i + ": NO");
                }
            }


        }
    }

    public static BinaryTree buildTree(Scanner in, int n) {
        int[] parent = new int[100009];
        ArrayList<BinaryTree> treeList = new ArrayList<>();
        treeList.add(new BinaryTree(0));
        for (int j = 1; j <= n; j++) {
            parent[j] = -1;
            int value = in.nextInt();
            treeList.add(new BinaryTree(value));
        }

        for (int j = 1; j <= n - 1; j++) {
            int papaInd = in.nextInt();
            int babyInd = in.nextInt();
            if (treeList.get(papaInd).left == null) {
                treeList.get(papaInd).left = treeList.get(babyInd);

                parent[babyInd] = papaInd;

            } else if (treeList.get(papaInd).right == null) {
                treeList.get(papaInd).right = treeList.get(babyInd);

                parent[babyInd] = papaInd;
            } else return null;
        }
        int i = 1;
        for (; i <= n; i++) {
            if (parent[i] == -1) {
                break;
            }
        }
        return treeList.get(i);
    }

    public static boolean isHeapMin(BinaryTree root) {
        boolean judge = false;
        if (root.left == null && root.right == null) {
            return true;
        } else {
            if (root.left != null) {
                if (root.key > root.left.key) {
                    return false;
                }
                judge = isHeapMin(root.left);
            }
            if (root.right != null) {
                if (root.key > root.right.key) {
                    return false;
                }
                judge = isHeapMin(root.right);
            }
        }
        return judge;
    }

    public static boolean isHeapMax(BinaryTree root) {
        boolean judge = false;
        if (root.left == null && root.right == null) {
            return true;
        } else {
            if (root.left != null) {
                if (root.key < root.left.key) {
                    return false;
                }
                judge = isHeapMax(root.left);
            }
            if (root.right != null) {
                if (root.key < root.right.key) {
                    return false;
                }
                judge = isHeapMax(root.right);
            }
        }
        return judge;
    }

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
