package com.dada.entity.sys.tree;
import com.dada.entity.vo.MenuVO;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *   树结构建造
 */
public class TreeBuilder {

    public static Set<MenuVO> buildTree(Set<MenuVO> allNodes) {
        // 根节点
        Set<MenuVO> root = new HashSet<>();
        allNodes.forEach(node -> {
            if (Integer.valueOf( node.getParentId() ) == 0) {
                root.add(node);
            }
        });
        root.forEach(node -> {
            findChildren(node, allNodes);
        });
        return root;
    }


    /**
     * 递归查找子节点
     */
    private static MenuVO findChildren(MenuVO treeNode, Set<MenuVO> treeNodes) {
        for (MenuVO it : treeNodes) {
            if (String.valueOf( treeNode.getId() ).equals(it.getParentId())) {
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    public static List<PermissionTreeNode> buildPermissionTree(List<PermissionTreeNode> allNodes) {
        // 根节点
        List<PermissionTreeNode> root = Lists.newArrayList();
        allNodes.forEach(node -> {
            if (Integer.valueOf( node.getParentId() ) == 0) {
                root.add(node);
            }
        });
        root.forEach(node -> {
            findPermissionChildren(node, allNodes);
        });

        //对根节点排序
        List<PermissionTreeNode> sortedList = root.stream().sorted( Comparator.comparing( PermissionTreeNode::getSortNo ) ).collect( Collectors.toList());
        //先清空，在添加
        root.clear();
        root.addAll( sortedList );
        return root;
    }


    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static PermissionTreeNode findPermissionChildren(PermissionTreeNode treeNode, List<PermissionTreeNode> treeNodes) {
        for (PermissionTreeNode it : treeNodes) {
            if (String.valueOf( treeNode.getId() ).equals(it.getParentId())) {
                treeNode.getChildren().add(findPermissionChildren(it, treeNodes));
            }
        }
        //对子节点排序
        List<PermissionTreeNode> childrenSorted = treeNode.getChildren().stream().sorted( Comparator.comparing( PermissionTreeNode::getSortNo ) ).collect( Collectors.toList());
        //先清空，在添加
        treeNode.getChildren().clear();
        treeNode.getChildren().addAll( childrenSorted );
        return treeNode;
    }


}