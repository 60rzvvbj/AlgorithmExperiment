package week14;

import java.util.HashMap;
import java.util.Map;

public class Week14_2 {
    public static void main(String[] args) {
        int[] nodes = {1, 2, 3, 4, 5, 6};
        int[][] edges = {
                {1, 2},
                {1, 5},
                {1, 6},
                {2, 3},
                {2, 4},
                {2, 5},
                {3, 4},
                {4, 5},
                {5, 6}
        };
        GraphColor graphColor = new GraphColor();
        int res;
        res = graphColor.run(nodes, edges, 3);
        System.out.println("方案数为：" + res);
        res = graphColor.run(nodes, edges, 4);
        System.out.println("方案数为：" + res);
        res = graphColor.run(nodes, edges, 5);
        System.out.println("方案数为：" + res);
    }
}

class GraphColor {

    private boolean[][] matrix;
    private int[] color;
    private int len;
    private int colorNumber;
    private boolean result;
    private int count;

    public int run(int[] nodes, int[][] edges, int m) {
        len = nodes.length;
        color = new int[len];
        colorNumber = m;
        matrix = new boolean[len][len];
        count = 0;

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

        dfs(0);
        return count;
    }

    private void dfs(int now) {
        if (now == len) {
            count++;
        } else {
            for (int i = 0; i < colorNumber; i++) {
                int c = i;
                boolean flag = true;
                for (int j = 0; j < now; j++) {
                    if (matrix[j][now] && color[j] == c) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    color[now] = c;
                    dfs(now + 1);
                }
            }
        }
    }
}
