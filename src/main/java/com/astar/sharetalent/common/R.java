package com.astar.sharetalent.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyu
 * @description: 结果返回
 * @date: 2018/6/19
 */
@Data
public class R implements Serializable {

    private String result = "success";

    //错误码
    private String code = "0";

    //错误信息
    private String msg = "";


    //返回实体
    private Object data;


    public R(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = (data == null ? "" : data);
        this.result = "error";
    }

    public R(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = "";
        this.result = "error";
    }

    public R(Object data) {
        this.data = (data == null ? "" : data);
    }

    public static R error(String code, String msg){
        return new R(code,msg);
    }

    public static R error(String msg){
        return new R("500",msg);
    }

    public static R ok(String msg){
        return new R(msg);
    }

    public static R ok(Object data){
        return new R(data);
    }


}
