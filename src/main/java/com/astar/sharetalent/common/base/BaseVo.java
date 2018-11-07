package com.astar.sharetalent.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiaoleilu.hutool.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据模型基类
 */
@Data
public abstract class BaseVo  implements Serializable {
    private static final long serialVersionUID = 7258436689721815928L;

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Long id;


    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数",name = "size",hidden = true)
    @JSONField(serialize = false)
    private int size = 10;


    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页",name = "current",hidden = true)
    @JSONField(serialize = false)
    private int current = 1;


    /**
     * DESC ASC
     */
    @ApiModelProperty(value = "desc降序,asc升序",name = "order",example = "desc",hidden = true)
    @JSONField(serialize = false)
    private String order = "desc";


    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段",name = "sidx",example = "id",hidden = true)
    @JSONField(serialize = false)
    private String sidx = "id";


    public <T> T copyPropertiesTo(T target, String... ignoreProperties){
        BeanUtils.copyProperties(this,target,ignoreProperties);
        return target;
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public <T> T  copyPropertiesIgnoreNull(T target){
        BeanUtils.copyProperties(this, target, getNullPropertyNames(this));
        return target;
    }


    public <F extends BaseDo> Page<F> plusPage(){
        Page<F> plusPage = new Page<>(current,size);
        boolean isAsc = order.equals("asc");
        plusPage.setAsc(isAsc);
        if (isAsc){
            plusPage.setAscs(Arrays.asList(StrUtil.toUnderlineCase(sidx)));
        }else {
            plusPage.setDescs(Arrays.asList(StrUtil.toUnderlineCase(sidx)));
        }
        return plusPage;
    }




}
