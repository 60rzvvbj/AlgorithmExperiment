package week8;

import util.treeUtil.TreeNode;
import util.treeUtil.TreeUtil;

import java.util.Arrays;

public class Week8_1 {
    public static void main(String[] args) {

        // 输入数据
        int[] b = {3, 3, 1, 1};
        int[] a = {2, 3, 1, 1, 1};

        // 数据预处理
        double[] pointP = new double[b.length];
        double[] intervalP = new double[a.length];
        int[] num = new int[b.length];

        double sum = 0;
        for (int i : b) {
            sum += i;
        }
        for (int i : a) {
            sum += i;
        }
        for (int i = 0; i < b.length; i++) {
            num[i] = i + 1;
            pointP[i] = b[i] / sum;
        }
        for (int i = 0; i < a.length; i++) {
            intervalP[i] = a[i] / sum;
        }

        // 计算
        OptimalBST optimalBST = new OptimalBST();
        double expect = optimalBST.createTree(pointP, intervalP, num);
        System.out.println("最小期望：" + expect);
        System.out.println("二叉搜索树如下：");
        optimalBST.displayTree();
    }
}

class OptimalBST {

    private double[] pointP;
    private double[] intervalP;
    private int[] num;
    private double[][] expect;
    private double[][] probability;
    private int[][] root;
    private int len;

    public double createTree(double[] pointP, double[] intervalP, int[] num) {

        // 初始化
        this.pointP = pointP;
        this.intervalP = intervalP;
        this.num = num;
        len = pointP.length;
        inputCheck();

        // 开始建树
        this.expect = new double[len + 1][len + 1];
        this.probability = new double[len + 1][len + 1];
        this.root = new int[len][len];

        for (int i = 0; i < len + 1; i++) {
            expect[i][i] = intervalP[i];
            probability[i][i] = intervalP[i];
        }

        for (int l = 1; l < len + 1; l++) {
            for (int i = 0; i < len - l + 1; i++) {
                int j = i + l;
                expect[i][j] = Double.MAX_VALUE;
                probability[i][j] = probability[i][j - 1] + pointP[j - 1] + intervalP[j];
                for (int k = i + 1; k <= j; k++) {
                    double t = expect[i][k - 1] + expect[k][j] + probability[i][j];
                    if (t < expect[i][j]) {
                        expect[i][j] = t;
                        root[i][j - 1] = k - 1;
                    }
                }
            }
        }

//        debug();
        return expect[0][len];
    }

    // 输入检测
    private void inputCheck() {
        if (intervalP.length != len + 1 || num.length != len) {
            throw new RuntimeException("input error");
        }
        double sumP = 0;
        for (double p : pointP) {
            sumP += p;
        }
        for (double p : intervalP) {
            sumP += p;
        }
        if (Math.abs(sumP - 1) > 0.00001) {
            throw new RuntimeException("input error");
        }
    }

    public void displayTree() {
        BTreeNode root = createTree(0, len - 1);
        TreeUtil.printTree(root);
    }

    private BTreeNode createTree(int leftIndex, int rightIndex) {
        if (leftIndex > rightIndex) {
            return null;
        }

        BTreeNode node = new BTreeNode();

        node.setData("" + num[root[leftIndex][rightIndex]]);

        node.setLeft(createTree(leftIndex, root[leftIndex][rightIndex] - 1));
        node.setRight(createTree(root[leftIndex][rightIndex] + 1, rightIndex));

        return node;
    }

    // 调试用
    private void debug() {
        for (int i = 0; i < expect.length; i++) {
            for (double d : expect[i]) {
                if (d > 100) {
                    System.out.print("max \t");
                } else {
                    System.out.printf("%.2f\t", d);
                }
            }
            System.out.print("\t\t");
            for (double d : probability[i]) {
                if (d > 100) {
                    System.out.print("max \t");
                } else {
                    System.out.printf("%.2f\t", d);
                }
            }
            System.out.println();
        }
        for (int[] ra : root) {
            System.out.println(Arrays.toString(ra));
        }
    }

}

class BTreeNode implements TreeNode {

    private TreeNode left;
    private TreeNode right;
    private Object data;

    @Override
    public void setLeft(TreeNode l) {
        this.left = l;
    }

    @Override
    public void setRight(TreeNode r) {
        this.right = r;
    }

    @Override
    public TreeNode getLeft() {
        return this.left;
    }

    @Override
    public TreeNode getRight() {
        return this.right;
    }

    @Override
    public void setData(Object object) {
        this.data = object;
    }

    @Override
    public String getData() {
        return this.data.toString();
    }
}