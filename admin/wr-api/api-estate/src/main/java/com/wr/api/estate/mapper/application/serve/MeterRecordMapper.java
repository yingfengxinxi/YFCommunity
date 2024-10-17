package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.api.estate.entity.vo.application.serve.MeterRecordVO;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.manage.contract.Owner;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 15:48:18
 * @Desc: 数据层
 */
public interface MeterRecordMapper {

    /**
     * 列表
     *
     * @param meterRecord
     * @return
     */
    List<MeterRecord> selectMeterCord(MeterRecord meterRecord);


    /**
     * 插入
     *
     * @param meterRecord
     * @return
     */
    int insertCord(MeterRecord meterRecord);

    /**
     * 查看抄表详情
     * @param recordId
     * @return
     */
    MeterRecordVO getMeterRecordDetail(Long recordId);

    /**
     * 获取业主详情
     * @param ownerId
     * @return
     */
    MeterRecordVO getOwnerDetail(Long ownerId);

    /**
     * 获取上次抄表数
     * @param meterRecord
     * @return
     */
    BigDecimal getLastMeterRecord(MeterRecord meterRecord);

    /**
     * 获取楼栋集合
     * @param communityId
     * @return
     */
    List<Building> getBuilderList(Long communityId);
}
