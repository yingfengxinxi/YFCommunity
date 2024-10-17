package com.wr.estate.service.impl.rescue;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusCommunityCareType;
import com.wr.estate.mapper.rescue.BusCommunityCareTypeMapper;
import com.wr.estate.service.rescue.BusCommunityCareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:48:47
 * @Desc: 社区关怀类型
 */
@Service
public class BusCommunityCareTypeServiceImpl implements BusCommunityCareTypeService {

    @Autowired
    private BusCommunityCareTypeMapper communityCareTypeMapper;

    @Override
    public List<BusCommunityCareType> getList(BusCommunityCareType communityCareType) {
        return communityCareTypeMapper.getList(communityCareType);
    }


    @Override
    public BusCommunityCareType getById(Long id) {
        return communityCareTypeMapper.getById(id);
    }

    @Override
    public void insert(BusCommunityCareType communityCareType) {
        communityCareType.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        communityCareTypeMapper.insert(communityCareType);
    }

    @Override
    public void update(BusCommunityCareType communityCareType) {
        communityCareType.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        communityCareTypeMapper.update(communityCareType);
    }

    @Override
    public void delete(Long id) {
        communityCareTypeMapper.delete(id);
    }

    /**
     *
     * @param name
     * @param id
     * @return
     */
    @Override
    public Long getByNameId(String name,Long id) {
        return communityCareTypeMapper.getByNameId(name,id);
    }
}
