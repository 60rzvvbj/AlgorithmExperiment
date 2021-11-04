package week10;

import util.consoleUtil.ConsoleCanvas;
import util.consoleUtil.ConsoleColor;

public class Week10_1 {
    public static void main(String[] args) {
        int[] w = {5, 4, 8, 6, 9};
        int[] v = {20, 6, 8, 15, 18};
        int c = 18;
        KnapsackProblem kp = new KnapsackProblem();
        int res = kp.run(w, v, c);
        System.out.println("最大价值为：" + res);
        kp.display();
    }
}

// 背包问题
class KnapsackProblem {
    private int len;
    private int[] weight;
    private int[] value;
    private int capacity;
    private int[][] dp;

    // 运行
    public int run(int[] weight, int[] value, int capacity) {

        // 检测输入
        inputCheck(weight, value, capacity);

        // 初始化
        this.len = weight.length;
        this.weight = weight;
        this.value = value;
        this.capacity = capacity;
        this.dp = new int[len + 1][capacity + 1];

        // 计算
        for (int i = 0; i <= capacity; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j <= capacity; j++) {
                dp[i][j] = j - weight[i - 1] >= 0 ? Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]) : dp[i - 1][j];
            }
        }

        // 调试
        // debug();
        return dp[len][capacity];
    }

    // 显示结果
    public void display() {

        // 如果没调用过run则抛出异常
        if (weight == null || value == null) {
            throw new RuntimeException("Please call the run method first");
        }

        // 找到选择了哪些物品
        int[] select = new int[len];
        int selectIndex = 0;
        int h = len;
        int w = capacity;
        while (h > 0) {
            if (dp[h][w] != dp[h - 1][w]) {
                select[selectIndex++] = h - 1;
                w -= weight[h - 1];
            }
            h--;
        }

        // 显示表格
        int sumW = 0;
        int sumV = 0;
        System.out.println("\n选择的物品为：\n");
        System.out.println("序号\t重量\t价值");
        for (int i = selectIndex - 1; i >= 0; i--) {
            System.out.println(select[i] + 1 + "\t" + weight[i] + "\t" + value[i]);
            sumW += weight[i];
            sumV += value[i];
        }
        System.out.println("总\t" + sumW + "\t" + sumV);

        // 使用控制台画布工具显示图像
        System.out.println("\n如图所示：\n");

        ConsoleCanvas cc = new ConsoleCanvas(capacity + 3, capacity + 2);
        cc.setColor(ConsoleColor.YELLOW);
        cc.drawRectangle(0, 0, capacity + 1, capacity - 1);
        cc.setColor(ConsoleColor.DEFAULT);
        cc.clear(0, 1, capacity, capacity - 2);
        cc.setColor(ConsoleColor.WHITE);
        for (int i = 1; i <= capacity; i++) {
            cc.writeText(capacity - i + 1, capacity, "-");
        }
        cc.setColor(ConsoleColor.RED);
        for (int i = 1; i <= capacity; i++) {
            cc.writeText(capacity - i + 1, capacity + 1, i + "");
        }

        int flag = 0;
        int nowHeight = capacity;
        for (int i = selectIndex - 1; i >= 0; i--) {
            ConsoleColor cColor = null;
            if (flag == 0) {
                cColor = ConsoleColor.BLUE;
            } else if (flag == 1) {
                cColor = ConsoleColor.PURPLE;
            } else if (flag == 2) {
                cColor = ConsoleColor.GREEN;
            }
            cc.setColor(cColor);
            cc.drawRectangle(nowHeight, 1, nowHeight - weight[i] + 1, capacity - 2);
            cc.clear(nowHeight - 1, 2, nowHeight - weight[i] + 2, capacity - 3);
            cc.setColor(ConsoleColor.YELLOW);
            cc.writeText(nowHeight - weight[i] / 2, (capacity - 1) / 2, "" + value[i]);
            nowHeight -= weight[i];
            flag = (flag + 1) % 3;
        }

        cc.display();
    }

    // 调试用
    private void debug() {
        System.out.print("\t");
        for (int i = 0; i <= capacity; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        for (int i = 0; i < dp.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // 输入检测
    private void inputCheck(int[] weight, int[] value, int capacity) {
        if (weight.length != value.length) {
            throw new RuntimeException("input error");
        }
    }
}