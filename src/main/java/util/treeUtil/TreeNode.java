package util.treeUtil;

/**
 * 树节点接口
 *
 * @author yohoyes, 60rzvvbj
 * @date 2021/4/17
 */
public interface TreeNode {
    void setLeft(TreeNode l);

    void setRight(TreeNode r);

    TreeNode getLeft();

    TreeNode getRight();

    void setData(Object object);

    String getData();
}
