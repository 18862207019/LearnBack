package com.dada.entity.sys.tree;

import com.dada.entity.sys.SysUser;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 用户树节点
 */
public class UserTreeNode extends SysUser implements Serializable {

    List<UserTreeNode> children = Lists.newArrayList();

}
