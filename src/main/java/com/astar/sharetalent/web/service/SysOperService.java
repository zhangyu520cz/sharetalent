package com.astar.sharetalent.web.service;


import com.astar.sharetalent.web.vo.SysOperVo;
import com.astar.sharetalent.common.base.PageVo;
import java.util.List;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
public interface SysOperService{

    /**新增**/
    SysOperVo add(SysOperVo entity);

    /**根据ID查询**/
    SysOperVo selectById(Long id);

    /**根据账号名查询**/
    SysOperVo selectByUserName(String userName);

    /**分页查询**/
    PageVo<SysOperVo> selectList(SysOperVo entity);

    /**根据ID更新**/
    void updateById(SysOperVo entity);

    /**批量删除**/
    void deleteBatchIds(List<Long> ids);


}
