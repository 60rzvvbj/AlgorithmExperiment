package week4;

import util.RandomUtil;

import java.util.Arrays;

public class SelectK {
    static int a[] = {2, 9, 11, 3, 14, 7, 10, 8, 15, 4, 13, 1, 6, 5, 12};

    private static int Select(int p, int r, int k) {
        if (r - p < 75) {
            bubbleSort(p, r);
            return a[p + k - 1];
        }
        for (int i = 0; i <= (r - p - 4) / 5; i++) {
            int s = p + i * 5, t = s + 4;
            bubbleSort(s, t);
            exchange(a, p + i, s + 2);
        }
        int x = Select(p, p + (r - p - 4) / 5, (r - p + 6) / 10);
        int i = partition(p, r, x), j = i - p + 1;
        if (k <= j) return Select(p, i, k);
        return Select(i + 1, r, k - j);
    }

    private static int partition(int p, int r, int x) {
        int tmp = x;
        int i = p;
        int j = r - 1;
        while (i != j) {
            while (a[j] >= tmp && i < j)
                j--;
            while (a[i] <= tmp && i < j)
                i++;
            if (i < j)//交换两个数在数组中的位置
                exchange(a, i, j);
        }
        //最终将基准数归位
        a[p + 1] = a[i];
        a[i] = tmp;
        return i;
    }

    public static void bubbleSort(int p, int r) {
        for (int i = p; i < r; i++) {
            for (int j = p; j < r - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    public static void exchange(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
//        MyRandom mr = new MyRandom();
//        a=mr.randomArray(80,0,100);
//        Bubbling bl = new Bubbling();
//        bl.sortArray(a);
//        for(int i=0;i<a.length;i++){
//            System.out.print(a[i]+" ");
//        }
//        System.out.println();
        int test = 10000;
        int count = 0;
        for (int i = 0; i < test; i++) {
            int t = RandomUtil.getIntRandom(80, 80);
            a = RandomUtil.createPermutation(t);
            int[] arr = Arrays.copyOf(a, a.length);
            int res = RandomUtil.getIntRandom(1, t);
            int x = Select(0, a.length, res);

            if (x != res - 1) {
                count++;
//                System.out.println(false);
//                System.out.println(Arrays.toString(arr));
//                System.out.println("res:" + res);
//                System.out.println("x:" + x);
            }
        }
        System.out.println("错误率：" + (count + 0.0) / test);
    }

}
