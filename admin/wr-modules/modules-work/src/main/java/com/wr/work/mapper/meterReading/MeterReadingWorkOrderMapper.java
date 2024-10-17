package com.wr.work.mapper.meterReading;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.remote.estate.manage.meter.RecordVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/5 12:28
 */
public interface MeterReadingWorkOrderMapper extends BaseMapper<MeterReadingWorkOrder> {

    /**
     * @param communityId
     * @return
     */
    List<String> getYearList(@Param("communityId") Long communityId);

    /**
     * @param recordVo
     * @return
     */
    List<RecordVo> getMeterReadingCountList(RecordVo recordVo);

    /**
     *
     * @param no
     * @return
     */
    List<RecordVo> getMeterReadingEquipmentCountList(@Param("no") String no);


    BigDecimal getMeterReadingWorkOrderCount(RecordVo recordVo);
}
