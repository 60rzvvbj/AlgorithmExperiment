package week9;

import util.consoleUtil.ColorChar;
import util.consoleUtil.ConsoleCanvas;
import util.consoleUtil.ConsoleColor;

public class Week9_1 {
    public static void main(String[] args) {
        int[] pi = {6, 8, 12, 2, 1, 4, 5, 3, 11, 7, 10, 9, 13}; // 数据
        CircuitWiring circuitWiring = new CircuitWiring();
        int res = circuitWiring.run(pi); // 计算
        System.out.println("最多可以布" + res + "条线");
        circuitWiring.display(); // 显示
    }
}

class CircuitWiring {
    private int[] pi; // π数组
    private int[][] dp; // dp数组
    private int len; // 线个数

    public int run(int[] pi) {

        // 验证输入
        inputCheck(pi);

        // 初始化
        this.pi = pi;
        len = pi.length;
        dp = new int[len + 1][len + 1];

        // 初始化第0列
        for (int i = 0; i < len + 1; i++) {
            dp[i][0] = 0;
        }

        // 初始化第1行
        for (int i = 0; i < getPIx(1); i++) {
            dp[1][i] = 0;
        }
        for (int i = getPIx(1); i < len + 1; i++) {
            dp[1][i] = 1;
        }

        // dp
        for (int i = 1; i < len + 1; i++) {
            for (int j = 1; j < getPIx(i); j++) {
                dp[i][j] = dp[i - 1][j];
            }
            for (int j = getPIx(i); j < len + 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][pi[i - 1]] + 1);
            }
        }

        // 调试
        // debug();
        return dp[len][len];
    }

    // 输入验证
    private void inputCheck(int[] pi) {
        // 如果输入的不是π.length - 1的转置，则抛出异常
        // n的转置为由1~n组成的乱序的数组
        // 通过异或一个数两次值不变的性质判断
        int x = pi.length;
        for (int i = 0; i < pi.length; i++) {
            x ^= i;
            x ^= pi[i];
        }
        if (x != 0) {
            throw new RuntimeException("Input error");
        }
    }

    // 显示布线结果
    public void display() {

        // 如果还没调用run就调用显示则抛异常
        if (pi == null) {
            throw new RuntimeException("Please call the run method first");
        }

        // 初始化布线数组
        int[] light = new int[dp[len][len]];
        int p = dp[len][len];
        int j = len;

        // 寻找布线
        for (int i = len; i >= 1; i--) {
            if (dp[i][j] != dp[i - 1][j]) {
                light[--p] = i;
                j = getPIx(i);
            }
        }

        // 输出布线
        System.out.println("\n布线方式为：");
        for (int k : light) {
            int index1 = k - 1;
            int index2 = getPIx(k) - 1;
            System.out.println((index1 + 1) + "->" + (index2 + 1));
        }

        // 使用控制台画布工具显示图像
        System.out.println("\n如图所示：\n");
        int canvasHeight = 30; // 画布高度
        int canvasItemWidth = 14; // 每个接线柱宽度

        // 创建画布
        ConsoleCanvas cc = new ConsoleCanvas((len - 1) * canvasItemWidth + (len + 1 + "").length(), canvasHeight);

        // 绘制接线柱(红色)
        cc.setColor(ConsoleColor.RED);
        for (int i = 0; i < len; i++) {
            cc.writeText(1, i * canvasItemWidth, "" + (i + 1));
            cc.writeText(canvasHeight - 2, i * canvasItemWidth, "" + (i + 1));
        }

        // 绘制非布线线条(虚线, 灰色)
        cc.setColor(ConsoleColor.GREY);
        cc.setDottedInterval(2);
        for (int i = 0; i < len; i++) {
            int index1 = i;
            int index2 = getPIx(i + 1) - 1;
            cc.drawLine(2, index1 * canvasItemWidth, canvasHeight - 3, index2 * canvasItemWidth, "dotted");
        }

        // 绘制布线线条(实线，黄色)
        cc.setColor(ConsoleColor.YELLOW);
        for (int k : light) {
            int index1 = k - 1;
            int index2 = getPIx(k) - 1;
            cc.drawLine(2, index1 * canvasItemWidth, canvasHeight - 3, index2 * canvasItemWidth);
        }

        // 压缩显示
        cc.displayC();
    }

    // 获取π(x)
    private int getPIx(int x) {
        return pi[x - 1];
    }

    // 调试用
    private void debug() {
        System.out.println("==========================华丽的分割线==========================");

        // 打印列标题
        System.out.print("\t");
        for (int i = 0; i < dp.length; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        // 打印dp数组
        for (int i = 0; i < dp.length; i++) {
            System.out.print(i + "\t"); // 打印行标题
            for (int j = 0; j < dp[i].length; j++) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }
    }
}