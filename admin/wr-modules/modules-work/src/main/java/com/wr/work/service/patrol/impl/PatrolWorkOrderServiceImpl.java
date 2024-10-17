package com.wr.work.service.patrol.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.remote.domain.PatrolWorkOrder;
import com.wr.work.mapper.patrol.PatrolWorkOrderMapper;
import com.wr.work.service.patrol.PatrolWorkOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:01
 */
@Service
public class PatrolWorkOrderServiceImpl extends ServiceImpl<PatrolWorkOrderMapper, PatrolWorkOrder> implements PatrolWorkOrderService {

}
