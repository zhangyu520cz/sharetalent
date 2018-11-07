package com.astar.sharetalent.common.web;

import com.astar.sharetalent.common.R;
import com.astar.sharetalent.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jujun chen
 * @description:
 * @date: 2018/1/1
 */
@Slf4j
@RestController
public class ErrorController {

    @RequestMapping("/404")
    public R error404(){
        SystemException systemException = SystemException.SYSTEM_NO_HANDLER_FOUND_EXCEPTION;
        return R.error(systemException.getCode(),systemException.getMsg());
    }

    @RequestMapping("/502")
    public R error502(){
        SystemException systemException = SystemException.SYSTEM_BAD_GATEWAY;
        return R.error(systemException.getCode(),systemException.getMsg());
    }

}
