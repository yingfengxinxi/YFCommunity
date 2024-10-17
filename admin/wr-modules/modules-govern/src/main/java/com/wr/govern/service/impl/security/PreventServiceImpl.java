package com.wr.govern.service.impl.security;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.security.PreventMapper;
import com.wr.govern.service.security.PreventService;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:46
 * @Desc: 防控信息业务层
 */
@Service
public class PreventServiceImpl implements PreventService {

    @Autowired
    private PreventMapper preventMapper;

    @Override
    public List<Prevent> getList(Prevent prevent) {
        prevent.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId() == null?prevent.getEstateId():SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return preventMapper.getList(prevent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addPrevent(PreventVO prevent) {
        //添加防控信息
        prevent.setCreateBy(SecurityUtils.getUsername());
        prevent.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        Integer preventResult = preventMapper.addPrevent(prevent);

        //添加关联表
        savePreventRang(prevent.getPreventId(),prevent.getCommunityIds());
        return preventResult;
    }

    @Override
    public PreventVO getPreventDetail(Long preventId) {
        PreventVO preventDetail = preventMapper.getPreventDetail(preventId);
        Long[] ids= preventMapper.getPreventRangInfo(preventDetail.getPreventId());
        preventDetail.setCommunityIds(ids);
        return preventDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updatePrevent(PreventVO prevent) {
        prevent.setUpdateBy(SecurityUtils.getUsername());
        //修改防空信息
        Integer result=preventMapper.updatePrevent(prevent);
        //删除关联表
        removePreventRang(prevent.getPreventId());
        //建立新关系
        savePreventRang(prevent.getPreventId(),prevent.getCommunityIds());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer delPrevent(Long preventId) {
        //删除关联表
        removePreventRang(preventId);
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
