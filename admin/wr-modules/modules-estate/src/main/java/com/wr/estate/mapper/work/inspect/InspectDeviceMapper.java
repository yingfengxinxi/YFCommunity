package com.wr.estate.mapper.work.inspect;

import com.wr.remote.work.inspect.InspectDevice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检工单-设备表
 * @Author: lvzy
 * @Date: 2022-12-06 16:02:17
 * @Desc:巡检工单-设备表
 */
@Mapper
public interface InspectDeviceMapper {

    int insertSelective(InspectDevice inspectDevice);


}