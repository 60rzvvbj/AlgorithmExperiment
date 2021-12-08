package week13;

import java.util.Arrays;

public class Week13_1 {
    public static void main(String[] args) {
        int c1 = 10;
        int c2 = 12;
        int[] weight = {5, 2, 1, 3};
        LoadProblem lp = new LoadProblem();
        if (lp.run(c1, c2, weight)) {
            System.out.println("可以全装下");
            lp.display();
        } else {
            System.out.println("不能全装下");
        }
    }
}

class LoadProblem {
    private int len;
    private boolean[] flag;
    private int[] weight;
    private int nowWeight;
    private int sumWeight;
    private int c1;
    private int c2;

    public boolean run(int c1, int c2, int[] weight) {
        this.c1 = c1;
        this.c2 = c2;
        this.weight = weight;
        len = weight.length;
        flag = new boolean[len];
        nowWeight = 0;
        Arrays.sort(weight);
        for (int i = 0; i < len / 2; i++) {
            int t = weight[i];
            weight[i] = weight[len - i - 1];
            weight[len - i - 1] = t;
        }
        sumWeight = 0;
        for (int i = 0; i < len; i++) {
            sumWeight += weight[i];
        }
        return dfs(0);
    }

    private boolean dfs(int now) {
        if (now == len) {
            return sumWeight - nowWeight <= c2;
        }
        if (nowWeight + weight[now] > c1 && sumWeight - nowWeight <= c2) {
            return true;
        }
        if (nowWeight + weight[now] <= c1) {
            nowWeight += weight[now];
            flag[now] = true;
            if (dfs(now + 1)) {
                return true;
            }
            nowWeight -= weight[now];
            flag[now] = false;
        }
        return dfs(now + 1);
    }

    public void display() {
        System.out.println("装配方案为：");
        System.out.print("C1: ");
        for (int i = 0; i < len; i++) {
            if (flag[i]) {
                System.out.print(weight[i] + " ");
            }
        }
        System.out.println();
        System.out.print("C2: ");
        for (int i = 0; i < len; i++) {
            if (!flag[i]) {
                System.out.print(weight[i] + " ");
            }
        }
    }
}
