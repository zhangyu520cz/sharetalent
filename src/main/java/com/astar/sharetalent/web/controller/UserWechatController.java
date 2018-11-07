package com.astar.sharetalent.web.controller;

import com.astar.sharetalent.web.vo.UserWechatVo;
import com.astar.sharetalent.common.base.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.astar.sharetalent.common.web.BaseController;
import com.astar.sharetalent.web.service.UserWechatService;
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
 * 微信用户前端控制器
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@RestController
@Validated
@RequestMapping("/api/userwechat")
@Api(tags = {"UserWechatController"},description = "微信用户接口")
@Slf4j
public class UserWechatController extends BaseController {
    @Autowired
    private UserWechatService service;

    /**
    * 根据微信用户ID查询
    */
    @GetMapping("/query/{id}")
    @ApiOperation(value = "/query/{id}",notes = "根据微信用户ID查询",response = UserWechatVo.class)
    public UserWechatVo query(@PathVariable Long id) {
        UserWechatVo entity = service.selectById(id);
        return entity;
    }

    /**
    * 查询微信用户分页方法
    */
    @ApiOperation(value = "/queryListPage",notes = "查询微信用户分页方法")
    @PostMapping("/queryListPage")
    public PageVo<UserWechatVo> queryListPage(UserWechatVo entity) {
        PageVo<UserWechatVo> pageVo = (PageVo<UserWechatVo>) service.selectList(entity);
        return pageVo;
    }

    /**
     * 新增微信用户方法
     */
    @ApiOperation(value = "/add",notes = "新增微信用户方法",response = UserWechatVo.class)
    @PostMapping("/add")
    public UserWechatVo add(@Valid UserWechatVo entity) {
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        UserWechatVo entityVo = service.add(entity);
        return entityVo;
    }

    /**
     * 修改微信用户方法
     */
    @ApiOperation(value = "/update",notes = "修改微信用户方法",response = UserWechatVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "修改id",example="1",required = true,dataType = "long",paramType = "query"),
    })
    @PostMapping("/update")
    public void update(@Valid UserWechatVo entity) {
        entity.setModifyDate(new Date());
        service.updateById(entity);
    }

    /**
     * 批量删除微信用户方法
     */
    @ApiOperation(value = "/delBatchByIds",notes = "批量删除微信用户方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "删除id,逗号分隔",example="1,2,3",required = true,dataType = "String",paramType = "query"),
    })
    @PostMapping("/delBatchByIds")
    public void delBatchByIds(@NotEmpty(message = "ids不能为空") Long[] ids) {
        service.deleteBatchIds(Arrays.asList(ids));
    }
}

