package week12;

import java.util.*;

public class Week12_1 {
    public static void main(String[] args) {
        int[] node = {1, 2, 3, 4, 5, 6};
        int[][] edge = {
                {1, 2, 3},
                {1, 3, 4},
                {2, 3, 1},
                {2, 4, 9},
                {2, 5, 4},
                {3, 4, 5},
                {3, 5, 13},
                {4, 6, 8},
                {5, 4, 12},
                {5, 6, 10}
        };
        Map<Integer, Integer> res = new Dijkstra().run(node, edge, 1);
        System.out.println("到各点的距离为：");
        for (int j : node) {
            System.out.println(j + " : " + res.get(j));
        }
    }
}

class Dijkstra {
    private int[][] edge;
    private int edgeNum;
    private Map<Integer, Boolean> status;
    Queue<DirectedEdge> queue;

    public Map<Integer, Integer> run(int[] node, int[][] edge, int src) {
        this.edge = edge;
        edgeNum = edge.length;
        HashMap<Integer, Integer> distance = new HashMap<>();
        for (int j : node) {
            if (j != src) {
                distance.put(j, Integer.MAX_VALUE);
            } else {
                distance.put(j, 0);
            }
        }
        status = new HashMap<>();
        for (int j : node) {
            status.put(j, false);
        }
        queue = new PriorityQueue<>(Comparator.comparingInt(DirectedEdge::getLen));

        addNode(src);
        while (!queue.isEmpty()) {
            DirectedEdge e = queue.poll();
            distance.put(e.getAim(), Math.min(distance.get(e.getSrc()) + e.getLen(), distance.get(e.getAim())));
            addNode(e.getAim());
        }
        return distance;
    }

    private void addNode(int node) {
        if (!status.get(node)) {
            status.put(node, true);
            for (int i = 0; i < edgeNum; i++) {
                if (edge[i][0] == node) {
                    queue.offer(new DirectedEdge(edge[i][0], edge[i][1], edge[i][2]));
                }
            }
        }
    }

}

class DirectedEdge {
    private final int src;
    private final int aim;
    private final int len;

    public DirectedEdge(int src, int aim, int len) {
        this.src = src;
        this.aim = aim;
        this.len = len;
    }

    public int getSrc() {
        return src;
    }

    public int getAim() {
        return aim;
    }

    public int getLen() {
        return len;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "src=" + src +
                ", aim=" + aim +
                ", len=" + len +
                '}';
    }
}