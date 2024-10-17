package com.wr.estate.service.impl.base;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.base.MainTainMapper;
import com.wr.estate.mapper.info.HeadlineMapper;
import com.wr.estate.service.base.MainTainService;
import com.wr.remote.estate.base.BusRepairWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 维修工种管理业务实现
 * 
 * @author WuKong
 * @date 2022-10-11
 */
@Service
public class MainTainServiceImpl implements MainTainService {

    @Autowired
    private MainTainMapper mainTainMapper;

    @Override
    public BusRepairWork selectBusRepairWorkId(Long workId) {
        return mainTainMapper.selectBusRepairWorkId(workId);
    }

    @Override
    public List<BusRepairWork> selectBusRepairWorkList(BusRepairWork busRepairWork) {
        busRepairWork.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return mainTainMapper.selectBusRepairWorkList(busRepairWork);
    }

    @Override
    public int insertBusRepairWork(BusRepairWork busRepairWork) {
        busRepairWork.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        busRepairWork.setCreateBy(SecurityUtils.getUsername());
        busRepairWork.setUpdateBy(SecurityUtils.getUsername());
        busRepairWork.setCreateTime(DateUtils.getNowDate());
        busRepairWork.setUpdateTime(DateUtils.getNowDate());
        return mainTainMapper.insertBusRepairWork(busRepairWork);
    }

    @Override
    public int updateBusRepairWork(BusRepairWork busRepairWork) {
        busRepairWork.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        busRepairWork.setUpdateBy(SecurityUtils.getUsername());
        busRepairWork.setUpdateTime(DateUtils.getNowDate());
        return mainTainMapper.updateBusRepairWork(busRepairWork);
    }

    @Override
    public int deleteBusRepairWorkId(Long workId) {
        return mainTainMapper.deleteBusRepairWorkId(workId);
    }

    @Override
    public int deleteBusRepairWorkIds(Long[] workIds) {


        return mainTainMapper.deleteBusRepairWorkIds(workIds);
    }
}
