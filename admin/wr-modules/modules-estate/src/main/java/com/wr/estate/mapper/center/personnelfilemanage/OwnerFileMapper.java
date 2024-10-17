package com.wr.estate.mapper.center.personnelfilemanage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.Access;
import com.wr.remote.estate.center.vo.OwnerFileVo;
import com.wr.remote.estate.center.vo.OwnerVo;
import com.wr.remote.estate.manage.contract.Owner;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 17:20:41
 * @Desc: 业主档案
 */
public interface OwnerFileMapper extends BaseMapper<Owner> {
    /**
     * 新增 业主
     *
     * @param params
     * @return
     */
    int insert(Map<String, Object> params);

    Integer insertHouse(Map<String, Object> params);

    /**
     * 房间人数 +1
     *
     * @param roomId
     * @return
     */
    int increaseLive(Long roomId);

    /**
     * @param liveNum
     * @param roomId
     * @return
     */
    void updateByRoomIdLiveNum(@Param("liveNum") Integer liveNum, @Param("roomId") Long roomId);

    /**
     * 房间人数 -1
     *
     * @param roomId
     * @return
     */
    int decreaseLive(Long roomId);

    /**
     * 获取业主 本人 id
     *
     * @param
     * @return
     */
    int getOwnerId(Long id);

    /**
     * 修改 业主
     *
     * @param params
     * @return
     */
    int update(Map<String, Object> params);

    /**
     * 修改房间状态
     *
     * @param
     * @return
     */
    int updateRoomState(Map<String, Object> params);

    Owner getOwnerInfo(Owner owner);

    /**
     * 修改房间信息
     *
     * @param params
     * @return
     */
    int updateRoom(Map<String, Object> params);

    /**
     * @param ownerId
     * @param updateBy
     * @return
     */
    int updateByRoomIdOwerId(@Param("ownerId") Long ownerId, @Param("updateBy") String updateBy, @Param("roomId") Long roomId);

    /**
     * 修改房间信息s
     *
     * @param params
     * @return
     */
    int updateRooms(Map<String, Object> params);

    /**
     * 获取删除业主的 账号 房间
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> getRoomIdAndAccountId(Long[] id);

    /**
     * 删除 业主多个
     *
     * @param id
     * @return
     */
    int deleteds(Long[] id);

    /**
     * 删除 业主单个
     *
     * @param id
     * @return
     */
    int deleted(Long id);

    /**
     * 小区下的业主
     *
     * @param params
     * @return
     */
    List<OwnerFileVo> queryByCommunityId(Map<String, Object> params);

    /**
     * @param roomId
     * @param ownerId
     * @return
     */
    Integer getByRoomIdOwnerRelationCount(@Param("roomId") Long roomId, @Param("ownerId") Long ownerId);

    /**
     * 查询 房间下的人员
     *
     * @param
     * @return
     */
    List<LinkedHashMap<String, Object>> queryByRoomId(Map<String, Object> params);

    /**
     * 业主房产
     *
     * @param
     * @return
     */
    List<LinkedHashMap<String, Object>> queryRoomByOwnerId(Long id);

    /**
     * 业主车辆
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryCarByOwnerId(Long id);

    /**
     * 查询账号下的 车辆
     *
     * @param id
     * @return
     */
    String[] queryCarByAccountId(Long id);

    /**
     * 业主的租客
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryTenantByOwnerId(Long id);

    /**
     * 业主的车位
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryCarPortByOwnerId(Long id);

    /**
     * 存在相同手机号
     *
     * @param no
     * @return
     */
    int getPhoneNum(String no);

    /**
     * 存在相同身份证号
     *
     * @param no
     * @return
     */
    int getCardNoNum(String no);

    /**
     * 查询字典方法
     *
     * @return
     */
    List<LinkedHashMap<String, Object>> getDictData();

    /**
     * 根据小区id查询门禁设备
     *
     * @param communityId 小区id
     * @return {@link List< Access>}
     * @ver v1.0.0
     */
    List<Access> selectAccessList(Long communityId);

    /**
     * 根据id查询业主
     *
     * @param ownerId
     * @return {@link Owner}
     * @ver v1.0.0
     */
    Owner selectOwnerById(Long ownerId);

    /**
     * 房间人数-1
     *
     * @param roomIds 房间id
     * @return {@link Integer}
     * @ver v1.0.0
     */
    Integer reduceLiveNumOne(List<Long> roomIds);

    /**
     * @param roomId
     * @return
     */
    List<OwnerVo> getByRoomIdOwnerInfo(@Param("roomId") Long roomId);

    /**
     * @param ownerId
     * @param communityId
     * @return
     */
    List<OwnerVo> getByOwnerIdOwnerInfo(@Param("ownerId") Long ownerId, @Param("communityId") Long communityId);

    /**
     * 查询所有业主【投票人员下拉】
     *
     * @param communityId
     * @param voteScopeId
     * @return
     */
    List<OwnerFileVo> getAllOwnerList(@Param("communityId") Long communityId, @Param("voteScopeId") String voteScopeId);

    /**
     * @param ownerPhone
     * @return
     */
    OwnerFileVo getOwnerPhoneInfo(@Param("ownerPhone") String ownerPhone);

}
