package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.PreventVO;
import com.wr.api.owner.mapper.serve.PreventMapper;
import com.wr.api.owner.service.serve.PreventService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.PreventSafety;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 10:50:47
 * @Desc: 服务-防控信息业务实现
 */
@Service
@Slf4j
public class PreventServiceImpl implements PreventService {
    @Resource
    private PreventMapper preventMapper;

    @Override
    public List<PreventSafety> getPreventAll(Long communityId) {
        return preventMapper.getPreventAll(communityId);
    }

    @Override
    public PreventSafety getPreventById(Long preventId) {
        return preventMapper.getPreventById(preventId);
    }

    @Override
    public List<Prevent> getList(Prevent prevent) {

        return preventMapper.getList(prevent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addPrevent(com.wr.remote.govern.security.vo.PreventVO prevent) {
        //添加防控信息
        prevent.setCreateBy( SecurityUtils.getUsername());
        prevent.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Integer preventResult = preventMapper.addPrevent(prevent);
        return preventResult;
    }

    @Override
    public com.wr.remote.govern.security.vo.PreventVO getPreventDetail(Long preventId) {
        com.wr.remote.govern.security.vo.PreventVO preventDetail = preventMapper.getPreventDetail(preventId);
        return preventDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updatePrevent(com.wr.remote.govern.security.vo.PreventVO prevent) {
        prevent.setUpdateBy(SecurityUtils.getUsername());
        //修改防空信息
        Integer result=preventMapper.updatePrevent(prevent);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer delPrevent(Long preventId) {
        //删除信息
        return preventMapper.delPrevent(preventId);
    }

    /**
     * 添加关联表
     * @param preventId
     * @param communityIds
     */
    public void savePreventRang(Long preventId,Long[] communityIds){
        preventMapper.addPreventRang(preventId,communityIds);
    }

    /**
     * 删除关联表
     * @param preventId
     */
    public void removePreventRang(Long preventId){
        preventMapper.removePreventRang(preventId);
    }

}
