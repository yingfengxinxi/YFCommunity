package com.wr.govern.mapper.manage;

import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.govern.manage.vo.UserDetailLogVO;
import com.wr.remote.govern.manage.vo.UserLogVO;
import com.wr.remote.govern.manage.vo.VehicleLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:42:05
 * @Desc: 轨迹管理DAO层
 */
@Mapper
public interface PathMapper {


    /**
     * 查询人员出行列表
     *
     * @param userLogVO
     * @return
     */
    List<UserLogVO> selectUserLogList(UserLogVO userLogVO, @Param("tableName") String tableName, @Param("queryTime") String queryTime);

    /**
     * 获取业主详细信息
     *
     * @param idCard
     * @return
     */
    UserLogVO selectOwnerInfo(@Param("idCard") String idCard, @Param("name") String name);

    /**
     * 获取租客详细信息
     *
     * @param idCard
     * @return
     */
    UserLogVO selectTenantInfo(@Param("idCard") String idCard, @Param("name") String name);

    /**
     * 获取人员轨迹详细信息
     *
     * @param idCard
     * @return
     */
    List<UserLogVO> getUserLogDetail(@Param("idCard") String idCard, @Param("currentTableName") String currentTableName);

    /**
     * 获取车辆进出记录列表
     *
     * @param tableName 表名
     * @param queryTime 查询时间
     * @param plateNo   车牌号
     * @return
     */
    List<VehicleLogVO> selectVehicleList(@Param("tableName") String tableName, @Param("plateNo") String plateNo, @Param("queryTime") String queryTime);


    /**
     * 获取车辆进出详情
     *
     * @param currentTableName
     * @param plateNo
     * @return
     */
    List<VehicleLogVO> getVehicleDetail(@Param("currentTableName") String currentTableName, @Param("plateNo") String plateNo);

    /**
     * 获取用户详细信息
     * @param currentTableName 当前表名
     * @param logId id
     * @return
     */
    UserLogVO getUserLogInfo(@Param("currentTableName")String currentTableName, @Param("logId") String logId);
}
