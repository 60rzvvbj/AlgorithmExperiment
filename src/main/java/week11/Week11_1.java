package week11;

import util.consoleUtil.ConsoleCanvas;
import util.consoleUtil.ConsoleColor;

import java.util.Arrays;
import java.util.Comparator;

public class Week11_1 {
    public static void main(String[] args) {

        // 数据
        int[] start = {2, 1, 6, 7, 11, 6, 4, 14, 10, 18, 16, 7};
        int[] end = {4, 5, 9, 10, 14, 15, 16, 17, 19, 22, 23, 25};

        // 运行
        ActivityArrangement aa = new ActivityArrangement();
        int res = aa.run(start, end);
        System.out.println("共可安排" + res + "个活动");
        aa.display();
    }
}

// 活动安排
class ActivityArrangement {
    private int len;
    private Activity[] activities;
    private boolean[] res;

    // 运行
    public int run(int[] start, int[] end) {

        // 检测输入
        inputCheck(start, end);

        // 初始化
        len = start.length;
        activities = new Activity[len];
        res = new boolean[len];
        for (int i = 0; i < len; i++) {
            activities[i] = new Activity(start[i], end[i]);
        }

        // 按结束时间排序
        Arrays.sort(activities, new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                if (o1.getEnd() == o2.getEnd()) {
                    return o1.getEnd() - o2.getStart();
                } else {
                    return o1.getEnd() - o2.getEnd();
                }
            }
        });

        // 计算
        int now = 0;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            if (activities[i].getStart() >= now) {
                res[i] = true;
                now = activities[i].getEnd();
                sum++;
            }
        }

        return sum;
    }

    // 显示结果
    public void display() {

        // 相关数据
        int numLen = ((len + 1) + "").length();
        int maxTime = activities[len - 1].getEnd();
        int colNumLen = (maxTime + "").length();
        int widthLeft = 3 * (numLen + 1) + 1;
        int width = widthLeft + maxTime * 2;
        int height = len + 3 + colNumLen;

        // 表
        System.out.println("\n安排的活动为：");
        for (int i = 0; i < len; i++) {
            if (res[i]) {
                System.out.println(getTabString(new String[]{i + 1 + ""}, numLen) + " : " + getTabString(new String[]{activities[i].getStart() + ""}, colNumLen) + " -> " + getTabString(new String[]{activities[i].getEnd() + ""}, colNumLen));
            }
        }

        // 图
        System.out.println("\n如图所示：\n");
        ConsoleCanvas cc = new ConsoleCanvas(width, height);
        cc.setColor(ConsoleColor.WHITE);

        // 网格线
        String line = getCircleString("▀", width);
        cc.writeText(0, 0, line);
        cc.writeText(colNumLen + 1, 0, line);
        cc.writeText(height - 1, 0, line);

        // 表头
        String title = getTabString(new String[]{"N", "S", "E"}, numLen + 1);
        cc.writeText(1, 0, title);
        for (int i = 0; i < maxTime; i++) {
            cc.writeText(1, widthLeft + i * 2, i + 1 + "", 1);
        }

        // 内容
        for (int i = 0; i < len; i++) {
            cc.setColor(res[i] ? ConsoleColor.YELLOW : ConsoleColor.GREY);
            cc.writeText(i + colNumLen + 2, 0, getTabString(new String[]{"" + (i + 1), "" + activities[i].getStart(), "" + activities[i].getEnd()}, numLen + 1));
            cc.writeText(i + colNumLen + 2, numLen * 3 + 2 + activities[i].getStart() * 2, getCircleString("█", (activities[i].getEnd() - activities[i].getStart()) * 2));
            if (res[i]) {
                for (int j = activities[i].getStart(); j < activities[i].getEnd(); j++) {
                    cc.writeText(1, widthLeft + (j - 1) * 2, j + "", 1);
                }
            }
        }

        // 显示
        cc.display();
    }

    private String getTabString(String[] str, int interval) {
        String res = "";
        for (int i = 0; i < str.length; i++) {
            res += str[i];
            for (int j = str[i].length(); j < interval; j++) {
                res += " ";
            }
        }
        return res;
    }

    private String getCircleString(String str, int count) {
        String res = "";
        for (int i = 0; i < count; i++) {
            res += str;
        }
        return res;
    }

    // 调试
    private void debug() {
        for (int i = 0; i < len; i++) {
            if (res[i]) {
                System.out.println(i + ":" + activities[i].getStart() + "->" + activities[i].getEnd());
            }
        }
    }

    // 输入检测
    private void inputCheck(int[] start, int[] end) {
        if (start.length == end.length) {
            boolean flag = true;
            for (int i = 0; i < start.length; i++) {
                if (start[i] >= end[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return;
            }
        }
        throw new RuntimeException("input error");
    }
}

// 活动
class Activity {
    private final int start; // 开始时间
    private final int end; // 结束时间

    public Activity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}