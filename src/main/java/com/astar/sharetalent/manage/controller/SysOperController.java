package com.astar.sharetalent.manage.controller;

import com.astar.sharetalent.web.vo.SysOperVo;
import com.astar.sharetalent.common.base.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.astar.sharetalent.common.web.BaseController;
import com.astar.sharetalent.web.service.SysOperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Arrays;
import javax.validation.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


/**
 * <p>
 * 管理员前端控制器
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@RestController
@Validated
@RequestMapping("/manage/sysoper")
@Api(tags = {"SysOperController"},description = "管理员接口")
@Slf4j
public class SysOperController extends BaseController {
    @Autowired
    private SysOperService service;

    /**
    * 根据管理员ID查询
    */
    @GetMapping("/query/{id}")
    @ApiOperation(value = "/query/{id}",notes = "根据管理员ID查询",response = SysOperVo.class)
    public SysOperVo query(@PathVariable Long id) {
        SysOperVo entity = service.selectById(id);
        return entity;
    }

    /**
    * 查询管理员分页方法
    */
    @ApiOperation(value = "/queryListPage",notes = "查询管理员分页方法")
    @PostMapping("/queryListPage")
    public PageVo<SysOperVo> queryListPage(SysOperVo entity) {
        PageVo<SysOperVo> pageVo = (PageVo<SysOperVo>) service.selectList(entity);
        return pageVo;
    }

    /**
     * 新增管理员方法
     */
    @ApiOperation(value = "/add",notes = "新增管理员方法",response = SysOperVo.class)
    @PostMapping("/add")
    public SysOperVo add(@Valid SysOperVo entity) {
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        SysOperVo entityVo = service.add(entity);
        return entityVo;
    }

    /**
     * 修改管理员方法
     */
    @ApiOperation(value = "/update",notes = "修改管理员方法",response = SysOperVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "修改id",example="1",required = true,dataType = "long",paramType = "query"),
    })
    @PostMapping("/update")
    public void update(@Valid SysOperVo entity) {
        entity.setModifyDate(new Date());
        service.updateById(entity);
    }

    /**
     * 批量删除管理员方法
     */
    @ApiOperation(value = "/delBatchByIds",notes = "批量删除管理员方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "删除id,逗号分隔",example="1,2,3",required = true,dataType = "String",paramType = "query"),
    })
    @PostMapping("/delBatchByIds")
    public void delBatchByIds(@NotEmpty(message = "ids不能为空") Long[] ids) {
        service.deleteBatchIds(Arrays.asList(ids));
    }
}

