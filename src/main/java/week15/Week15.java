package week15;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Week15 {
    public static void main(String[] args) {
        int c1 = 120;
        int c2 = 80;
        int[] weight = {60, 40, 10, 30, 50};
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

    private int[] weight;
    private int len;
    private int max;
    private boolean[] status;

    public boolean run(int c1, int c2, int[] weight) {
        this.weight = weight;
        len = weight.length;
        max = 0;
        status = new boolean[len];
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(null, 0, 0));
        queue.offer(null);
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += weight[i];
        }
        int level = 0;
        while (level != len) {
            Node n = queue.poll();
            if (n == null) {
                level++;
                queue.offer(null);
            } else {
                if (n.value + weight[level] <= c1) {
                    queue.offer(new Node(n, n.value + weight[level], 1));
                }
                queue.offer(new Node(n, n.value, 0));
            }
        }

        while (true) {
            Node n = queue.poll();
            if (n == null) {
                break;
            }
            if (n.value > max) {
                max = n.value;
                for (int i = len - 1; i >= 0; i--) {
                    status[i] = n.type == 1;
                    n = n.father;
                }
            }
        }

        return sum - max <= c2;
    }

    public void display() {
        System.out.println("装配方案为：");
        System.out.print("C1: ");
        for (int i = 0; i < len; i++) {
            if (status[i]) {
                System.out.print(weight[i] + " ");
            }
        }
        System.out.println();
        System.out.print("C2: ");
        for (int i = 0; i < len; i++) {
            if (!status[i]) {
                System.out.print(weight[i] + " ");
            }
        }
    }

    private void debug() {
        System.out.println(max);
        System.out.println(Arrays.toString(status));
    }

}

class Node {

    public Node father;
    public int type;
    public int value;

    public Node(Node father, int value, int type) {
        this.father = father;
        this.value = value;
        this.type = type;
    }
}
