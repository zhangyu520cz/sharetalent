package com.astar.sharetalent.web.entity;


import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.astar.sharetalent.common.base.BaseDo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信用户
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_wechat")
public class UserWechatDo extends BaseDo {

    private static final long serialVersionUID = 1L;

        /**
         * 微信获取用户信息的凭证，对于某个小程序具有唯一性
         */
        @TableField("open_id")
        private String openId;

        /**
         * 关联管理员账号
         */
        @TableField("oper_id")
        private Long operId;

        /**
         * 手机号
         */
        @TableField("mobile_number")
        private String mobileNumber;

        /**
         * 创建时间
         */
        @TableField("create_date")
        private Date createDate;

        /**
         * 修改时间
         */
        @TableField("modify_date")
        private Date modifyDate;

        /**
         * 真实姓名
         */
        @TableField("real_name")
        private String realName;

        /**
         * 昵称
         */
        @TableField("nick_name")
        private String nickName;

        /**
         * 头像
         */
        @TableField("pic_url")
        private String picUrl;

        /**
         * 性别（0:男 1:女）
         */
        private Integer sex;

        /**
         * 市
         */
        private String city;

        /**
         * 省
         */
        private String province;

        /**
         * 国家
         */
        private String country;

        /**
         * 语言
         */
        private String language;

        /**
         * 备注
         */
        private String remark;



}
