package week9;

import util.consoleUtil.ColorChar;
import util.consoleUtil.ConsoleCanvas;

public class Week9_1 {
    public static void main(String[] args) {
//        int[] pi = {8, 7, 4, 2, 5, 1, 9, 3, 10, 6};
        int[] pi = {6, 8, 12, 2, 1, 4, 5, 3, 11, 7, 10, 9, 13};
        CircuitWiring circuitWiring = new CircuitWiring();
        int res = circuitWiring.run(pi);
        System.out.println("最多可以布" + res + "条线");
        circuitWiring.display();
    }
}

class CircuitWiring {
    private int[] pi;
    private int[][] dp;
    private int len;

    public int run(int[] pi) {
        this.pi = pi;
        len = pi.length;
        dp = new int[len + 1][len + 1];
        for (int i = 0; i < len + 1; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < getPIx(1); i++) {
            dp[1][i] = 0;
        }
        for (int i = getPIx(1); i < len + 1; i++) {
            dp[1][i] = 1;
        }
        for (int i = 1; i < len + 1; i++) {
            for (int j = 1; j < getPIx(i); j++) {
                dp[i][j] = dp[i - 1][j];
            }
            for (int j = getPIx(i); j < len + 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][pi[i - 1]] + 1);
            }
        }
//        debug();
        return dp[len][len];
    }

    public void display() {
        int[] light = new int[dp[len][len]];
        int p = dp[len][len];
        int j = len;
        for (int i = len; i >= 1; i--) {
            if (dp[i][j] != dp[i - 1][j]) {
                light[--p] = i;
                j = getPIx(i);
            }
        }

        System.out.println("\n布线方式为：");
        for (int k : light) {
            int index1 = k - 1;
            int index2 = getPIx(k) - 1;
            System.out.println((index1 + 1) + "->" + (index2 + 1));
        }

        System.out.println("\n如图所示：\n");
        int canvasHeight = 30;
        int canvasItemWidth = 14;
        ConsoleCanvas cc = new ConsoleCanvas((len - 1) * canvasItemWidth + (len + 1 + "").length(), canvasHeight);
        cc.setColor(ColorChar.RED);
        for (int i = 0; i < len; i++) {
            cc.writeText(1, i * canvasItemWidth, "" + (i + 1));
            cc.writeText(canvasHeight - 2, i * canvasItemWidth, "" + (i + 1));
        }
        cc.setColor(ColorChar.DEFAULT);
        cc.setDottedInterval(2);
        for (int i = 0; i < len; i++) {
            int index1 = i;
            int index2 = getPIx(i + 1) - 1;
            cc.drawLine(2, index1 * canvasItemWidth, canvasHeight - 3, index2 * canvasItemWidth, "dotted");
        }
        cc.setColor(ColorChar.YELLOW);
        for (int k : light) {
            int index1 = k - 1;
            int index2 = getPIx(k) - 1;
            cc.drawLine(2, index1 * canvasItemWidth, canvasHeight - 3, index2 * canvasItemWidth);
        }
        cc.displayC();
    }

    private int getPIx(int x) {
        return pi[x - 1];
    }

    private void debug() {
        System.out.println("==========================华丽的分割线==========================");
        System.out.print("\t");
        for (int i = 0; i < dp.length; i++) {
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
}