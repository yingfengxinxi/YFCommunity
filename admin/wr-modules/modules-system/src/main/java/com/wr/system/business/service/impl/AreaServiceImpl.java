package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.TreeUtils;
import com.wr.system.business.domain.AreaVo;
import com.wr.system.business.domain.TreeSelectVo;
import com.wr.system.business.mapper.AreaMapper;
import com.wr.system.business.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: RainCity
 * @Date: 2021-06-15 15:13:20
 * @Desc: 地区管理业务处理层
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaVo> selectAreaList(AreaVo area) {
        List<AreaVo> areaList = areaMapper.selectAreaList(area);
        for(AreaVo scArea : areaList){
            String[] ancs = scArea.getAncestors().split(",");
            scArea.setViewFlag("1");
            if(ancs.length<3){
                scArea.setViewFlag("0");
            }
        }
        return areaList;
    }

    @Override
    public String checkAreaNameUnique(AreaVo area) {
        Long areaId = StringUtils.isNull(area.getAreaId()) ? -1L : area.getAreaId();
        AreaVo info = areaMapper.checkAreaNameUnique(area.getAreaName(), area.getParentId());
        if (StringUtils.isNotNull(info) && info.getAreaId().longValue() != areaId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertArea(AreaVo area) {
        AreaVo info = areaMapper.selectAreaById(area.getParentId());
        if(null != info){
            area.setAncestors(info.getAncestors() + "," + area.getParentId());
        }
        return areaMapper.insertArea(area);
    }

    @Override
    public AreaVo selectAreaById(Long areaId) {

        return areaMapper.selectAreaById(areaId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateArea(AreaVo area) {
        AreaVo newParentArea = areaMapper.selectAreaById(area.getParentId());
        AreaVo oldArea = areaMapper.selectAreaById(area.getAreaId());
        if (StringUtils.isNotNull(newParentArea) && StringUtils.isNotNull(oldArea)) {
            String newAncestors = newParentArea.getAncestors() + "," + newParentArea.getAreaId();
            String oldAncestors = oldArea.getAncestors();
            area.setAncestors(newAncestors);
            updateAreaChildren(area.getAreaId(), newAncestors, oldAncestors);
        }
        return areaMapper.updateArea(area);
    }

    /**
     * 修改子元素关系
     * @param areaId 被修改的ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateAreaChildren(Long areaId, String newAncestors, String oldAncestors) {
        List<AreaVo> children = areaMapper.selectChildrenAreaById(areaId);
        for (AreaVo child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            areaMapper.updateAreaChildren(children);
        }
    }

    @Override
    public Boolean hasChildByAreaId(Long areaId) {
        int result = areaMapper.hasChildByAreaId(areaId);
        return result > 0;
    }

    @Override
    public Boolean checkAreaExistCommunity(Long areaId) {
        int result = areaMapper.checkAreaExistCommunity(areaId);
        return result > 0;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteAreaById(Long areaId) {

        return areaMapper.deleteAreaById(areaId);
    }

    @Override
    public List<TreeSelectVo> buildAreaTreeSelectVo(List<AreaVo> areas) {
        List<AreaVo> areaTrees = TreeUtils.buildLongTree(areas);
        return areaTrees.stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }


}
