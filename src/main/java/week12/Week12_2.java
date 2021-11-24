package week12;

import util.graphutil.*;

import java.util.*;

public class Week12_2 {
    public static void main(String[] args) {
        String[] nodes = {"V0", "V1", "V2", "V3", "V4", "V5"};
        UndirectedEdge[] edges = new UndirectedEdge[]{
                new UndirectedEdge("V0", "V1", 34),
                new UndirectedEdge("V0", "V4", 12),
                new UndirectedEdge("V1", "V2", 46),
                new UndirectedEdge("V1", "V5", 19),
                new UndirectedEdge("V2", "V3", 17),
                new UndirectedEdge("V2", "V5", 25),
                new UndirectedEdge("V3", "V4", 38),
                new UndirectedEdge("V3", "V5", 25),
                new UndirectedEdge("V4", "V5", 26)
        };
        Prim prim = new Prim();
        UndirectedEdge[] res = prim.run(nodes, edges);
        System.out.println("剩余边为：");
        for (UndirectedEdge edge : res) {
            System.out.println(edge.getNode()[0] + " --- " + edge.getLen() + " --- " + edge.getNode()[1]);
        }
        prim.export("G:\\ideaProject\\Algotithm\\src\\main\\java\\week12\\w12_2.ycx");
    }
}

class Prim {

    private UndirectedEdge[] res;
    private String[] nodes;
    private UndirectedEdge[] edges;
    private Map<String, Boolean> status;
    private Map<UndirectedEdge, Boolean> statusEdge;
    private Queue<UndirectedEdge> queue;

    public UndirectedEdge[] run(String[] nodes, UndirectedEdge[] edges) {
        this.nodes = nodes;
        this.edges = edges;
        res = new UndirectedEdge[nodes.length - 1];
        int point = 0;
        status = new HashMap<>();
        for (String node : nodes) {
            status.put(node, false);
        }
        statusEdge = new HashMap<>();
        for (UndirectedEdge edge : edges) {
            statusEdge.put(edge, false);
        }
        queue = new PriorityQueue<>(Comparator.comparingInt(UndirectedEdge::getLen));
        addNode(nodes[0]);
        while (!queue.isEmpty()) {
            UndirectedEdge edge = queue.poll();
            res[point++] = edge;
            if (point == nodes.length - 1) {
                break;
            }
            String[] edgeNode = edge.getNode();
            addNode(edgeNode[0]);
            addNode(edgeNode[1]);
        }
        return res;
    }

    public void export(String path) {
        GraphUtil.exportGraph(new Graph() {
            @Override
            public int getType() {
                return 0;
            }

            @Override
            public List<GraphNode> getNodes() {
                List<GraphNode> r = new LinkedList<>();
                for (String node : nodes) {
                    r.add(() -> node);
                }
                return r;
            }

            @Override
            public List<GraphEdge> getEdges() {
                List<GraphEdge> r = new LinkedList<>();
                for (UndirectedEdge edge : res) {
                    r.add(new GraphEdge() {
                        @Override
                        public int getRank() {
                            return edge.getLen();
                        }

                        @Override
                        public GraphNode getFirst() {
                            return () -> edge.getNode()[0];
                        }

                        @Override
                        public GraphNode getSecond() {
                            return () -> edge.getNode()[1];
                        }
                    });
                }
                return r;
            }

            @Override
            public List<GraphRoute> getRoutes() {
                return new LinkedList<>();
            }
        }, path);
    }

    private void addNode(String node) {
        if (!status.get(node)) {
            status.put(node, true);
            for (UndirectedEdge edge : edges) {
                if (edge.include(node) && !statusEdge.get(edge)) {
                    statusEdge.put(edge, true);
                    queue.offer(edge);
                }
            }
        }
    }
}

class UndirectedEdge {
    private final String node1;
    private final String node2;
    private final int len;

    public UndirectedEdge(String node1, String node2, int len) {
        this.node1 = node1;
        this.node2 = node2;
        this.len = len;
    }

    public boolean include(String node) {
        return node1.equals(node) || node2.equals(node);
    }

    public String[] getNode() {
        return new String[]{node1, node2};
    }

    public int getLen() {
        return len;
    }

    @Override
    public String toString() {
        return "UndirectedEdge{" +
                "node1='" + node1 + '\'' +
                ", node2='" + node2 + '\'' +
                ", len=" + len +
                '}';
    }
}