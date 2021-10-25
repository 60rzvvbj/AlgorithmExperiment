package week1;

import java.util.Arrays;

/**
 * 给定一组“无序”记录序列｛25, 30, 11, 7, 22, 16, 18, 33, 40, 55｝
 * 采用冒泡排序、堆排序、直接选择排序以及直接插入排序方法，将该序列排成非递减序列，完成以下问题：
 * 1）写出冒泡排序、堆排序、直接选择排序和直接插入排序方法的Java实现代码。
 * 2）采用上述4种方法进行排序时，都要执行的两种基本操作是什么？
 * 3）写出冒泡排序第二趟排序后的结果。
 * 4）画出采用堆排序方法第一次抽取堆顶元素后得到的最小堆。
 * 5）采用直接选择法排序时，第5次交换和选择后，未排序记录是什么？
 * 6）采用直接插入法排序把第6个记录16插入有序表时，为寻找插入位置，需要比较多少次？
 * 7）试比较上述4种排序算法的性能（时间复杂度）。
 */
public class Week1_1 {
    public static void main(String[] args) {
        int[] arr = {25, 30, 11, 7, 22, 16, 18, 33, 40, 55};
        SortTool sortTool = new BubbleSort(); // 创建需要使用的排序工具类
        sortTool.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

// 冒泡排序
class BubbleSort extends SortTool {

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    flag = true;
                    swap(i, i + 1);
                }
            }
        }
    }

}

// 堆排序
class HeapSort extends SortTool {

    // 堆索引从0开始，使用最小堆

    // 堆大小
    private int size;

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        this.size = arr.length;

        // 堆调整
        for (int i = 0; i < size; i++) {
            up(i);
        }

        // 排序
        while (size > 0) {
            swap(0, size - 1);
            size--;
            down(0);
        }

        // 逆序
        for (int i = 0; i < arr.length / 2; i++) {
            swap(i, arr.length - i - 1);
        }
    }

    // 获取左子节点索引
    private int left(int index) {
        return index * 2 + 1;
    }

    // 获取右子节点索引
    private int right(int index) {
        return index * 2 + 2;
    }

    // 获取父节点索引
    private int father(int index) {
        return (index - 1) / 2;
    }

    /**
     * 下沉
     *
     * @param index 当前索引
     */
    private void down(int index) {
        while (index < size / 2) {
            if (right(index) < size) { // 有右节点
                if (arr[left(index)] < arr[right(index)]) { // 左 < 右
                    if (arr[index] <= arr[left(index)]) { // 父 < 左 < 右
                        break;
                    } else { // 左 < 父， 左 < 右
                        swap(index, left(index));
                        index = left(index);
                    }
                } else { // 右 < 左
                    if (arr[index] <= arr[right(index)]) { // 父 < 右 < 左
                        break;
                    } else { // 右 < 父， 右 < 左
                        swap(index, right(index));
                        index = right(index);
                    }
                }
            } else { // 无右节点
                if (arr[left(index)] < arr[index]) { // 左 < 父
                    swap(index, left(index));
                    index = left(index);
                } else { // 父 < 左
                    break;
                }
            }
        }
    }

    /**
     * 上浮
     *
     * @param index 当前索引
     */
    private void up(int index) {
        while (index > 0 && arr[index] < arr[father(index)]) { // 如果不是根节点并且值比父节点小
            swap(index, father(index));
            index = father(index);
        }
    }

}

// 选择排序
class SelectSort extends SortTool {

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        for (int i = arr.length - 1; i >= 0; i--) {
            int max = arr[0];
            int maxIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (arr[j] > max) {
                    max = arr[j];
                    maxIndex = j;
                }
            }
            swap(i, maxIndex);
        }
    }

}

// 插入排序
class InsertSort extends SortTool {

    @Override
    public void sort(int[] arr) {
        this.arr = arr;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j > 0; j--) {
                // 这里其实找到当前元素所属位置之后再一起调整位置所需要的赋值步骤会比现在这样每比较一次就交换一次少一倍
                // 但是考虑到父类中已经写了交换方法，为了尽量使代码简洁，复用多一点，就还是采用了每比较一次就交换一次的方法
                // 虽说差距是一倍，但是这本来就是一个O(n^2)的算法，本来就慢，也不差这点了
                if (arr[j] <= arr[j - 1]) {
                    swap(j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

}