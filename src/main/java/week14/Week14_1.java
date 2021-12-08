package week14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week14_1 {
    public static void main(String[] args) {
        int[] nodes = {1, 2, 3, 4, 5, 6};
        int[][] edges = {
                {1, 2},
                {1, 4},
                {1, 5},
                {1, 6},
                {2, 3},
                {2, 4},
                {2, 5},
                {3, 4},
                {4, 5},
                {5, 6}
        };
        MaximumClique maximumClique = new MaximumClique();
        int res = maximumClique.run(nodes, edges);
        System.out.println("最大子集中元素个数为：" + res);
        maximumClique.display();
    }
}

class MaximumClique {

    private boolean[][] matrix;
    private boolean[] status;
    private int[] nodes;
    private int[] set;
    private int max;
    private int[] maxSet;
    private int len;

    public int run(int[] nodes, int[][] edges) {

        // 初始化
        this.nodes = nodes;
        len = nodes.length;
        set = new int[len];
        matrix = new boolean[len][len];
        max = 0;
        status = new boolean[len];
        maxSet = new int[len];

        // 构造邻接矩阵
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(nodes[i], i);
            matrix[i][i] = true;
        }
        for (int[] edge : edges) {
            matrix[map.get(edge[0])][map.get(edge[1])] = true;
            matrix[map.get(edge[1])][map.get(edge[0])] = true;
        }

        // debug();
        dfs(0);
        return max;
    }

    private void dfs(int now) {
        if (now > max) {
            max = now;
            for (int i = 0; i < max; i++) {
                maxSet[i] = set[i];
            }
        }
        if (now == len) {
            return;
        }
        for (int i = 0; i < len; i++) {
            if (status[i]) {
                continue;
            }
            boolean flag = true;
            for (int j = 0; j < now; j++) {
                if (!matrix[i][set[j]]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                set[now] = i;
                status[i] = true;
                dfs(now + 1);
                status[i] = false;
            }
        }
    }

    public void display() {
        System.out.println("\n最大子集为：");
        for (int i = 0; i < max; i++) {
            System.out.print(nodes[maxSet[i]] + " ");
        }
        System.out.println();
    }

    private void debug() {
        for (int i = 0; i < len; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}
