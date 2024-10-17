package com.wr.estate.service.center;

import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.RoomImportVo;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.center.vo.GraphVO;
import com.wr.remote.estate.center.vo.*;
import com.wr.remote.estate.vote.BusVoteScope;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:09:07
 * @Desc:
 */
public interface RoomService {

    /**
     * 根据当前登录人所在物业查询小区
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    List<RoomVO> listRooms(RoomQueryVO query);


    /**
     * @param buildingId
     * @return
     */
    List<Room> getByBuildingIdRoomList(Long buildingId);

    /**
     *
     * @param buildingId
     * @return
     */
    List<Room> getByBuildingIdNullRoomList(Long buildingId);

    /**
     *
     * @param query
     * @return
     */
    List<BusVoteScope> getVoteListRooms(RoomQueryVO query);

    /**
     * 批量新增单元和房间
     *
     * @param insertVO 新增参数
     * @author yx
     * @date 2022/10/12 16:07
     */
    void insertUnitAndRoomBatch(UnitAndRoomInsertVO insertVO);

    /**
     * 批量新增房间
     *
     * @param insertVO 房间批量参数
     * @author yx
     * @date 2022/10/13 11:56
     */
    void insertRoomBatch(RoomInsertVO insertVO);

    /**
     * 根据id修改单元
     *
     * @param updateVO 修改信息
     * @author yx
     * @date 2022/10/13 14:01
     */
    void updateUnitById(UnitUpdateVO updateVO);

    /**
     * 删除单元
     *
     * @param unitId 单元id
     * @author yx
     * @date 2022/10/13 14:40
     */
    void deleteUnit(Long unitId);

    /**
     * 根据条件查询所有房号
     *
     * @param query 查询条件
     * @return java.util.List<java.lang.String>
     * @author yx
     * @date 2022/10/13 19:46
     */
    List<String> listRoomNamesByCondition(RoomQueryVO query);

    /**
     * 根据小区id查询单元列表
     *
     * @param communityId 小区id
     * @return java.util.List<com.wr.estate.entity.vo.UnitVO>
     * @author yx
     * @date 2022/10/13 20:33
     */
    List<UnitVO> listUnitsByCommunityId(Long communityId);

    /**
     * 批量更新房屋
     *
     * @param updateVO 批量数据
     * @author yx
     * @date 2022/10/13 21:11
     */
    void updateRoomBatch(RoomUpdateBatchVO updateVO);

    /**
     *
     * @param roomId
     */
    void updateByRoomIdRoomStatus(Long roomId,String roomStatus);

    /**
     * 修改房屋
     *
     * @param updateVO 修改数据
     * @author yx
     * @date 2022/10/14 10:25
     */
    void updateRoom(RoomUpdateVO updateVO);

    /**
     * 根据id批量删除房屋
     *
     * @param ids 房屋ids
     * @author yx
     * @date 2022/10/13 21:40
     */
    void deleteRoomBatch(List<Long> ids);

    /**
     * 根据id查询房屋详情
     *
     * @param id id
     * @return com.wr.estate.entity.vo.RoomDetailVO
     * @author yx
     * @date 2022/10/14 10:11
     */
    RoomDetailVO getDetailById(Long id,Long communityId);

    /**
     * 房屋管理图形数据查询返回
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.estate.entity.vo.RoomDetailVO>
     * @author yx
     * @date 2022/10/14 14:44
     */
    GraphVO roomGraphData(RoomQueryVO query);

    /**
     * 过户查询业主信息
     *
     * @param record 查询条件
     * @return java.util.List<com.wr.estate.entity.vo.RoomSearchOwnerVO>
     * @author yx
     * @date 2022/10/15 13:49
     */
    List<RoomSearchOwnerVO> roomSearchListPage(BusOwner record);

    /**
     * 过户
     *
     * @param roomId  房间id
     * @param ownerId 新业主id
     * @author yx
     * @date 2022/10/17 9:40
     */
    void transferOwnership(Long roomId, Long ownerId, String otherLicenseUrl);

    /**
     * 查询业主信息以及家庭成员
     *
     * @param roomId 房屋id
     * @return java.util.List<com.wr.estate.entity.vo.RoomSearchOwnerVO>
     * @author yx
     * @date 2022/10/17 17:23
     */
    List<RoomSearchOwnerVO> roomFamilyList(Long roomId);

    /**
     * 收房
     *
     * @param roomId
     */
    void confirmRoom(Long roomId);

    /**
     *
     * @param unitId
     * @return
     */
    public Unit getUnitDetailById(Long unitId);
    public UnitInfoVo getByUnitIdDetail(Long unitId);

    String importRoom(List<RoomImportVo> userList, Long communityId);

    /**
     *
     * @param roomId
     * @return
     */
    BigDecimal getByRoomIdArea(Long roomId);

    /**
     *
     * @param roomId
     * @return
     */
    String getByRoomIdRoomStatus(Long roomId);

    Long getRoomIdOwnerId(Long roomId);

    /**
     *
     * @param roomId
     * @return
     */
    String getByRoomIdDeliveryTime(Long roomId);


    /**
     *
     * @param ownerId
     * @return
     */
    Long getByOwnerIdRoomId(Long ownerId);
}
