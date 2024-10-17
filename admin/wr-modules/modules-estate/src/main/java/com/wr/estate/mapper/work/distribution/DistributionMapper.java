package com.wr.estate.mapper.work.distribution;

import com.wr.remote.work.distribution.Distribution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/25 9:27
 * @Version 1.5
 */
@Mapper
public interface DistributionMapper {

    /**
     * 根据小区查询所有
     * @param distribution
     * @return
     */
    List<Distribution> selectList(Distribution distribution);
    /**
     * 根据小区 和 工单类型 查询详情信息
     * @param communityId 小区id allotType 工单类型
     * @return
     */
    Distribution getAllotByType(@Param("communityId") Long communityId,@Param("allotType")String allotType);
    /**
     * 新增
     * @param distribution
     * @return
     */
    int insertAllot(Distribution distribution);



    int insertAllotPost(Distribution distribution);


    /**
     * 修改
     * @param distribution
     * @return
     */
    int updateAllot(Distribution distribution);


    /**
     * 查询是否存在
     * @param distribution
     * @return
     */
    int selectAllot(Distribution distribution);


    /**
     * 根据id查询
     * @param allotId
     * @return
     */
    Distribution selectAllotById(Long allotId);


    String[] selectListAllot(Long allotId);


    int deleteAllot(Distribution distribution);

    String selectName(Long communityId);




}
