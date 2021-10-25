package week3;

public class Week3_1 {
    public static void main(String[] args) {
        int[] arr = {1, 8, 12, 15, 16, 21, 30, 35, 39};
        BinarySearch binarySearch = new BinarySearch();
        int res;
        int[] resArr;
        int value;

        value = 30;
        res = binarySearch.search(arr, value);
        if (res == -1) {
            System.out.println("无元素" + value);
        } else {
            System.out.println("元素" + value + "下标为" + res);
        }

        System.out.println("======================华丽的分割线======================");

        value = 20;
        res = binarySearch.search(arr, value);
        if (res == -1) {
            System.out.println("无元素" + value);
        } else {
            System.out.println("元素" + value + "下标为" + res);
        }

        System.out.println("======================华丽的分割线======================");

        value = 10;
        resArr = binarySearch.searchPlus(arr, value);
        if (resArr[0] == -1) {
            System.out.println("无元素" + value);
            System.out.println("小于" + value + "的最大元素位置为" + resArr[1]);
            System.out.println("大于" + value + "的最小元素位置为" + resArr[2]);
        } else {
            System.out.println("元素" + value + "下标为" + resArr[1]);
        }
    }
}

class BinarySearch {

    private int[] arr;
    private int index;
    private int value;

    public int search(int[] arr, int value) {
        init(arr, value);
        return search() ? index : -1;
    }

    public int[] searchPlus(int[] arr, int value) {
        init(arr, value);
        int[] res;
        if (search()) {
            res = new int[]{1, index};
        } else {
            res = new int[]{-1, index - 1, index};
        }
        return res;
    }

    private void init(int[] arr, int value) {
        this.arr = arr;
        this.value = value;
    }

    private boolean search() {
        int l = 0;
        int r = arr.length;
        index = -1;
        while (l < r) {
            index = (r - l) / 2 + l;
            if (arr[index] == value) {
                return true;
            } else if (arr[index] > value) {
                r = index;
            } else {
                l = index + 1;
            }
        }
        index = l;
        return false;
    }

}