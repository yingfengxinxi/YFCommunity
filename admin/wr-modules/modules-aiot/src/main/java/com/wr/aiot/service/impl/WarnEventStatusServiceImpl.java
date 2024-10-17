package com.wr.aiot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.aiot.entity.WarnEventStatus;
import com.wr.aiot.mapper.WarnEventStatusMapper;
import com.wr.aiot.service.WarnEventStatusService;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/4/23 14:27
 */
@Service
public class WarnEventStatusServiceImpl extends ServiceImpl<WarnEventStatusMapper, WarnEventStatus> implements WarnEventStatusService {
}
