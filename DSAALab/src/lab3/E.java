package lab3;

import java.util.*;

public class E {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        int numberOfSpell1 = input.nextInt();
        int numberOfSpell2 = input.nextInt();
        soldier[] s = new soldier[number];

        for (int i = 0; i < number; i++) {
            long hp = input.nextLong();
            long attack = input.nextLong();
            long hpMinusAtt = hp - attack;
            s[i] = new soldier(hp, attack, hpMinusAtt);
        }

        long maxAttack1 = 0;
        long maxAttack2 = 0;
        MergeSort(s, 0, number - 1);

        if (numberOfSpell2 == 0) {
            for (int i = 0; i < number; i++) {
                maxAttack1 += s[i].getAttack();
            }
        } else {
            long[] findMax1 = new long[number];
            for (int i = 0; i < number; i++) {
                if (s[i].getHpMinusAttack() <= 0) {
                    maxAttack1 += s[i].getAttack();
                    findMax1[i] = s[i].getHp() * power(numberOfSpell1) - s[i].getAttack();
                } else {
                    if (i < number - numberOfSpell2 + 1) {
                        maxAttack1 += s[i].getAttack();
                        findMax1[i] = s[i].getHp() * power(numberOfSpell1) - s[i].getAttack();
                    } else {
                        maxAttack1 += s[i].getHp();
                        findMax1[i] = s[i].getHp() * power(numberOfSpell1) - s[i].getHp();
                    }
                }
            }
            MergeSort(findMax1, 0, number - 1);
            maxAttack1 += findMax1[number - 1];

            int j = 0;
            long max = s[0].getHp() * power(numberOfSpell1) - s[0].getAttack();
            for (int i = 0; i < number; i++) {
                maxAttack2 += s[i].getAttack();
                if (s[i].getHp() * power(numberOfSpell1) - s[i].getAttack() > max) {
                    max = s[i].getHp() * power(numberOfSpell1) - s[i].getAttack();
                    j = i;
                }
            }

            s[j].setHpMinusAttack(max);
            MergeSort(s, 0, number - 1);
            for (int i = number - 1; i >= 0; i--) {
                if (s[i].getHpMinusAttack() > 0 && i >= number - numberOfSpell2) {
                    maxAttack2 += s[i].getHpMinusAttack();
                }
            }
        }
        System.out.println(Math.max(maxAttack1, maxAttack2));

    }

    public static long power(int n) {
        if (n == 0) {
            return 1L;
        } else {
            return 2L * power(n - 1);
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

    public static soldier[] MergeSort(soldier[] A, int sta, int end) {
        if (sta < end) {
            int mid = (sta + end) / 2;
            MergeSort(A, sta, mid);
            MergeSort(A, mid + 1, end);
            Merge2(A, sta, mid, end);
        }
        return A;
    }

    public static void Merge2(soldier[] A, int sta, int mid, int end) {
        soldier[] ary = new soldier[end - sta + 1];
        int i = sta;
        int j = mid + 1;
        for (int k = 0; k < ary.length; k++) {
            if (i <= mid && (j > end || A[i].getHpMinusAttack() <= A[j].getHpMinusAttack())) {
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

class soldier {
    private long hp;
    private long attack;
    private long hpMinusAttack;

    public soldier(long hp, long attack, long hpMinusAttack) {
        this.hp = hp;
        this.attack = attack;
        this.hpMinusAttack = hpMinusAttack;
    }

    public long getHp() {
        return hp;
    }

    public long getAttack() {
        return attack;
    }

    public void setHpMinusAttack(long hpMinusAttack) {
        this.hpMinusAttack = hpMinusAttack;
    }

    public long getHpMinusAttack() {
        return hpMinusAttack;
    }
}
