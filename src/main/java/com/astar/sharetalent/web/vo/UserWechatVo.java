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
 * 微信用户 vo
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="微信用户",description="微信用户")
public class UserWechatVo extends BaseVo{

    private static final long serialVersionUID = 1L;

    /**
     * 微信获取用户信息的凭证，对于某个小程序具有唯一性
     */
    @ApiModelProperty(value="微信获取用户信息的凭证，对于某个小程序具有唯一性",name="openId")
    private String openId;

    /**
     * 关联管理员账号
     */
    @ApiModelProperty(value="关联管理员账号",name="operId")
    private Long operId;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",name="mobileNumber")
    private String mobileNumber;

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

    /**
     * 真实姓名
     */
    @ApiModelProperty(value="真实姓名",name="realName")
    private String realName;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称",name="nickName")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像",name="picUrl")
    private String picUrl;

    /**
     * 性别（0:男 1:女）
     */
    @ApiModelProperty(value="性别（0:男 1:女）",name="sex")
    private Integer sex;

    /**
     * 市
     */
    @ApiModelProperty(value="市",name="city")
    private String city;

    /**
     * 省
     */
    @ApiModelProperty(value="省",name="province")
    private String province;

    /**
     * 国家
     */
    @ApiModelProperty(value="国家",name="country")
    private String country;

    /**
     * 语言
     */
    @ApiModelProperty(value="语言",name="language")
    private String language;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注",name="remark")
    private String remark;


}

