package week1;

/**
 * 排序工具类
 * <br>方法：
 * <br> {@link #sort(int[])} 排序方法
 * <br> {@link #sort(int[])} 排序方法
 *
 * @author 60rzvvbj
 * @date 2021/9/2
 */
public abstract class SortTool {

    public int[] arr; // 待排序数组

    /**
     * 排序方法
     *
     * @param arr 待排序数组
     */
    public abstract void sort(int[] arr);

    /**
     * 交换函数
     *
     * @param index1 索引1
     * @param index2 索引2
     */
    public void swap(int index1, int index2) {
        int t = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = t;
    }
}
