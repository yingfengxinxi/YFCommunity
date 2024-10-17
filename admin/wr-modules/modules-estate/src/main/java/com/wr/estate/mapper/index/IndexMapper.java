package com.wr.estate.mapper.index;

import com.wr.estate.entity.vo.OrderNumVO;
import com.wr.remote.domain.vo.GetFoundDataVo;
import com.wr.remote.domain.Community;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author DIANWEI
 * 2022-12-01
 * 首页 mapper层
 */
public interface IndexMapper {

    /**
     * estateId 物业id
     * 审核数量 房产审核 车辆审核 业主审核 租客审核
     *
     * @return Map
     */
    public Map<String, Object> getAuditNum(Long estateId);

    /**
     * 根据物业ID 查询社区列表
     *
     * @param estateId 物业id
     * @return 返回社区ID列表
     */
    public List<Community> getCommunityIdList(Long estateId);

    /**
     * @param communityId   小区id
     * @param eventTypeList 表名
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 个数
     */
    public BigDecimal getUserNumList(@Param("communityId") Long communityId, @Param("eventTypeList") List<String> eventTypeList, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 每日车行
     *
     * @param communityId 小区id
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 个数
     */
    public BigDecimal getVehicleNumList(@Param("communityId") Long communityId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 能耗统计
     *
     * @param meterType   表类型：0=水表，1=电表，2=燃气表
     * @param communityId 社区id
     * @param meterTime   时间
     * @return 0=水表，1=电表，2=燃气表
     */
    public BigDecimal getEnergyData(@Param("meterType") Long meterType, @Param("communityId") Long communityId, @Param("meterTime") String meterTime);

    /**
     * 查询房屋状态
     *
     * @param communityId 社区id
     * @return 查询房屋状态
     */
    public Map<String, Object> getRoomStatusData(Long communityId);

    /**
     * 获取工单数量
     *
     * @param estateId 物业id
     * @return 获取工单数量
     */
    public List<OrderNumVO> getOrderList(Long estateId);

    /**
     * 查询社区公告
     *
     * @param estateId 物业id
     * @return 查询社区公告
     */
    public List<Map<String, Object>> getNoticeList(Long estateId);

    /**
     * 查询基金投票金额
     *
     * @param communityId 社区id
     * @return 查询基金投票金额
     */
    public List<GetFoundDataVo> getFoundData(@Param("communityId") Long communityId);
}
