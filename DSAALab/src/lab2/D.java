package lab2;

import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int length = Integer.parseInt(input.nextLine());
        long target = Long.parseLong(input.nextLine());
        long[] sequence = new long[length];
        for (int i = 0; i < length; i++) {
            sequence[i] = input.nextLong();
        }
        if (target == 0) {
            //只有1个0
            if (countZero(sequence) == 1) {
                int counter = countNoRepeated(sequence) - 1;
                System.out.println(counter);
            }
            //没有0
            else if (countZero(sequence) == 0) {
                System.out.println(0);
            }
            //有两个或两个以上的0
            else {
                // 计数器从1开始
                int counter = countNoRepeated(sequence);
                System.out.println(counter);
            }
        } else {
            //计数器
            int counter = 0;
            for (int i = 0; i < length; ) {
                //当index为i的时候sequence[i]不为0
                if (sequence[i] != 0) {
                    //当target能够被sequence[i]整除时
                    if (target % sequence[i] == 0) {
                        //计算商，进行二分法查找
                        long temp = target / sequence[i];
                        //若搜索不同
                        if (binaryLowerBoundSearch(sequence, 0, length, sequence[i])
                                == binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i])) {
                            //若商和sequence[i]相等
                            if (temp == sequence[i]) {
                                i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                            }
                            //若不相等且temp的index大于i
                            else if (temp > sequence[i]) {
                                //若商存在于数组中
                                if (binaryLowerBoundSearch(sequence, 0, length - 1, temp) != -1) {
                                    counter++;
                                    i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                                }
                                //不在数组中时index直接加1
                                else {
                                    i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                                }
                            } else {
                                i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                            }
                        } else {
                            if (temp == sequence[i]) {
                                counter++;
                                i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                            }
                            //若不相等且temp的index大于i
                            else if (temp > sequence[i]) {
                                //若商存在于数组中
                                if (binaryLowerBoundSearch(sequence, 0, length - 1, temp) != -1) {
                                    counter++;
                                    i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                                }
                                //不在数组中时index直接加1
                                else {
                                    i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                                }
                            } else {
                                i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                            }
                        }

                    }
                    //不能整除时index直接加1
                    else {
                        i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                    }
                }
                //sequence[i]=0时index直接加1
                else {
                    i = binaryUpperBoundSearch(sequence, 0, length - 1, sequence[i]) + 1;
                }
            }
            System.out.println(counter);
        }

    }

    public static int binaryLowerBoundSearch(long[] arr, int left, int right, long search) {
        boolean isBiSearch = false;
        int j = 0;
        int lowerIndex = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > search) {
                right = mid - 1;
            } else if (arr[mid] < search) {
                left = mid + 1;
            } else {
                isBiSearch = true;
                j = mid;
                break;
            }
        }
        if (isBiSearch) {
            if (j == 0) {
                return 0;
            } else {
                for (int i = j; i >= 0; i--) {
                    if (i != 0) {
                        if (arr[i] < search) {
                            lowerIndex = i + 1;
                            break;
                        }
                    } else {
                        lowerIndex = 0;
                    }

                }
            }
            return lowerIndex;
        } else return -1;
    }

    public static int binaryUpperBoundSearch(long[] arr, int left, int right, long search) {
        boolean isBiSearch = false;
        int j = 0;
        int UpperIndex = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > search) {
                right = mid - 1;
            } else if (arr[mid] < search) {
                left = mid + 1;
            } else {
                isBiSearch = true;
                j = mid;
                break;
            }
        }
        if (isBiSearch) {
            if (j == arr.length - 1) {
                return arr.length - 1;
            } else {
                for (int i = j; i < arr.length; i++) {
                    if (i != arr.length - 1) {
                        if (arr[i] > search) {
                            UpperIndex = i - 1;
                            break;
                        }
                    } else {
                        UpperIndex = arr.length - 1;
                    }
                }
            }
            return UpperIndex;
        } else return -1;
    }

    //数这个数组中有几个不同的数
    public static int countNoRepeated(long[] arr) {
        int counter = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                counter++;
            }
        }
        return counter;
    }

    public static int countZero(long[] arr) {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                counter++;
            } else if (arr[i] > 0) {
                break;
            } else continue;
        }
        return counter;
    }
}
