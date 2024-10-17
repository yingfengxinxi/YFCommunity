package com.wr.api.owner.mapper.home.location;

import com.wr.api.owner.entity.home.location.ApiHomeOrder;
import com.wr.api.owner.entity.home.location.ApiHomeRepair;
import com.wr.api.owner.entity.vo.location.ApiHomeOrderVo;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.distribution.Distribution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 居家维修工单表
 *
 * @Author: lvzy
 * @Date: 2022-12-01 14:40:35
 * @Desc:居家维修工单表
 */
public interface ApiHomeOrderMapper {

    int deleteByPrimaryKey(Long orderId);

    int insertSelective(ApiHomeOrder record);

    ApiHomeOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(ApiHomeOrder record);

    /**
     * 分页查询
     *
     * @param apiHomeOrderVo
     * @return
     */
    List<ApiHomeOrderVo> listPage(ApiHomeOrderVo apiHomeOrderVo);

    /**
     * 详情
     *
     * @param orderId
     * @return
     */
    ApiHomeOrderVo detail(Long orderId);

    /**
     * 根据物业ID查维修类型
     *
     * @param estateId
     * @return
     */
    List<ApiHomeRepair> selectRepairList(Long estateId);

    /**
     * 获取工单分配方式
     * @param communityId 小区id
     * @param allotType 工单类型
     * @return: com.wr.remote.work.distribution.Distribution
     * @date: 2023/1/10 17:34
     * @author: SJiang
     **/
    Distribution getAllotByType(@Param("communityId") Long communityId, @Param("allotType") String allotType);

    /**
     * 查询工单空闲人员
     *
     * @param allotId     工单分配id
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @author yx
     * @date 2022/11/3 13:20
     */
    List<Staff> listLeisureStaffs(@Param("allotId") Long allotId,
                                  @Param("communityId") Long communityId);

}
