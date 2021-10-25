package week4;

import java.util.Arrays;

public class Week4_1 {
    public static void main(String[] args) {
        int[] arr = {2, 9, 11, 3, 14, 7, 10, 8, 15, 4, 13, 1, 6, 5, 12};
        int k = 8;

        SearchKthSmallest searchKthSmallest = new SearchKthSmallest();
        int res = searchKthSmallest.search(arr, k);
        System.out.println(res);
    }
}

// 寻找第k小元素
class SearchKthSmallest {

    private int[] arr; // 基数组

    private static final int NUMBER_OF_EACH_GROUP = 5; // 每组元素个数
    private static final int LIMIT = 5; // 界限 为了测试程序正确性改小一点

    // 寻找
    public int search(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new RuntimeException("第" + k + "小元素不存在");
        }

        this.arr = arr; // 初始化基数组
        return arr[search(0, arr.length, k - 1)]; // 寻找
    }

    // 递归寻找方法
    private int search(int l, int r, int k) {

        // 如果小于界限则采用排序的方法
        if (r - l < LIMIT) {
            Arrays.sort(arr, l, r);
            return l + k;
        }

        // 分成若干组，并将各组中位数前置
        for (int i = l; i < r; i += NUMBER_OF_EACH_GROUP) {
            int end = Math.min(i + NUMBER_OF_EACH_GROUP, r);
            int medianIndex = searchMedian(i, end);
            swap(medianIndex, (i - l) / NUMBER_OF_EACH_GROUP + l);
        }

        // 找到中位数的中位数基准x的下标
        int xIndex = searchMedian(l, l + (r - l + 1) / 5 - 1);

        // 找到x在待寻找数组中的排位
        int xPosition = partition(l, r, xIndex);

        if (k == xPosition) { // 如果元素x正好是第k小则直接返回
            return l + xPosition;
        } else if (k < xPosition) { // 如果k比x小，则去左子数组中找
            return search(l, l + xPosition, k);
        } else { // 如果k比x大，则取右子数组中找
            return search(xPosition + 1 + l, r, k - xPosition - 1);
        }
    }

    // 寻找中位数
    private int searchMedian(int l, int r) {

        // 如果小于每组元素个数则采用一半的选择排序来找中位数
        if (r - l <= NUMBER_OF_EACH_GROUP) {
            for (int j = l; j < (r - l) / 2 + l + 1; j++) {
                int minIndex = j;
                for (int p = j + 1; p < r; p++) {
                    if (arr[p] < arr[minIndex]) {
                        minIndex = p;
                    }
                }
                swap(minIndex, j);
            }

            // 返回中位数下标
            return (r - l) / 2 + l;
        } else {

            // 通过search方法寻找第中间小的数
            return search(l, r, (r - l) / 2);
        }
    }

    // 分割数组
    private int partition(int l, int r, int xIndex) {

        // 将基准放到首位
        swap(xIndex, l);

        // index 指向第一个比基准大的数的位置
        int index = l + 1;

        // 将数组分成比基准小和比基准大的两部分
        for (int i = l + 1; i < r; i++) {
            if (arr[i] < arr[l]) {
                swap(index, i);
                index++;
            }
        }

        // 将基准放到它的位置
        swap(index - 1, l);

        // 返回基准的排位
        return index - 1 - l;
    }

    // 交换元素
    private void swap(int index1, int index2) {
        int t = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = t;
    }

}
