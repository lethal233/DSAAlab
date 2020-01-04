package lab8;

import java.util.*;

public class F {
    public static long temp1 = -1;
    public static long temp2 = -1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long result = 0;
        int n = in.nextInt();
        BBSTNode r = new BBSTNode(in.nextInt(), in.nextLong());
        for (int i = 1; i < n; i++) {
            if (r == null) {
                r = new BBSTNode(in.nextInt(), in.nextLong());
            } else {
                int t = in.nextInt();
                long num = in.nextLong();
                if (t == r.type) {
                    r = r.insert(r, num, t);
                } else {
                    Stack<Long> s = new Stack<>();
                    long tmp1 = r.predecessorQuery(r, num, s);
                    s.clear();
                    long tmp2 = r.successorQuery(r, num, s);
                    s.clear();
                    if (tmp1 == -1 && tmp2 != -1) {
                        result += tmp2 - num;
                        r = r.remove(r, tmp2);
                    } else if (tmp1 != -1 && tmp2 == -1) {
                        result += num - tmp1;
                        r = r.remove(r, tmp1);
                    } else {
                        if (tmp1 == tmp2) {
                            r = r.remove(r, num);
                        } else if ((num - tmp1) <= (tmp2 - num)) {
                            r = r.remove(r, tmp1);
                            result += num - tmp1;
                        } else {
                            r = r.remove(r, tmp2);
                            result += tmp2 - num;
                        }
                    }

                }
            }
        }
        System.out.println(result);

    }

    static class BBSTNode {
        long key;
        int type;
        int height;
        int times;
        BBSTNode left;
        BBSTNode right;

        public BBSTNode(int type, long key) {
            this(type, key, null, null);
        }

        public BBSTNode(int type, long key, BBSTNode left, BBSTNode right) {
            this.key = key;
            this.type = type;
            this.left = left;
            this.right = right;
            height = 0;
            times = 1;
        }


        public int getHeight(BBSTNode a) {
            return (a == null) ? -1 : a.height;
        }

        private BBSTNode leftRotate(BBSTNode a) {
            BBSTNode b = a.left;
            a.left = b.right;
            b.right = a;
            a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
            b.height = Math.max(getHeight(b.left), getHeight(a.left)) + 1;
            return b;
        }

        private BBSTNode rightRotate(BBSTNode a) {
            BBSTNode b = a.right;
            a.right = b.left;
            b.left = a;
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

        private BBSTNode insert(BBSTNode a, long key, int type) {
            if (a == null) {
                return new BBSTNode(type, key);
            } else {
                if (key < a.key) {
                    a.left = insert(a.left, key, type);
                } else if (key > a.key) {
                    a.right = insert(a.right, key, type);
                } else {
                    a.times++;
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

        private BBSTNode remove(BBSTNode a, long value) {
            if (a == null) {
                return a;
            }
            if (value > a.key) {
                a.right = remove(a.right, value);
            } else if (value < a.key) {
                a.left = remove(a.left, value);
            } else {
                if (a.times == 1) {
                    if (a.left != null && a.right != null) {
                        BBSTNode temp = a.right;
                        while (temp.left != null) {
                            temp = temp.left;
                        }
                        a.key = temp.key;
                        a.right = remove(a.right, a.key);
                    } else {
                        a = (a.left != null) ? a.left : a.right;
                    }
                } else {
                    a.times--;
                }
            }
            return maintain(a);
        }

        private long predecessorQuery(BBSTNode a, long value, Stack<Long> s) {
            if (a == null) {
                if (s.empty()) {
                    temp1 = -1;
                } else {
                    temp1 = s.pop();
                }
            } else {
                if (value < a.key) {
                    predecessorQuery(a.left, value, s);
                } else if (value > a.key) {
                    s.push(a.key);
                    predecessorQuery(a.right, value, s);
                } else {
                    temp1 = value;
                }
            }
            return temp1;
        }

        private long successorQuery(BBSTNode a, long value, Stack<Long> s) {
            if (a == null) {
                if (s.empty()) {
                    temp2 = -1;
                } else {
                    temp2 = s.pop();
                }
            } else {
                if (value < a.key) {
                    s.push(a.key);
                    successorQuery(a.left, value, s);
                } else if (value > a.key) {
                    successorQuery(a.right, value, s);
                } else {
                    temp2 = value;
                }
            }
            return temp2;
        }
    }
}
