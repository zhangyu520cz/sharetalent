package com.astar.sharetalent.web.vo;


import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.astar.sharetalent.common.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 管理员 vo
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="管理员",description="管理员")
public class SysOperVo extends BaseVo{

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号",name="userName")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码",name="password")
    private String password;

    /**
     * 真是姓名
     */
    @ApiModelProperty(value="真是姓名",name="realName")
    private String realName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码",name="mobileNumber")
    private String mobileNumber;

    /**
     * 状态，1:正常;2:已删除;3:已冻结
     */
    @ApiModelProperty(value="状态，1:正常;2:已删除;3:已冻结",name="status")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;


}

