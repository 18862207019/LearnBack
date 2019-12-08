package com.dada.entity.sys.tree;

import com.dada.entity.sys.SysPermission;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树节点
 */
@Data
public class PermissionTreeNode extends SysPermission implements Serializable {

    List<PermissionTreeNode> children = Lists.newArrayList();

}
