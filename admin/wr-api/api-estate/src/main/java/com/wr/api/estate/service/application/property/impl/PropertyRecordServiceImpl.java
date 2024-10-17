package com.wr.api.estate.service.application.property.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.property.PropertyRecordMapper;
import com.wr.api.estate.service.application.property.PropertyRecordService;
import com.wr.remote.estate.property.PropertyRecord;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/17 13:24
 */
@Service
public class PropertyRecordServiceImpl extends ServiceImpl<PropertyRecordMapper, PropertyRecord> implements PropertyRecordService {
}
