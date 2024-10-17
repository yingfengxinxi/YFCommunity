package com.wr.estate.mapper.center.audit;

import java.util.List;

import com.wr.estate.entity.BusHouse;
import com.wr.estate.entity.BusHouseExample;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import org.apache.ibatis.annotations.Param;

public interface BusHouseMapper {
    int countByExample(BusHouseExample example);

    int deleteByExample(BusHouseExample example);

    int deleteByPrimaryKey(Long houseId);

    Integer getRoomIdCount(@Param("roomId") Long roomId);

    int insert(BusHouse record);

    int insertSelective(BusHouse record);

    List<BusHouse> selectByExample(BusHouseExample example);

    BusHouse selectByPrimaryKey(Long houseId);

    void deleteHouse(BusHouse busHouse);

    void deleteByOwnerIdHouse(@Param("ownerId") Long ownerId);

    /**
     * @param ownerId
     * @return
     */
    Integer getByOwnerIdHouseCount(@Param("ownerId") Long ownerId);

    Long getByRoomIdOwnerId(@Param("roomId") Long roomId);

    int updateByExampleSelective(@Param("record") BusHouse record, @Param("example") BusHouseExample example);

    int updateByExample(@Param("record") BusHouse record, @Param("example") BusHouseExample example);

    int updateByPrimaryKeySelective(BusHouse record);

    int updateByPrimaryKey(BusHouse record);

    /**
     *
     * @param record
     * @return
     */
    List<OwneAuditVo> selectListOwnerAudit(BusOwner record);

    int deleteByPrimaryKeys(@Param("houseId") Long houseId, @Param("updateBy") String updateBy);

    OwneAuditVo selectoneOwneAuditVo(@Param("houseId") Long houseId, @Param("estateId") Long estateId);

    int updataByownerIdAndAuditStatus(@Param("houseId") Long houseId, @Param("auditStatus") String auditStatus, @Param("rejectReason") String rejectReason, @Param("updateBy") String updateBy);

    /**
     * @param ownerId
     * @param roomId
     * @param auditStatus
     * @param rejectReason
     * @param updateBy
     * @return
     */
    int updataByOwnerIdAuditStatus(@Param("roomId") Long roomId, @Param("ownerId") Long ownerId, @Param("auditStatus") String auditStatus, @Param("rejectReason") String rejectReason, @Param("updateBy") String updateBy);

    /**
     * 查看房间是否存在
     *
     * @param roomId
     * @return: java.lang.Integer
     * @date: 2023/6/5 16:58
     * @author: SJiang
     **/
    Integer selectHouseByRoomId(Long roomId);
}