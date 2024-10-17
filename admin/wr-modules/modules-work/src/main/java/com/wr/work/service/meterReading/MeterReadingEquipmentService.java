package com.wr.work.service.meterReading;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.MeterReadingEquipment;

import java.text.ParseException;

/**
 * @Author lvzy
 * @Date 2024/3/4 11:24
 */
public interface MeterReadingEquipmentService extends IService<MeterReadingEquipment> {



    public void saveRedisData(MeterReadingEquipment meterReadingEquipment, String flag) throws ParseException;
}
