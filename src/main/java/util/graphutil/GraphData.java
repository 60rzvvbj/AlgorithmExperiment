package util.graphutil;

import java.util.List;

public class GraphData {
    private int type;
    private List<String> nodes;
    private List<List<String>> edges;
    private List<List<String>> routes;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public List<List<String>> getEdges() {
        return edges;
    }

    public void setEdges(List<List<String>> edges) {
        this.edges = edges;
    }

    public List<List<String>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<List<String>> routes) {
        this.routes = routes;
    }
}