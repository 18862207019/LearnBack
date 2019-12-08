package com.dada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dada.entity.sys.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findList(@Param("name") String name);

    List<SysRole> findListByUserId(String userId);

}