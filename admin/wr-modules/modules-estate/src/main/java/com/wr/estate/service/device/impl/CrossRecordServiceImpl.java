package com.wr.estate.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.device.CrossRecordMapper;
import com.wr.estate.service.device.CrossRecordService;
import com.wr.remote.aiot.CrossRecord;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/4/10 8:53
 */
@Service
public class CrossRecordServiceImpl extends ServiceImpl<CrossRecordMapper, CrossRecord> implements CrossRecordService {
}
