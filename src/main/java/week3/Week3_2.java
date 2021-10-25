package week3;

import util.Timer;
import week1.SortTool;

import java.util.Arrays;

public class Week3_2 {
    public static void main(String[] args) {
        int[] arr = {8, 4, 3, 7, 1, 5, 6, 2};
        SortTool sortTool = new QuickSort();
        sortTool.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

class QuickSort extends SortTool {

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        sortPlus(0, arr.length);
    }

    private void sort(int l, int r) {
        if (l < r) {
            int index = partition(l + 1, l, r);
            swap(index - 1, l);
            sort(l, index - 1);
            sort(index, r);
        }
    }

    private void sortPlus(int l, int r) {
        if (l < r) {
            swap((int) (Math.random() * (r - 1 - l + 1) + l), l);
            int index = partition(l + 1, l, r);
            swap(index - 1, l);
            sortPlus(l, index - 1);
            sortPlus(index, r);
        }
    }

    private int partition(int index, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            if (arr[i] < arr[l]) {
                swap(index, i);
                index++;
            }
        }
        return index;
    }

}
