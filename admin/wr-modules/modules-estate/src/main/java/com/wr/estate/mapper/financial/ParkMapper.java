package com.wr.estate.mapper.financial;

import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.estate.entity.ParkPayEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkMapper
 * @History:
 */
@Repository
public interface ParkMapper {

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
     * 验证小区是否添加收费规则
     *
     * @param communityId
     * @return
     */
    int getParkCountByCommunityId(Long communityId);

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

    int parkDurationCount(Long communityId);

    int addParkDuration(ParkDurationEntity parkDurationEntity);

    List<ParkDurationEntity> getpd(Long communityId);

    int delpd(Long communityId);

    int addParkDuration1(List<ParkDurationEntity> list);

    Long getcid(Long durationId);

    ParkDurationEntity getMax(Long communityId);

    int xgfy(ParkDurationEntity parkDurationEntity);

}
