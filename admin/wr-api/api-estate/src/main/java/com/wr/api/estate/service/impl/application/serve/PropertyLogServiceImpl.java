package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.application.serve.PropertyLog;
import com.wr.api.estate.entity.vo.application.serve.PropertyLogListVo;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.api.estate.mapper.application.serve.PropertyLogMapper;
import com.wr.api.estate.service.application.serve.PropertyLogService;
import com.wr.remote.estate.manage.contract.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 15:35:30
 * @Desc: 物业缴费业务层
 */
@Service
public class PropertyLogServiceImpl implements PropertyLogService {

    @Autowired
    private PropertyLogMapper propertyLogMapper;


    @Override
    public List<PropertyLog> selectPropertyLogList(Long ownerId) {
        return propertyLogMapper.selectPropertyLogList(ownerId);
    }

    @Override
    public List<Owner> selectOwnerList(Owner owner) {
        return propertyLogMapper.selectOwnerList(owner);
    }

    /**
     *
     * @param propertyLogListVo
     * @return
     */
    @Override
    public List<PropertyLogListVo> getPropertyList(PropertyLogListVo propertyLogListVo) {
        return propertyLogMapper.getPropertyList(propertyLogListVo);
    }

    /**
     *
     * @param logId
     * @return
     */
    @Override
    public PropertyLogListVo getByLogIdInfo(Long logId) {
        return propertyLogMapper.getByLogIdInfo(logId);
    }
}
