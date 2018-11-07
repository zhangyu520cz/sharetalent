package com.astar.sharetalent.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhangyu
 * @description: 控制器基类
 * @date: 2018/1/1
 */
@Slf4j
public abstract class BaseController {

    //时间参数自动绑定
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CustomDateEditor cust = new CustomDateEditor(sdf, true);
        binder.registerCustomEditor(Date.class, cust);
    }

}
