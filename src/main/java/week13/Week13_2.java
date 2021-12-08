package week13;

import util.consoleUtil.ConsoleCanvas;
import util.consoleUtil.ConsoleColor;

public class Week13_2 {
    public static void main(String[] args) {
        int[][] job = new int[][]{
                {1, 3, 5},
                {2, 6, 1},
                {3, 5, 2},
                {4, 4, 4},
                {5, 3, 2}
        };
        BatchJobScheduling bjs = new BatchJobScheduling();
        int res = bjs.run(job);
        System.out.println("最少时间和为：" + res);
        bjs.display();
    }
}

class BatchJobScheduling {
    private int len;
    private int[][] job;
    private int[][] res;
    private int[][] nowArr;
    private int m1;
    private int m2;
    private int sum;
    private int min;
    private int minM2;

    public int run(int[][] job) {
        this.job = job;
        len = job.length;
        res = new int[len][];
        nowArr = new int[len][];
        m1 = 0;
        m2 = 0;
        sum = 0;
        min = Integer.MAX_VALUE;
        minM2 = min;
        dfs(0);
        return min;
    }

    private void dfs(int now) {
        if (now == len) {
            if (sum < min) {
                for (int i = 0; i < len; i++) {
                    res[i] = nowArr[i];
                }
                min = sum;
                minM2 = m2;
            }
        } else {
            for (int i = now; i < len; i++) {
                m1 += job[i][1];
                int temp = m2;
                m2 = Math.max(m1, m2) + job[i][2];
                sum += m2;

                nowArr[now] = job[i];
                if (sum < min) {
                    swap(i, now);
                    dfs(now + 1);
                    swap(i, now);
                }

                m1 -= job[i][1];
                sum -= m2;
                m2 = temp;
            }
        }
    }

    private void swap(int index1, int index2) {
        int[] j = job[index1];
        job[index1] = job[index2];
        job[index2] = j;
    }

    public void display() {
        System.out.println("\n执行顺序为：");
        for (int i = 0; i < len; i++) {
            System.out.println("J" + res[i][0] + ": " + res[i][1] + " " + res[i][2]);
        }
        System.out.println();
        System.out.println("如图所示：\n");

        int nameWidth = 8;
        int titleHeight = (minM2 + 1 + "").length();
        int titleWidth = (minM2 + 1) * 2;
        int width = nameWidth + titleWidth;
        int height = titleHeight + 2;
        ConsoleCanvas cc = new ConsoleCanvas(width, height);
        cc.writeText(0, 0, "time");
        cc.writeText(titleHeight, 0, "M1:");
        cc.writeText(titleHeight + 1, 0, "M2:");

        cc.drawRectangle(0, 6, height - 1, 6);

        for (int i = 1; i <= minM2 + 1; i++) {
            cc.writeText(0, nameWidth + (i - 1) * 2, i + "", 1);
        }

        int m1 = 0;
        int m2 = 0;
        ConsoleColor[] colorArr = new ConsoleColor[]{ConsoleColor.YELLOW, ConsoleColor.RED, ConsoleColor.BLUE, ConsoleColor.PURPLE, ConsoleColor.SKYBLUE};
        int colorIndex = 0;
        for (int i = 0; i < res.length; i++) {
            cc.setColor(colorArr[colorIndex]);
            colorIndex = (colorIndex + 1) % 5;
            cc.drawRectangle(titleHeight, nameWidth + m1 * 2, titleHeight, nameWidth + (m1 + res[i][1]) * 2 - 1);
            m1 += res[i][1];
            m2 = Math.max(m1, m2);
            cc.drawRectangle(titleHeight + 1, nameWidth + m2 * 2, titleHeight + 1, nameWidth + (m2 + res[i][2]) * 2 - 1);
            m2 += res[i][2];
        }

        cc.display();
    }
}
