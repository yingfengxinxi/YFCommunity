package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.application.info.BuildingInfoVo;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomVo;
import com.wr.api.estate.entity.vo.application.info.TenantVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: lzhen
 * @Date: 2022-11-16 11:04:22
 * @Desc: 数据层
 */
public interface RoomMapper {

    /**
     * 楼栋总数
     *
     * @param communityId
     * @return
     */
    int getBuildingCount(Long communityId);

    /**
     * 楼栋列表
     *
     * @param communityId
     * @return
     */
    List<RoomVo> getBuildingList(Long communityId);

    /**
     * 房间列表
     *
     * @param roomVo
     * @return
     */
    List<RoomVo> getRoomList(RoomVo roomVo);

    /**
     * 房主信息
     *
     * @param roomId
     * @return
     */
    OwnerVo getOwner(Long roomId);

    /**
     * 业主信息
     *
     * @param tenantVo
     * @return
     */
    BuildingInfoVo getTenantList(@Param("roomId") Long roomId, @Param("communityId") Long communityId);

    /**
     * 业主信息
     *
     * @param roomId
     * @param communityId
     * @return
     */
    BuildingInfoVo getOwnerInfo(@Param("roomId") Long roomId, @Param("communityId") Long communityId);

    /**
     * 楼宇概况
     *
     * @param roomVo
     * @return
     */
    RoomVo getStatusCount(RoomVo roomVo);

    /**
     * 查询家庭成员
     *
     * @param roomId
     * @return: com.wr.api.estate.entity.vo.application.info.OwnerVo
     * @date: 2023/5/22 14:41
     * @author: SJiang
     **/
    List<Map<String, Object>> getFamilyList(@Param("roomId") Long roomId);

    String getRoomIdRoomStatus(@Param("roomId") Long roomId);

    Date getByRoomIdDeliveryTime(@Param("roomId") Long roomId);

    void updateByRoomIdRoomStatus(@Param("roomId") Long roomId,@Param("roomStatus")  String roomStatus);
}
