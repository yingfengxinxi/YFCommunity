package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.entity.vo.serve.OldBasicInfoVo;
import com.wr.api.owner.entity.vo.serve.OlderFilesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 16:20:47
 * @Desc:
 */
public interface OlderMapper {
    /**
     * 查询是否是业主登录
     * @param accountId
     * @return
     */
    int getAccountType(Long accountId);

    /**
     * 查询老人信息
     * @param ownerPhone
     * @return
     */
    List<OlderFilesVo> getOwner(String ownerPhone);


    /**
     * 查询老人详情
     * @param orderId
     * @return
     */
    OlderFilesVo getOwnerInfoList(Long orderId);

    /**
     * 查看老人信息
     * @param ownerId
     * @return
     */
    OldBasicInfoVo getOlderName(@Param("ownerId") Long ownerId, @Param("cardNo") String cardNo);

    /**
     * 添加老人
     * @param olderFilesVo
     * @return
     */
    int insertOlder(OlderFilesVo olderFilesVo);

    /**
     * 查询老人是否办理过档案
     * @param ownerId
     * @return
     */
    int getoldfilecount(@Param("ownerId") Long ownerId, @Param("cardNo") String cardNo);

    /**
     * 根据业主id查询房产
     * @param ownerId
     * @return
     */
    MyRoomsVo getMyOnwerHouse(Long ownerId);

    /**
     * 查询小区
     * @param accountId
     * @return
     */
    List<MyRoomsVo> getCommunitys(Long accountId);

    /**
     * 查询楼栋
     * @param communityId
     * @return
     */
    List<MyRoomsVo> getBuild(Long communityId);

    /**
     * 查询单元
     * @param buildingId
     * @return
     */
    List<MyRoomsVo> getUnits(Long buildingId);

    /**
     * 查询房间
     * @param unitId
     * @return
     */
    List<MyRoomsVo> getRooms(Long unitId);

    /**
     * 查询老人
     * @param roomId
     * @return
     */
    List<OldBasicInfoVo> getAllOwner(Long roomId);
}
