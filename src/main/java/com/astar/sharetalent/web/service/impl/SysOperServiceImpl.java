package com.astar.sharetalent.web.service.impl;


import com.astar.sharetalent.web.entity.SysOperDo;
import com.astar.sharetalent.web.vo.SysOperVo;
import com.astar.sharetalent.web.dao.SysOperMapper;
import com.astar.sharetalent.web.service.SysOperService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.astar.sharetalent.common.base.PageVo;
import com.astar.sharetalent.util.WrapperUtil;
import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Service
public class SysOperServiceImpl extends ServiceImpl<SysOperMapper, SysOperDo > implements SysOperService {

    /**新增**/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysOperVo  add(SysOperVo entity) {
        SysOperDo entityDo = new SysOperDo();
        super.insert(entity.copyPropertiesTo(entityDo));
        return entityDo.copyPropertiesTo(entity);
    }

    /**根据ID查询**/
    @Override
    public SysOperVo  selectById(Long id) {
        SysOperDo  entity = super.selectById(id);
        if(entity == null){
            return null;
        }
        return entity.copyPropertiesTo(new SysOperVo ());
    }

    @Override
    public SysOperVo selectByUserName(String userName) {
        Wrapper<SysOperDo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",userName);
        SysOperDo sysOperDo = super.selectOne(wrapper);
        return sysOperDo != null ? sysOperDo.copyPropertiesTo(new SysOperVo()) : null;
    }

    /**分页查询**/
    @Override
    public PageVo<SysOperVo > selectList(SysOperVo  entity) {
        Wrapper wrapper = WrapperUtil.getWrapperByVO(entity);
        Page<SysOperDo > returnPage = super.selectPage(entity.plusPage(), wrapper);
        return new PageVo(returnPage,SysOperVo .class);
    }

    /**根据ID更新**/
    @Override
    @Transactional
    public void updateById(SysOperVo  entity) {
        super.updateById(entity.copyPropertiesTo(new SysOperDo()));
    }

    /**批量删除**/
    @Override
    @Transactional
    public void deleteBatchIds(List<Long> longs) {
        super.deleteBatchIds(longs);
    }

}
