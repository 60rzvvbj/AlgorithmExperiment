package week2;

import week1.SortTool;

import java.util.Arrays;

public class Week2_2 {
    public static void main(String[] args) {
        int[] arr = {23, 5, 9, 16, 30, 25, 17, 18};
        SortTool sortTool = new MergeSort();
        sortTool.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

// 归并排序
class MergeSort extends SortTool {

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        sort(0, arr.length);
    }

    // 分
    private void sort(int l, int r) {
        int middle = (r - l) / 2 + l; // 取中点
        if (l == middle) { // 如果只剩一个元素则返回
            return;
        }
        sort(l, middle);
        sort(middle, r);
        merge(l, middle, r);
    }

    // 和
    private void merge(int l, int middle, int r) {
        int[] temp = new int[r - l]; // 建立临时数组
        int pt = 0; // 临时数组当前索引
        int pl = l; // 左侧索引
        int pr = middle; // 右侧索引
        while (pt != temp.length) { // 如果临时数组满了，说明排完了，结束循环
            if (pl == middle) { // 如果左侧到头了，则加右侧
                temp[pt++] = arr[pr++];
            } else if (pr == r) { // 如果右侧到头了，则加左侧
                temp[pt++] = arr[pl++];
            } else { // 如果两侧都未到头则比较大小
                if (arr[pl] < arr[pr]) { // 左小加左
                    temp[pt++] = arr[pl++];
                } else { // 右小加右
                    temp[pt++] = arr[pr++];
                }
            }
        }
        System.arraycopy(temp, 0, arr, l, temp.length); // 合并好的数组再拷贝到原数组中
    }
}
