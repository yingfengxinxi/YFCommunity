package com.wr.estate.mapper.work.disasterOrder;

import com.wr.remote.work.vo.BusDisasterAuditVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-28 14:04:02
 * @Desc:
 */
@Mapper
public interface DisasterAuditMapper {
    /**
     * 分页查询灾害报事审核
     * @param busDisaster
     * @return
     */
    List<BusDisasterAuditVo> getList(BusDisasterAuditVo busDisaster);


    /**
     * 业主申请审核通过
     * @param id
     * @return
     */
    Integer updateById(@Param("auditId")Long id, @Param("auditName") String auditName);

    /**
     * 驳回
     * @param busDisasterAuditVo
     * @return
     */
    Integer updateByReject(BusDisasterAuditVo busDisasterAuditVo);

    /**
     * 分页查询灾害报事审核员工申请
     * @param busDisasterAuditVo
     * @return
     */
    List<BusDisasterAuditVo> getEmpList(BusDisasterAuditVo busDisasterAuditVo);


    Long getDisasterId(Long auditId);

    /**
     *
     * 工单异常
     * @param map
     * @return
     */
    List<LinkedHashMap<String, Object>> queryError(HashMap<String, Object> map);

    /**
     * 获取多张上传的工单图片
     * @param auditId
     * @return
     */
    List<String> getImageList(Long auditId);
}
