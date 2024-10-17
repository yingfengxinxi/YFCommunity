package com.wr.estate.mapper.work.inspect;

import com.wr.remote.work.inspect.Inspect;
import com.wr.remote.work.vo.InspectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检工单
 * @Author: lvzy
 * @Date: 2022-10-25 11:45:32
 * @Desc: 巡检工单
 */
@Mapper
public interface InspectMapper {

    /**
     * 巡检工单新增
     * @param record
     * @return
     */
    int insertSelective(Inspect record);

    /**
     * 详情=展示
     * @param inspectId
     * @return
     */
    InspectVo selectByPrimaryKey(Long inspectId);

    /**
     * 修改
     * @param record
     * @return
     */
    Integer updateByPrimaryKeySelective(InspectVo record);

    /**
     * 分页查询
     * @param inspectVo
     * @return
     */
    List<InspectVo> listPage(InspectVo inspectVo);

    /**
     * 获取设备类型
     * @return
     */
    List<InspectVo> deviceType();

    /**
     * 获取设备编号
     * @param deviceType
     * @return
     */
    List<InspectVo> deviceNo(@Param("deviceType") String deviceType, @Param("communityId") Long communityId);

    /**
     * 插入设备数据
     * @param inspectId
     * @param deviceType
     * @param indexCode
     * @return
     */
    Integer insertDevice(@Param("inspectId") Long inspectId, @Param("deviceType") String deviceType, @Param("indexCode") String indexCode);

    /**
     * 设备详情
     * @param inspectId
     * @return
     */
    List<InspectVo> detailList(Long inspectId);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer UpdateRemove(Long id);

    /**
     * 详情 工单执行人信息
     * @param inspectId 工单ID
     * @param orderType 工单类型
     * @return
     */
    List<InspectVo> deatilListTwo(@Param("inspectId") Long inspectId, @Param("orderType") String orderType);

    /**
     * 查分配方式
     * @param communityId
     * @param allotType
     * @return
     */
    InspectVo selectAllotWay(@Param("communityId") Long communityId, @Param("allotType") String allotType);

    /**
     * 修改查看详情
     * @param inspectId
     * @return
     */
    InspectVo selectDetail(Long inspectId);

    /**
     * 查找是否有生成巡检工单
     * @param planId
     * @return
     */
    int selectCrerateData(Long planId);

    /**
     * 查找设备集合
     * @param inspectId
     * @return
     */
    List<String> selectDeviceList(Long inspectId);
}
