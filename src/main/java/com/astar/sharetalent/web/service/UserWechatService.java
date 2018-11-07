package com.astar.sharetalent.web.service;


import com.astar.sharetalent.web.vo.UserWechatVo;
import com.astar.sharetalent.common.base.PageVo;
import java.util.List;

/**
 * <p>
 * 微信用户 服务类
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
public interface UserWechatService{

    /**新增**/
    UserWechatVo add(UserWechatVo entity);

    /**根据ID查询**/
    UserWechatVo selectById(Long id);

    /**分页查询**/
    PageVo<UserWechatVo> selectList(UserWechatVo entity);

    /**根据ID更新**/
    void updateById(UserWechatVo entity);

    /**批量删除**/
    void deleteBatchIds(List<Long> ids);


}
