package com.wr.estate.mapper.center.audit;


import java.util.List;

import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.BusOwnerExample;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.remote.estate.center.vo.RoomSearchOwnerVO;
import org.apache.ibatis.annotations.Param;

public interface BusOwnerMapper {
    int countByExample(BusOwnerExample example);

    int deleteByExample(BusOwnerExample example);

    int deleteByPrimaryKey(Long ownerId);

    int insert(BusOwner record);

    int insertSelective(BusOwner record);

    List<BusOwner> selectByExample(BusOwnerExample example);

    BusOwner selectByPrimaryKey(Long ownerId);

    int updateByExampleSelective(@Param("record") BusOwner record, @Param("example") BusOwnerExample example);

    int updateByExample(@Param("record") BusOwner record, @Param("example") BusOwnerExample example);

    int updateByPrimaryKeySelective(BusOwner record);

    int updateByPrimaryKey(BusOwner record);

    List<OwneAuditVo> getListOwnerAudit(BusOwner record);

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
     * 尾删除数据
     *
     * @param ownerId
     * @param updateBy
     * @return
     */
    int deleteByPrimaryKeys(@Param("ownerId") Long ownerId, @Param("updateBy") String updateBy);

    /**
     * 查询单个对象
     *
     * @param ownerId
     * @param roomId
     * @return
     */
    OwneAuditVo selectoneOwneAuditVo(@Param("ownerId") Long ownerId, @Param("roomId") Long roomId);

    /**
     * 驳回或审核通过
     *
     * @param ownerId
     * @param auditStatus
     * @param rejectReason
     * @param updateBy
     * @return
     */
    int updataByownerIdAndAuditStatus(@Param("ownerId") Long ownerId, @Param("auditStatus") String auditStatus, @Param("rejectReason") String rejectReason, @Param("updateBy") String updateBy);

    /**
     * 修改用户是否是业主
     *
     * @param accountId
     * @param updateBy
     * @return
     */
    int updateaccountType(@Param("accountId") Long accountId, @Param("updateBy") String updateBy);

    /**
     *
     * @param ownerId
     * @return
     */
    Integer getByOwnerIdCount(@Param("ownerId") Long ownerId);
}
