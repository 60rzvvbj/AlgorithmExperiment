// 这个是平时做leetcode的时候用的，不用管它

import java.util.*;

public class Main {
    public static void main(String[] args) {
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}


class Solution {
    private boolean[][] flag;
    private int w;
    private int h;
    private int[][] grid;

    public int closedIsland(int[][] grid) {
        h = grid.length;
        w = grid[0].length;
        flag = new boolean[h][w];
        this.grid = grid;

        for (int i = 0; i < h; i++) {
            if (grid[i][0] == 0 && !flag[i][0]) {
                dfs(i, 0);
            }
            if(grid[i][w - 1] == 0 && !flag[i][w - 1]) {
                dfs(i, w - 1);
            }
        }
        for (int i = 1; i < w - 1; i++) {
            if (grid[0][i] == 0 && !flag[0][i]) {
                dfs(0, i);
            }
            if (grid[h - 1][i] == 0 && !flag[h - 1][i]) {
                dfs(h - 1, i);
            }
        }

        int res = 0;
        for (int i = 1; i < h; i++){
            for (int j = 1; j < w; j++) {
                if (grid[i][j] == 0 && !flag[i][j]) {
                    res++;
                    dfs(i, j);
                }
            }
        }

        return res;
    }

    private void dfs(int x, int y) {
        flag[x][y] = true;
        if (x != 0 && grid[x - 1][y] == 0 && !flag[x - 1][y]) {
            dfs(x - 1, y);
        }
        if (x != h - 1 && grid[x + 1][y] == 0 && !flag[x + 1][y]) {
            dfs(x + 1, y);
        }
        if (y != 0 && grid[x][y - 1] == 0 && !flag[x][y - 1]) {
            dfs(x, y - 1);
        }
        if (y != w - 1 && grid[x][y + 1] == 0 && !flag[x][y + 1]) {
            dfs(x, y + 1);
        }
    }
}
