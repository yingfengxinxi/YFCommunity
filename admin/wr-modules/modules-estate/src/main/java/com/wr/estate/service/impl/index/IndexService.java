package com.wr.estate.service.impl.index;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.vo.OrderNumVO;
import com.wr.remote.domain.Community;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DIAN WEI
 * 首页service层
 * 2022-12-01
 *
 * @author DIANWEI
 */
public interface IndexService {

    /**
     * estateId 物业id
     * 审核数量 房产审核 车辆审核 业主审核 租客审核
     *
     * @return 审核数量
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
     * 每日人行/车行
     *
     * @param estateId 物业id
     * @return 每日人行/车行
     */
    public Map<String, List<BigDecimal>> getLogList(Long communityId);

    /**
     * 能耗统计
     *
     * @param meterType   表类型：0=水表，1=电表，2=燃气表
     * @param communityId 社区id
     * @param meterTime   时间
     * @return 0=水表，1=电表，2=燃气表
     */
    public Map<String, List<BigDecimal>> getEnergyData(Long meterType, Long communityId);

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
    public AjaxResult getFoundData(Long communityId);
}
