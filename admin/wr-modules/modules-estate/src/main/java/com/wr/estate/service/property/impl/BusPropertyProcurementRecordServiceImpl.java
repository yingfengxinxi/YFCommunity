package com.wr.estate.service.property.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.property.BusPropertyProcurementRecordMapper;
import com.wr.estate.service.property.BusPropertyProcurementRecordService;
import com.wr.remote.estate.property.PropertyProcurementRecord;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/21 18:19
 */
@Service
public class BusPropertyProcurementRecordServiceImpl extends ServiceImpl<BusPropertyProcurementRecordMapper, PropertyProcurementRecord> implements BusPropertyProcurementRecordService {
}
