package week5;

//import org.junit.jupiter.api.Test;
import util.RandomUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Week5_1 {

    // 主方法
    public static void main(String[] args) {
        double[] x = {1, 2, 2.5, 3, 4};
        double[] y = {1, 1, 2.5, 3, 4};
        NearestPointPair nearestPointPair = new NearestPointPair();
        double min = nearestPointPair.search(x, y);
        System.out.println(min);
    }

    // 测试方法
//    @Test
    public void test() {
        NearestPointPair nearestPointPair = new NearestPointPair();

        int num = 10; // 测试用例组数

        for (int i = 0; i < num; i++) {

            // 测试用例范围
            int len = RandomUtil.getIntRandom(1000, 10000);

            // 创建测试用例
            double[] x = new double[len];
            double[] y = new double[len];
            for (int j = 0; j < len; j++) {
                x[j] = RandomUtil.getDoubleRandom(-1000000, 1000000);
                y[j] = RandomUtil.getDoubleRandom(-1000000, 1000000);
            }

            // 得出结果
            double min = nearestPointPair.search(x, y);

            // 验证结果正确性
            try {
                nearestPointPair.test(min);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("测试结束");
    }
}

class NearestPointPair {

    private int len; // 点的个数
    private Point[] pointX; // 按X排序的点数组

    public double search(double[] x, double[] y) {

        // 输入验证
        if (!(x != null && y != null && x.length == y.length)) {
            throw new RuntimeException("输入数据有误");
        } else if (x.length < 2) {
            throw new RuntimeException("输入数据无意义");
        }

        // 初始化
        len = x.length;
        pointX = new Point[len];
        for (int i = 0; i < len; i++) {
            Point point = new Point(x[i], y[i]);
            pointX[i] = point;
        }

        // 按x排序
        Arrays.sort(pointX, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

        // 返回结果
        return search(0, len);
    }

    // 递归函数
    private double search(int l, int r) {

        // 递归终止
        if (r - l <= 3) {
            double res = pointX[l].distance(pointX[l + 1]);
            if (r - l == 3) {
                res = Math.min(res, pointX[l].distance(pointX[l + 2]));
                res = Math.min(res, pointX[l + 1].distance(pointX[l + 2]));
            }
            return res;
        }

        int middle = (r - l) / 2 + l; // 找中间坐标

        // 记录左右子区间最小距离
        double minDistance = Math.min(search(l, middle), search(middle, r));

        // 找到在2d范围内的点
        Point[] points = findLimitPoints(l, r, pointX[middle].getX() - minDistance, pointX[middle].getX() + minDistance);

        // 如果个数大于二就扫描一遍
        if (points.length >= 2) {

            // 滑动窗口头尾索引
            int start = 0;
            int end = start + 1;

            // 中间2d区域内最小值
            double middleMin = Double.MAX_VALUE;

            // 开始扫描
            while (start < points.length) {

                // end后移到对应位置
                while (end < points.length && points[end].getY() - points[start].getY() > minDistance) {
                    end++;
                }

                // 计算中间所有的距离
                for (int i = start + 1; i < end; i++) {
                    middleMin = Math.min(middleMin, points[start].distance(points[i]));
                }

                // start后移
                start++;
            }

            // 更新最终结果
            minDistance = Math.min(minDistance, middleMin);
        }

        return minDistance;
    }

    // 寻找范围中的点，并按照y值大小排序
    private Point[] findLimitPoints(int pl, int pr, double limitL, double limitR) {
        List<Point> pointList = new LinkedList<>();
        for (int i = pl; i < pr; i++) {

            // 如果点的横坐标在范围内则添加到List中
            if (pointX[i].getX() >= limitL && pointX[i].getX() <= limitR) {
                pointList.add(pointX[i]);
            }
        }

        // 将List转化成数组
        Point[] points = pointList.toArray(new Point[0]);

        // 按Y排序
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });

        return points;
    }

    // 测试方法
    public void test(double min) throws Exception {

        double testMin = Double.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                testMin = Math.min(testMin, pointX[i].distance(pointX[j]));
            }
        }

        // 暴力算出结果后与传入结果作比较，结果有问题则抛出异常
        if (Math.abs(min - testMin) > 0.0001) {
            throw new Exception("结果" + min + "与预期的结果" + testMin + "不一致，测试不通过");
        }
    }

}

// 点类
class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // 计算自己和另一个点间的距离
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.getX() - other.getX(), 2) + Math.pow(this.getY() - other.getY(), 2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
