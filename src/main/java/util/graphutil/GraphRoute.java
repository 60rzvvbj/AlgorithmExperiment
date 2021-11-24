package util.graphutil;

import java.util.List;

/**
 * <p><b>接口名：</b>{@code GraphRoute}</p>
 * <p><b>功能：</b></p><br>图中的通路
 * <p><b>方法：</b></p>
 * <br> {@link #getName()}获取通路的名称
 * <br> {@link #getNodes()}获取通路上的节点
 *
 * @author 60rzvvbj
 * @date 2021/5/2
 */
public interface GraphRoute {

    /**
     * <p><b>方法名：</b>{@code getName}</p>
     * <p><b>功能：</b></p><br>获取通路的名称
     *
     * @return 线路的名称
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    String getName();

    /**
     * <p><b>方法名：</b>{@code getNodes}</p>
     * <p><b>功能：</b></p><br>获取通路上的节点
     *
     * @return 通路上节点的集合
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    List<GraphNode> getNodes();
}
