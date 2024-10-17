package com.wr.estate.service.financial;

import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.estate.entity.ParkPayEntity;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkService
 * @History:
 */
public interface ParkService {

    /**
     * 获取ParkPay集合
     *
     * @param parkPayEntity
     * @return
     */
    List<ParkPayEntity> selectParkPay(ParkPayEntity parkPayEntity);


    /**
     * 获取ParkDuration集合
     *
     * @param parkDurationEntity
     * @return
     */
    List<ParkDurationEntity> selectParkDuration(ParkDurationEntity parkDurationEntity);

    /**
     * 获取该小区是否开启停车收费
     *
     * @param communityId
     * @return
     */
    String getSwitchType(Long communityId);

    /**
     * 新增小区计费开关
     *
     * @param parkPayEntity
     * @return
     */
    int addParkPay(ParkPayEntity parkPayEntity);

    /**
     * 更新小区停车计费
     *
     * @param parkPayEntity
     * @return
     */
    int updateParkPay(ParkPayEntity parkPayEntity);

    int addParkDuration(ParkDurationEntity parkDurationEntity);

    int delPd(Long durationId);

    int xgfy(ParkDurationEntity parkDurationEntity);

}
