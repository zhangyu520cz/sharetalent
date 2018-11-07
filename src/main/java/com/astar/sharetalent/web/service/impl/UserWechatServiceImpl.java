package com.astar.sharetalent.web.service.impl;


import com.astar.sharetalent.web.entity.UserWechatDo;
import com.astar.sharetalent.web.vo.UserWechatVo;
import com.astar.sharetalent.web.dao.UserWechatMapper;
import com.astar.sharetalent.web.service.UserWechatService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.astar.sharetalent.common.base.PageVo;
import com.astar.sharetalent.util.WrapperUtil;
import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 微信用户 服务实现类
 * </p>
 *
 * @author zhangyu
 * @date 2018-10-15
 */
@Service
public class UserWechatServiceImpl extends ServiceImpl<UserWechatMapper, UserWechatDo > implements UserWechatService {

    /**新增**/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserWechatVo  add(UserWechatVo entity) {
        UserWechatDo entityDo = new UserWechatDo();
        super.insert(entity.copyPropertiesTo(entityDo));
        return entityDo.copyPropertiesTo(entity);
    }

    /**根据ID查询**/
    @Override
    public UserWechatVo  selectById(Long id) {
        UserWechatDo  entity = super.selectById(id);
        if(entity == null){
            return null;
        }
        return entity.copyPropertiesTo(new UserWechatVo ());
    }

    /**分页查询**/
    @Override
    public PageVo<UserWechatVo > selectList(UserWechatVo  entity) {
        Wrapper wrapper = WrapperUtil.getWrapperByVO(entity);
        Page<UserWechatDo > returnPage = super.selectPage(entity.plusPage(), wrapper);
        return new PageVo(returnPage,UserWechatVo .class);
    }

    /**根据ID更新**/
    @Override
    @Transactional
    public void updateById(UserWechatVo  entity) {
        super.updateById(entity.copyPropertiesTo(new UserWechatDo()));
    }

    /**批量删除**/
    @Override
    @Transactional
    public void deleteBatchIds(List<Long> longs) {
        super.deleteBatchIds(longs);
    }

}
