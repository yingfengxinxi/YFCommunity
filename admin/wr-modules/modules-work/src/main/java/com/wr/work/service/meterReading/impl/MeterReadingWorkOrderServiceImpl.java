package com.wr.work.service.meterReading.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.remote.domain.MeterReadingEquipment;
import com.wr.remote.domain.MeterReadingRecordWorkOrder;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.meter.RecordVo;
import com.wr.work.mapper.meterReading.MeterReadingWorkOrderMapper;
import com.wr.work.service.meterReading.MeterReadingWorkOrderService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/3/5 12:29
 */
@Service
public class MeterReadingWorkOrderServiceImpl extends ServiceImpl<MeterReadingWorkOrderMapper, MeterReadingWorkOrder> implements MeterReadingWorkOrderService {

}
