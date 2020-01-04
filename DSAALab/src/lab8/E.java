package lab8;

import java.util.*;

public class E {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int k = in.nextInt();
        int[] array = new int[m];
        for (int i = 0; i < m; i++) {
            array[i] = in.nextInt();
        }
        BBSTNode root = new BBSTNode(array[0]);
        for (int i = 1; i < k; i++) {
            root = root.insert(root, array[i]);
        }
        for (int i = k; i < m; i++) {
            int num = in.nextInt();
            System.out.println(root.topK(root, num));
            root = root.remove(root, array[i - k]);
            root = root.insert(root, array[i]);
        }
        System.out.println(root.topK(root, in.nextInt()));
    }

    static class BBSTNode {
        int key;
        int size;
        int height;
        BBSTNode left;
        BBSTNode right;

        public BBSTNode(int key) {
            this(key, null, null);
        }

        public BBSTNode(int key, BBSTNode left, BBSTNode right) {
            this.key = key;
            this.size = 1;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        private int getSize(BBSTNode a) {
            return (a == null) ? 0 : a.size;
        }

        private int getHeight(BBSTNode a) {
            return (a == null) ? -1 : a.height;
        }

        private BBSTNode leftRotate(BBSTNode a) {
            int temp = a.size;
            BBSTNode b = a.left;
            a.left = b.right;
            b.right = a;
            b.size = temp;
            a.size = getSize(a.left) + getSize(a.right) + 1;
            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(a.left)) + 1;
            return b;
        }

        private BBSTNode rightRotate(BBSTNode a) {
            int temp = a.size;
            BBSTNode b = a.right;
            a.right = b.left;
            b.left = a;
            b.size = temp;
            a.size = getSize(a.right) + getSize(a.left) + 1;
            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            b.height = Math.max(getHeight(b.right), getHeight(a.right)) + 1;
            return b;
        }

        private BBSTNode doubleLrRotate(BBSTNode a) {
            a.left = rightRotate(a.left);
            return leftRotate(a);
        }

        private BBSTNode doubleRlRotate(BBSTNode a) {
            a.right = leftRotate(a.right);
            return rightRotate(a);
        }

        private BBSTNode insert(BBSTNode a, int key) {
            if (a == null) {
                return new BBSTNode(key);
            } else {
                a.size++;
                if (key < a.key) {
                    a.left = insert(a.left, key);
                } else if (key > a.key) {
                    a.right = insert(a.right, key);
                }
                a = maintain(a);
                return a;
            }
        }

        private BBSTNode maintain(BBSTNode a) {
            if (a == null) {
                return a;
            }
            if (getHeight(a.left) - getHeight(a.right) > 1) {
                if (getHeight(a.left.left) >= getHeight(a.left.right)) {
                    a = leftRotate(a);
                } else {
                    a = doubleLrRotate(a);
                }
            } else {
                if (getHeight(a.right) - getHeight(a.left) > 1) {
                    if (getHeight(a.right.right) >= getHeight(a.right.left)) {
                        a = rightRotate(a);
                    } else {
                        a = doubleRlRotate(a);
                    }
                }
            }
            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            return a;
        }

        private int topK(BBSTNode a, int k) {
            int count = getSize(a.left) + 1;
            if (count == k) {
                return a.key;
            } else if (count > k) {
                return topK(a.left, k);
            } else {
                return topK(a.right, k - count);
            }
        }

        private BBSTNode remove(BBSTNode a, int value) {
            if (a == null) {
                return a;
            }
            a.size--;
            if (value > a.key) {
                a.right = remove(a.right, value);
            } else if (value < a.key) {
                a.left = remove(a.left, value);
            } else if (a.left != null && a.right != null) {
                BBSTNode temp = a.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                a.key = temp.key;
                a.right = remove(a.right, a.key);
            } else {
                a = (a.left != null) ? a.left : a.right;
            }
            return maintain(a);
        }
    }
}
