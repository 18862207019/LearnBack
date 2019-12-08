package com.dada.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dada.entity.sys.tree.PermissionTreeNode;
import com.dada.entity.sys.tree.TreeBuilder;
import com.dada.entity.sys.SysPermission;
import com.dada.service.sys.SysPermissionService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.result.Result;
import util.result.ResultGenerator;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 菜单管理
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 树结构列表展示   获取当前资源(菜单按钮)
     */
    @PostMapping("/listTreePermission")
    public Result listTreePermission() {
        List<SysPermission> list = permissionService.listTreePermission();
        List<PermissionTreeNode> permissionTreeNodeList = Lists.newArrayList();
        if (list != null && list.size() > 0) {
            list.forEach(temp -> {
                PermissionTreeNode permissionTreeNode = new PermissionTreeNode();
                BeanUtils.copyProperties(temp, permissionTreeNode);
                permissionTreeNodeList.add(permissionTreeNode);
            });
        }
        List<PermissionTreeNode> permissionTreeNodeList2 = TreeBuilder.buildPermissionTree(permissionTreeNodeList);
        permissionTreeNodeList2.stream().sorted(Comparator.comparing(PermissionTreeNode::getSortNo)).collect(Collectors.toList());
        JSONObject json = new JSONObject();
        json.put("menuList", list);
        json.put("menuTree", permissionTreeNodeList2);

        return new Result<>().setData(json);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysPermission permission) {
        SysPermission sysPermission = permissionService.getOne(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getResources, permission.getResources()));
        if (sysPermission != null) {
            return ResultGenerator.genFailResult("编码不可重复，添加失败！");
        }
        permission.setCreateTime(new Date());
        permission.setUpdateTime(new Date());
        permissionService.save(permission);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysPermission permission) {
        permission.setUpdateTime(new Date());
        permissionService.updateById(permission);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) throws Exception {
        permissionService.deleteRelation(String.valueOf(id));
        return ResultGenerator.genSuccessResult();
    }

}
