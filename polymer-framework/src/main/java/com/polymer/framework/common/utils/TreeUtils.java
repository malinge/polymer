package com.polymer.framework.common.utils;

import com.polymer.framework.common.pojo.TreeNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类，如：菜单、部门等
 *
 * @author polymer
 */
public class TreeUtils {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        // pid不能为空
        AssertUtils.isNull(pid, "pid");

        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getPid())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }

        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();

        // list转map
        Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getPid());
            if (parent != null && !(node.getId().equals(parent.getId()))) {
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }

        return result;
    }
    /*// 构建树结构并计算子集数量
    public static List<TreeNode> buildTree(List<TreeNode> nodes) {
        Map<Long, TreeNode> nodeMap = new HashMap<>();
        List<TreeNode> rootNodes = new ArrayList<>();

        // 将所有节点放入Map中，方便查找
        for (TreeNode node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        // 组装树结构
        for (TreeNode node : nodes) {
            if (node.getParentId() == null || !nodeMap.containsKey(node.getParentId())) {
                rootNodes.add(node);
            } else {
                TreeNode parent = nodeMap.get(node.getParentId());
                parent.addChild(node);
            }
        }

        // 计算每个节点的直接子集数量和所有子集数量
        for (TreeNode node : rootNodes) {
            calculateChildrenCount(node);
        }

        return rootNodes;
    }

    private static void calculateChildrenCount(TreeNode node) {
        int directChildrenCount = node.getChildren().size();
        int allChildrenCount = directChildrenCount;

        for (TreeNode child : node.getChildren()) {
            calculateChildrenCount(child);
            allChildrenCount += child.getDirectChildrenCount() + child.getAllChildrenCount();
        }

        node.setDirectChildrenCount(directChildrenCount);
        node.setAllChildrenCount(allChildrenCount);
    }*/

}