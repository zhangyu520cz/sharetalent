package com.astar.sharetalent.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.astar.sharetalent.util.DataTransform;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


/**
 * @author: zhangyu
 * @description:
 * @date: 2018/6/28
 */
@Data
@ApiModel
public  class PageVo<T extends BaseVo> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数",name = "size")
    private int size = 10;


    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页",name = "current")
    private int current = 1;


    /**
     * 总数
     */
    @ApiModelProperty(value = "总数",name = "total")
    private long total;


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


    public PageVo() {
    }

    public PageVo(int size, int current) {
        this.size = size;
        this.current = current;
    }



    public PageVo(int size, int current, long total, List<T> records) {
        this.size = size;
        this.current = current;
        this.total = total;
        this.records = records;
    }



    public  <F extends BaseDo> PageVo(Page<F> page,Class<T> tClass){
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.records = DataTransform.doTvo(page.getRecords(), tClass);
    }







}
