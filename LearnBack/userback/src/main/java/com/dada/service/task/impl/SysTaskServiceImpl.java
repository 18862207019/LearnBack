package com.dada.service.task.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dada.mapper.SysTaskMapper;
import com.dada.entity.task.SysTask;
import com.dada.service.task.SysTaskService;
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements SysTaskService{

}
