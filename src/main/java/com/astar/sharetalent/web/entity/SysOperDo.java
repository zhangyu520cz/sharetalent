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
 * 管理员
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_oper")
public class SysOperDo extends BaseDo {

    private static final long serialVersionUID = 1L;

        /**
         * 账号
         */
        @TableField("user_name")
        private String userName;

        /**
         * 密码
         */
        private String password;

        /**
         * 真是姓名
         */
        @TableField("real_name")
        private String realName;

        /**
         * 手机号码
         */
        @TableField("mobile_number")
        private String mobileNumber;

        /**
         * 状态，1:正常;2:已删除;3:已冻结
         */
        private Integer status;

        /**
         * 备注
         */
        private String remark;

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



}
