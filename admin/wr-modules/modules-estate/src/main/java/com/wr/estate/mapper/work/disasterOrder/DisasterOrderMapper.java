package com.wr.estate.mapper.work.disasterOrder;

import com.wr.remote.work.vo.BusDisasterOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-01 15:51:10
 * @Desc:
 */
@Mapper
public interface DisasterOrderMapper {

    /**
     * 查询灾害报事工单列表
     * @param busDisasterOrderVo
     * @return
     */
    List<BusDisasterOrderVo> getList(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 灾害报事执行人详情
     * @param busDisasterOrderVo
     * @return
     */
    List<BusDisasterOrderVo> getInfoList(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 添加
     * @param busDisasterOrderVo
     * @return
     */
    int insertOrder(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 修改
     * @param busDisasterOrderVo
     * @return
     */
    int updateOrder(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 确定分配
     * @param busDisasterOrderVo
     * @return
     */
    int updateDisasterOrder(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 获取执行人
     * @param communityId
     * @return
     */
    List<BusDisasterOrderVo> getNameList(@Param("communityId") Long communityId,@Param("orderType") String orderType);

    /**
     * 根据id删除
     * @param orderId
     * @return
     */
    int deleteById(Long orderId);

}
