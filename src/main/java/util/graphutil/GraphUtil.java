package util.graphutil;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

/**
 * <p><b>类名：</b>{@code GraphUtil}</p>
 * <p><b>功能：</b></p><br>图工具类
 * <p><b>方法：</b></p>
 * <br> {@link #exportGraph(Graph, String)}导出图
 *
 * @author 60rzvvbj
 * @date 2021/5/2
 */
public class GraphUtil {

    /**
     * <p><b>方法名：</b>{@code exportGraph}</p>
     * <p><b>功能：</b></p><br>导出图
     *
     * @param graph 要导出的图
     * @param path  文件的输出路径（文件后缀名需为.ycx）
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    public static boolean exportGraph(Graph graph, String path) {
        PrintStream printStream = null;
        try {

            // start
            printStream = new PrintStream(path);
            printStream.println("{");

            // type
            printStream.println("\t\"type\": " + graph.getType() + ",");

            // nodes
            printStream.print("\t\"nodes\": [");
            int len = graph.getNodes().size();
            int count = 0;
            for (GraphNode node : graph.getNodes()) {
                if (count == len - 1) {
                    printStream.println("\"" + node.getName() + "\"],");
                } else {
                    printStream.print("\"" + node.getName() + "\", ");
                }
                count++;
            }

            // edges
            printStream.println("\t\"edges\": [");
            len = graph.getEdges().size();
            count = 0;
            for (GraphEdge edge : graph.getEdges()) {
                String str = "\t\t[\"" + edge.getFirst().getName() + "\", \"" + edge.getSecond().getName() + "\", " + edge.getRank() + "]";
                if (count != len - 1) {
                    str += ",";
                }
                printStream.println(str);
                count++;
            }
            printStream.println("\t],");

            // routes
            len = graph.getRoutes().size();
            count = 0;
            printStream.println("\t\"routes\": [");
            for (GraphRoute route : graph.getRoutes()) {
                String end = ",";
                if (count == len - 1) {
                    end = "";
                }
                printStream.print("\t\t[\"" + route.getName() + "\", ");
                int tLen = route.getNodes().size();
                int tCount = 0;
                for (GraphNode node : route.getNodes()) {
                    if (tCount == tLen - 1) {
                        printStream.println("\"" + node.getName() + "\"]" + end);
                    } else {
                        printStream.print("\"" + node.getName() + "\", ");
                    }
                    tCount++;
                }
                count++;
            }
            printStream.println("\t]");

            // end
            printStream.println("}");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
        return false;
    }

    // 导入
    public static GraphData importGraph(String path) {
        Reader reader = null;
        char[] chars = new char[1024];
        int len;
        StringBuilder res = new StringBuilder();
        try {
            reader = new FileReader(path);
            while ((len = reader.read(chars)) != -1) {
                res.append(new String(chars, 0, len));
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        GraphData graphData = null;
        try {
            graphData = mapper.readValue(res.toString(), GraphData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphData;
    }
}


