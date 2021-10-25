package week2;

import java.util.Arrays;

public class Week2_1 {
    public static void main(String[] args) {
        int[][] fp = new FullPermutation().create(3);
        for (int[] ints : fp) {
            System.out.println(Arrays.toString(ints));
        }
    }
}

// 全排列类
class FullPermutation {
    private int[] base; // 基数组，原始数据数组
    private int[] temp; // 临时数组
    private boolean[] flag; // 记录基数组中每个数的状态，是否已经被添加到临时数组中
    private int[][] res; // 结果集数组
    private int index; // 当前结果集索引
    private int len; // 基数组长度

    /**
     * <p><b>方法名：</b>{@code create}</p>
     * <p><b>功能：</b></p><br>创建从1到n的全排列
     *
     * @param n n
     * @return 1到n的全排列
     * @author 60rzvvbj
     * @date 2021/9/13
     */
    public int[][] create(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        return create(arr);
    }

    /**
     * <p><b>方法名：</b>{@code create}</p>
     * <p><b>功能：</b></p><br> 创建由arr数组元素组成的全排列
     *
     * @param arr 数组arr
     * @return 由arr数组元素组成的全排列
     * @author 60rzvvbj
     * @date 2021/9/13
     */
    public int[][] create(int[] arr) {

        // 初始化成员变量
        base = arr;
        len = arr.length;
        res = new int[factorial(len)][len];
        flag = new boolean[len];
        temp = new int[len];
        index = 0;

        dfs(0); // 调用深搜方法
        return res;
    }

    // 生成全排列的深搜方法
    private void dfs(int depth) {
        if (depth == len) {
            int[] t = new int[len];
            for (int i = 0; i < len; i++) {
                t[i] = temp[i];
            }
            res[index++] = t;
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!flag[i]) {
                temp[depth] = base[i];
                flag[i] = true;
                dfs(depth + 1);
                flag[i] = false;
            }
        }
    }

    // 求n的阶乘
    private int factorial(int n) {
        return n > 1 ? n * factorial(n - 1) : 1;
    }
}