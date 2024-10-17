package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.application.info.CarportVo;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomInfoVo;
import com.wr.api.estate.entity.vo.application.serve.OwnerInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 数据层
 */
public interface OwnerMapper {
    /**
     * 列表
     *
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getList(OwnerVo ownerVo);

    /**
     * 表单详情
     *
     * @param ownerId
     * @return
     */
    OwnerInfoVo getOwnerDetail(@Param("ownerId") Long ownerId);

    /**
     * 业主房屋信息
     *
     * @param ownerId
     * @param communityId
     * @return
     */
    List<RoomInfoVo> getHouse(@Param("ownerId") Long ownerId, @Param("communityId") Long communityId);

    /**
     * 成员个数
     *
     * @param ownerVo
     * @return
     */
    int getOwnerNum(OwnerVo ownerVo);

    /**
     * 租客个数
     *
     * @param ownerVo
     * @return
     */
    int getTenantNum(OwnerVo ownerVo);

    /**
     * 车辆信息
     *
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getCar(OwnerVo ownerVo);

    /**
     * 车位信息
     *
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getCarport(OwnerVo ownerVo);


    /**
     * 业主总数
     *
     * @param communityId
     * @return
     */
    int getOwnerCount(Long communityId);

}
