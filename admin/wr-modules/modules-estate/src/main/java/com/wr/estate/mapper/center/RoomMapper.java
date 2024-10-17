package com.wr.estate.mapper.center;

import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.vo.*;
import com.wr.remote.estate.vote.BusVoteScope;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 19:55:38
 * @Desc: 房屋dao
 */
public interface RoomMapper {

    /**
     * 根据当前登录人所在物业查询小区
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    List<RoomVO> listRooms(RoomQueryVO query);

    /**
     * @param query
     * @return
     */
    List<BusVoteScope> getVoteListRooms(RoomQueryVO query);

    /**
     * 根据单元id查询所有房子
     *
     * @param unitId 单元id
     * @return java.util.List<com.wr.remote.business.center.Room>
     * @author yx
     * @date 2022/10/13 13:36
     */
    List<Room> listByUnitId(Long unitId);

    /**
     * 根据楼栋id查询所有房子
     *
     * @param buildingId 楼栋id
     * @return java.util.List<com.wr.remote.business.center.Room>
     * @author yx
     * @date 2022/10/13 14:33
     */
    List<Room> listByBuildingId(Long buildingId);

    /**
     * @param buildingId
     * @return
     */
    List<Room> getByBuildingIdRoomList(@Param("buildingId") Long buildingId);

    /**
     *
     * @param buildingId
     * @return
     */
    List<Room> getByBuildingIdNullRoomList(@Param("buildingId") Long buildingId);

    /**
     * 根据条件查询所有楼层
     *
     * @param query 查询条件
     * @return java.util.List<java.lang.Integer>
     * @author yx
     * @date 2022/10/13 16:54
     */
    List<Integer> listFloorsByBuildingId(RoomQueryVO query);

    /**
     * 批量新增房间
     *
     * @param list 批量数据
     * @author yx
     * @date 2022/10/12 17:55
     */
    void insertBatch(List<Room> list);

    void insert(Room room);

    void update(Room room);

    /**
     * 根据楼栋ids查询所有房号
     *
     * @param query 查询条件
     * @return java.util.List<java.lang.String>
     * @author yx
     * @date 2022/10/13 19:46
     */
    List<String> listRoomNamesByCondition(RoomQueryVO query);

    /**
     * 根据条件查询所有房号
     *
     * @param query 查询条件
     * @return java.util.List<java.lang.String>
     * @author yx
     * @date 2022/10/14 14:36
     */
    List<String> listRoomNamesByBuildId(RoomQueryVO query);

    /**
     * 根据条件查询所有房屋信息
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.estate.entity.vo.RoomDetailVO>
     * @author yx
     * @date 2022/10/14 14:44
     */
    List<RoomDetailVO> roomOtherDetailByBuildingId(RoomQueryVO query);

    /**
     * 过户
     *
     * @param roomId     房间id
     * @param ownerId    新业主id
     * @param licenseUrl 产权证书
     * @author yx
     * @date 2022/10/17 9:40
     */
    void transferOwnership(@Param("roomId") Long roomId,
                           @Param("ownerId") Long ownerId,
                           @Param("licenseUrl") String licenseUrl);

    /**
     * 过户房产
     *
     * @param roomId     房间id
     * @param ownerId    新业主id
     * @param licenseUrl 产权证书
     * @author yx
     * @date 2023/1/4 9:58
     */
    int transferHouse(@Param("roomId") Long roomId,
                      @Param("ownerId") Long ownerId,
                      @Param("licenseUrl") String licenseUrl);

    /**
     * 批量修改房屋
     *
     * @param updateVO 批量数据
     * @author yx
     * @date 2022/10/13 21:11
     */
    void updateRoomBatch(RoomUpdateBatchVO updateVO);

    /**
     * 修改房屋
     *
     * @param updateVO 修改数据
     * @author yx
     * @date 2022/10/14 10:25
     */
    void updateRoom(RoomUpdateVO updateVO);

    /**
     * @param ownerId
     * @param roomId
     */
    void updateByRoomIdOwnerId(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * 根据id批量删除房屋
     *
     * @param ids 房屋ids
     * @author yx
     * @date 2022/10/13 21:40
     */
    void deleteRoomBatch(List<Long> ids);

    /**
     * 根据房屋id查询房屋是否有产权房屋
     *
     * @param ids
     * @return java.lang.Integer
     * @author yx
     * @date 2023/2/25 9:41
     */
    Integer hasLicense(List<Long> ids);

    /**
     * 根据id查询房屋详情
     *
     * @param roomId
     * @param ownerId
     * @return
     */
    RoomDetailVO getDetailById(@Param("roomId") Long roomId, @Param("ownerId") Long ownerId);

    /**
     * @param roomId
     * @return
     */
    RoomDetailVO getByRoomIdBuildingUnit(@Param("roomId") Long roomId);

    /**
     * @param communityId
     * @param buildingId
     * @param unitId
     * @param roomName
     * @return
     */
    Long getRoomNameId(@Param("communityId") Long communityId, @Param("buildingId") Long buildingId, @Param("unitId") Long unitId, @Param("roomName") String roomName);

    /**
     * 根据楼栋id获取最大楼层
     *
     * @param buildingId 楼栋id
     * @return java.lang.Integer
     * @author yx
     * @date 2022/10/14 16:40
     */
    Integer maxFloorByBuildingId(Long buildingId);

    /**
     * 查询业主信息以及家庭成员
     *
     * @param roomId 房屋id
     * @return java.util.List<com.wr.estate.entity.vo.RoomSearchOwnerVO>
     * @author yx
     * @date 2022/10/17 17:23
     */
    List<RoomSearchOwnerVO> roomFamilyList(@Param("roomId") Long roomId);

    /**
     * @param roomId
     * @param roomStatus
     */
    void updateByRoomIdRoomStatus(@Param("roomId") Long roomId, @Param("roomStatus") String roomStatus);

    Integer getByRoomIdRoomStatusCount(@Param("roomId") Long roomId);

    /**
     * @param roomId
     * @return
     */
    BigDecimal getByRoomIdArea(@Param("roomId") Long roomId);

    /**
     * @param roomId
     * @return
     */
    String getByRoomIdRoomStatus(@Param("roomId") Long roomId);

    /**
     * @param roomId
     * @return
     */
    Long getRoomIdOwnerId(@Param("roomId") Long roomId);

    /**
     *
     * @param roomId
     * @return
     */
    String getByRoomIdDeliveryTime(@Param("roomId") Long roomId);

    /**
     *
     * @param ownerId
     * @return
     */
    Long getByOwnerIdRoomId(@Param("ownerId") Long ownerId);
}

