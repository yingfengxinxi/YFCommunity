package com.wr.estate.mapper.work.homeorder;

import com.wr.estate.entity.HomeRepair;
import com.wr.estate.entity.vo.HomeRepairVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-04 20:22:49
 * @Desc: 居家维修类型 mapper
 */
public interface HomeRepairMapper {

    /**
     * 获取居家维修类型
     * @param homeRepair
     * @return: java.util.List<com.wr.estate.entity.vo.HomeRepairVO>
     */
    List<HomeRepairVO> getHomeRepairAll(HomeRepair homeRepair);

    /**
     * 获取详情信息
     * @param repairId
     * @return: com.wr.estate.entity.vo.HomeRepairVO
     */
    HomeRepairVO getHomeRepairById(@Param("repairId") Long repairId);

    /**
     * 查看名称是否唯一
     * @param homeRepair
     * @return: com.wr.estate.entity.HomeRepair
     */
    HomeRepair checkNameUnique(HomeRepair homeRepair);

    /**
     * 添加类型
     * @param homeRepair
     * @return: java.lang.Integer
     */
   Integer addHomeRepair(HomeRepair homeRepair);

   /**
    * 修改类型
    * @param homeRepair
    * @return: java.lang.Integer
    */
   Integer upHomeRepair(HomeRepair homeRepair);

   /**
    * 删除
    * @param repairIds
    * @return: java.lang.Integer
    */
   Integer delHomeRepair(@Param("repairIds") Long [] repairIds);
}
