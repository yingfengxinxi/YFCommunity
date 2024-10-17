package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.api.estate.entity.vo.application.serve.MeterRecordVO;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.manage.contract.Owner;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 16:35:18
 * @Desc: 业务层
 */
public interface MeterRecordService {

    /**
     * 列表
     * @param meterRecord
     * @return
     */
    List<MeterRecord> selectMeterCord(MeterRecord meterRecord);

    /**
     * 插入
     * @param meterRecord
     * @return
     */
    int insertCord(MeterRecord meterRecord);

    /**
     * 获取抄表信息详情
     * @param recordId
     * @return
     */
    MeterRecordVO getMeterRecordDetail(Long recordId);

    /**
     * 获取业主信息
     * @param ownerId
     * @return
     */
    MeterRecordVO getOwnerDetail(Long ownerId);


    /**
     * 获取楼栋集合
     * @param communityId 小区id
     * @return
     */
    List<Building> getBuilderList(Long communityId);
}
