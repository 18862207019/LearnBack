package com.dada.controller.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dada.config.redis.menthod.MethodLock;
import com.dada.config.redis.param.MethodParamLock;
import com.dada.config.redis.param.ParamLock;
import com.dada.controller.base.BaseController;
import com.dada.entity.task.SysTask;
import com.dada.entity.vo.UserInfoVO;
import com.dada.service.task.SysTaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.result.Result;
import util.result.ResultGenerator;
import java.util.Date;
import java.util.List;

/**
 * 任务计划
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private SysTaskService sysTaskService;

    /**
     *  列表可按名称查询
     */
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        UserInfoVO currentUser = getCurrentUser();
        List<SysTask> list = sysTaskService.list(new LambdaQueryWrapper<SysTask>().like(StringUtils.isNotBlank(title), SysTask::getTitle, title).eq(SysTask::getCreateUserId,currentUser.getId()));
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     *  添加  加上redis分布式锁
     */
    @MethodLock
    @PostMapping("add")
    public Result add(@RequestBody SysTask sysTask){
        UserInfoVO currentUser = getCurrentUser();
        sysTask.setCTime(new Date());
        sysTask.setUTime(new Date());
        sysTask.setCreateUserId(currentUser.getId());
        sysTaskService.save(sysTask);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *  编辑
     */
    @MethodParamLock
    @PostMapping("update")
    public Result update(@RequestBody @ParamLock SysTask sysTask){
        sysTask.setUTime(new Date());
        sysTaskService.updateById(sysTask);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除
     */
    @MethodParamLock
    @PostMapping("del")
    public Result del(@ParamLock @RequestParam("id")Integer id){
        sysTaskService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *  结束
     */
    @PostMapping("end")
    public Result end(@RequestParam("id")Integer id){
        SysTask sysTask = sysTaskService.getById(id);
        sysTask.setEndTime(new Date());
        sysTaskService.updateById(sysTask);
        return ResultGenerator.genSuccessResult();
    }

}
