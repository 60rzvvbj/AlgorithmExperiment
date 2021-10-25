package util.treeUtil;

import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;
import org.xmind.core.internal.dom.WorkbookBuilderImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <p><b>类名：</b>{@code TreeUtil}</p>
 * <p><b>功能：</b></p><br>树工具类
 * <p><b>方法：</b></p>
 * <br> {@link #buildXmind(TreeNode, String)}生成Xmind文件
 * <br> {@link #printTree(TreeNode)}打印树
 *
 * @author 60rzvvbj
 * @date 2021/4/21
 */
public class TreeUtil {
    private static IWorkbookBuilder builder = new WorkbookBuilderImpl();
    private static IWorkbook workbook;

    public static void buildXmind(TreeNode root, String fileName) {
        workbook = builder.createWorkbook();
        ISheet sheet = workbook.getPrimarySheet();
        ITopic rootTopic = sheet.getRootTopic();
        rootTopic.setTitleText(root.getData());
        buildXmindTree(root.getLeft(), rootTopic);
        buildXmindTree(root.getRight(), rootTopic);

        try {
            workbook.save(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void buildXmindTree(TreeNode node, ITopic parentTopic) {
        if (node == null) {
            return;
        }
        ITopic topic = workbook.createTopic();
        topic.setTitleText(node.getData());
        parentTopic.add(topic);
        buildXmindTree(node.getLeft(), topic);
        buildXmindTree(node.getRight(), topic);
    }

    public static void printTree(TreeNode root) {
        LinkedList<TreeNode> allNode = new LinkedList<>();
        HashMap<TreeNode, Integer> nodeHeight = new HashMap<>();
        LinkedList<TreeNode> leafNode = new LinkedList<>();
        showTreeDfs(allNode, nodeHeight, leafNode, 0, root);
        HashMap<TreeNode, Integer> nodeX = new HashMap<>();
        HashMap<TreeNode, Integer> nodeY = new HashMap<>();
        int maxHeight = 0;
        for (TreeNode node : nodeHeight.keySet()) {
            maxHeight = Math.max(nodeHeight.get(node), maxHeight);
        }
        int width = maxHeight + maxHeight / 2;
        char[][] chart = new char[maxHeight * 2 + 2][width * (leafNode.size() + 1)];
        for (char[] chars : chart) {
            Arrays.fill(chars, ' ');
        }
        int x = width;
        for (TreeNode node : leafNode) {
            nodeX.put(node, x);
            x += width;
        }
        for (int y = maxHeight; y >= 0; y--) {
            for (TreeNode node : allNode) {
                if (nodeHeight.get(node) == y) {
                    nodeY.put(node, 2 * y);
                    if (node.getLeft() != null) {
                        nodeX.put(node, (nodeX.get(node.getLeft()) + nodeX.get(node.getRight())) / 2);
                    }
                }
            }
        }
        for (TreeNode node : allNode) {
            setChar(chart, node.getData(), nodeX.get(node), nodeY.get(node));
            if (node.getLeft() != null && node.getRight() != null) {
                if (!node.getLeft().getData().equals("\0")) {
                    chart[(nodeY.get(node) + nodeY.get(node.getLeft())) / 2][(nodeX.get(node) + nodeX.get(node.getLeft())) / 2] = '/';
                }
                if (!node.getRight().getData().equals("\0")) {
                    chart[(nodeY.get(node) + nodeY.get(node.getRight())) / 2][(nodeX.get(node) + nodeX.get(node.getRight())) / 2 + 1] = '\\';
                }
            }
        }
        for (int i = 0; i < chart.length; i++) {
            for (int j = 0; j < chart[i].length; j++) {
                System.out.print(chart[i][j]);
            }
            System.out.println();
        }
    }

    private static void setChar(char[][] chart, String str, int x, int y) {
        String[] strArr = str.split(" ");
        for (int i = 0; i < strArr.length; i++) {
            char[] chars = strArr[i].toCharArray();
            int len = chars.length;
            int start = x - len / 2;
            int index = 0;
            for (int j = start; index < chars.length; j++) {
                chart[y + i][j + 1] = chars[index++];
            }
        }
    }

    private static void showTreeDfs(LinkedList<TreeNode> allNode, HashMap<TreeNode, Integer> nodeHeight, LinkedList<TreeNode> leafNode, int nowHeight, TreeNode node) {
        allNode.add(node);
        nodeHeight.put(node, nowHeight);
        if (node.getLeft() == null && node.getRight() == null) {
            leafNode.add(node);
        } else {
            if (node.getLeft() == null) {
                node.setLeft(new NullNode());
            }
            if (node.getRight() == null) {
                node.setRight(new NullNode());
            }
            showTreeDfs(allNode, nodeHeight, leafNode, nowHeight + 1, node.getLeft());
            showTreeDfs(allNode, nodeHeight, leafNode, nowHeight + 1, node.getRight());
        }
    }
}

class NullNode implements TreeNode {

    @Override
    public void setLeft(TreeNode l) {

    }

    @Override
    public void setRight(TreeNode r) {

    }

    @Override
    public TreeNode getLeft() {
        return null;
    }

    @Override
    public TreeNode getRight() {
        return null;
    }

    @Override
    public void setData(Object object) {

    }

    @Override
    public String getData() {
        return "\0";
    }
}