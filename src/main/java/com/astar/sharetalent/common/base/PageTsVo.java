package com.astar.sharetalent.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * @author: xfwang
 * @description: 时间戳分页
 * @date: 2018/6/28
 */
@Data
@ApiModel
public  class PageTsVo<T extends BaseVo> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数",name = "size")
    private int size = 10;


    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳",name = "timeStamp")
    private Date timeStamp = new Date();

    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "查询数据列表",name = "records")
    private List<T> records = Collections.emptyList();


    /**
     * DESC ASC
     */
    @ApiModelProperty(value = "desc降序,asc升序",name = "order",example = "desc")
    @JSONField(serialize = false)
    private String order = "desc";


    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段",name = "sidx",example = "id")
    @JSONField(serialize = false)
    private String sidx = "id";


    public PageTsVo() {
    }

    public PageTsVo(int size, Date timeStamp) {
        this.size = size;
        this.timeStamp = timeStamp;
    }

    public PageTsVo(int size, Date timeStamp, long total, List<T> records) {
        this.size = size;
        this.timeStamp = timeStamp;
        this.records = records;
    }

}
