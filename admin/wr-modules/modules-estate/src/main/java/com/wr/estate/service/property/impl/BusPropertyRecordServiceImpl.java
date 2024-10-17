package com.wr.estate.service.property.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.property.BusPropertyRecordMapper;
import com.wr.estate.service.property.BusPropertyRecordService;
import com.wr.remote.estate.property.PropertyRecord;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/17 13:24
 */
@Service
public class BusPropertyRecordServiceImpl extends ServiceImpl<BusPropertyRecordMapper, PropertyRecord> implements BusPropertyRecordService {
}
