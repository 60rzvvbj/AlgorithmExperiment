package util.graphutil;

import java.util.List;

/**
 * <p><b>接口名：</b>{@code Graph}</p>
 * <p><b>功能：</b></p><br>图
 * <p><b>方法：</b></p>
 * <br> {@link #getType()}获取图的类型
 * <br> {@link #getNodes()}返回图中所有节点
 * <br> {@link #getEdges()}返回图中所有边
 * <br> {@link #getRoutes()}getRoutes
 *
 * @author 60rzvvbj
 * @date 2021/5/2
 */
public interface Graph {

    /**
     * <p><b>方法名：</b>{@code getType}</p>
     * <p><b>功能：</b></p><br>获取图的类型
     *
     * <br>目前支持的类型：
     * <br>0 - 无向图
     *
     * @return 图的类型
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    int getType();

    /**
     * <p><b>方法名：</b>{@code getNodes}</p>
     * <p><b>功能：</b></p><br>返回图中所有节点
     *
     * @return 所有节点的集合
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    List<GraphNode> getNodes();

    /**
     * <p><b>方法名：</b>{@code getEdges}</p>
     * <p><b>功能：</b></p><br>返回图中所有边
     *
     * @return 所有边的集合
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    List<GraphEdge> getEdges();

    /**
     * <p><b>方法名：</b>{@code getRoutes}</p>
     * <p><b>功能：</b></p><br>返回所有通路
     *
     * @return 所有通路的集合
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    List<GraphRoute> getRoutes();
}
