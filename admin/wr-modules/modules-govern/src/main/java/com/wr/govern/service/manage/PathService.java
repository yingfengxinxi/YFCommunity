package com.wr.govern.service.manage;

import com.wr.remote.govern.manage.VehicleLog;
import com.wr.remote.govern.manage.vo.UserDetailLogVO;
import com.wr.remote.govern.manage.vo.UserLogVO;
import com.wr.remote.govern.manage.vo.VehicleDetailLogVO;
import com.wr.remote.govern.manage.vo.VehicleLogVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:40:51
 * @Desc: 轨迹管理接口层
 */
public interface PathService {

    /**
     * 获取人员轨迹列表
     * @param userLogVO
     * @return
     */
    Map<String,Object> selectUserLogList(UserLogVO userLogVO);

    /**
     * 获取人员轨迹详情
     * @param idCard,currentTableName,logId
     * @return UserDetailLogVO
     */
    UserDetailLogVO getUserLogDetail(String idCard, String currentTableName,String logId);

    /**
     * 获取车辆进出列表
     * @param vehicleLog
     * @return
     */
    Map<String,Object> selectVehicleLogList(VehicleLogVO vehicleLog);

    /**
     * 获取车辆进出详情
     * @param plateNo
     * @param currentTableName
     * @return
     */
    VehicleDetailLogVO getVehicleLogDetail(String plateNo, String currentTableName);

}
