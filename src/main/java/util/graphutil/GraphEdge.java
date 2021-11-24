package util.graphutil;

/**
 * <p><b>接口名：</b>{@code GraphEdge}</p>
 * <p><b>功能：</b></p><br>图中的边
 * <p><b>方法：</b></p>
 * <br> {@link #getRank()}获取边的权值
 * <br> {@link #getFirst()}获取第一个节点
 * <br> {@link #getSecond()}获取第二个节点
 *
 * @author 60rzvvbj
 * @date 2021/5/2
 */
public interface GraphEdge {

    /**
     * <p><b>方法名：</b>{@code getRank}</p>
     * <p><b>功能：</b></p><br>获取边的权值（目前工具类中未投入使用，以后可能会用到）
     *
     * @return 权值
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    int getRank();

    /**
     * <p><b>方法名：</b>{@code getFirst}</p>
     * <p><b>功能：</b></p><br>获取第一个节点
     *
     * @return 第一个节点
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    GraphNode getFirst();

    /**
     * <p><b>方法名：</b>{@code getSecond}</p>
     * <p><b>功能：</b></p><br>获取第二个节点
     *
     * @return 第二个节点
     * @author 60rzvvbj
     * @date 2021/5/2
     */
    GraphNode getSecond();
}
