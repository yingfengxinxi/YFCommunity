package com.wr.estate.service.impl.work.homeorder;

import com.wr.common.customize.constant.OrderConstants;
import com.wr.estate.entity.HomeOrder;
import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeOrderVO;
import com.wr.estate.mapper.work.homeorder.HomeOrderMapper;
import com.wr.estate.mapper.work.patrol.PatrolMapper;
import com.wr.estate.service.work.homeorder.HomeOrderService;
import com.wr.remote.work.patrol.vo.AssistStaffListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-05 17:50:35
 * @Desc: 居家维修工单 业务实现
 */
@Service
@Slf4j
public class HomeOrderServiceImpl implements HomeOrderService {
    @Autowired
    private HomeOrderMapper homeOrderMapper;
    @Autowired
    private PatrolMapper patrolMapper;

    @Override
    public List<HomeOrderVO> getHomeOrderAll(HomeOrderVO homeOrder) {
        List<HomeOrderVO> homeOrderAll = homeOrderMapper.getHomeOrderAll(homeOrder);
        return homeOrderAll;
    }

    @Override
    public HomeOrderVO getHomeOrderById(Long orderId) {
        HomeOrderVO homeOrderById = homeOrderMapper.getHomeOrderById(orderId);
        // 获取异常
        String abnormal = homeOrderMapper.getAbnormal(OrderConstants.HOME_ORDER, orderId);
        homeOrderById.setAbnormalInfo(abnormal);
        // 获取工单图片
        List<String> photos = homeOrderMapper.getPhoto("0", orderId);
        homeOrderById.setPhotos(photos);
        return homeOrderById;
    }

    @Override
    public Integer distribution(HomeOrder homeOrder) {
        return homeOrderMapper.distribution(homeOrder);
    }

    @Override
    public List<HomeRepair> getHomeRepair(Long estateId) {
        return homeOrderMapper.getHomeRepair(estateId);
    }
}
