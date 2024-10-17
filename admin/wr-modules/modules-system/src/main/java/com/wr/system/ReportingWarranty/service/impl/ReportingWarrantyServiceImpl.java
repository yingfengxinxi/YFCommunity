package com.wr.system.ReportingWarranty.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.SysDictData;
import com.wr.system.ReportingWarranty.domain.ReportingWarranty;
import com.wr.system.ReportingWarranty.mapper.SystemReportingWarrantyMapper;
import com.wr.system.ReportingWarranty.service.ReportingWarrantyService;
import com.wr.system.business.domain.HomeScoreVO;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/1/31 20:57
 */
@Service
public class ReportingWarrantyServiceImpl extends ServiceImpl<SystemReportingWarrantyMapper, ReportingWarranty> implements ReportingWarrantyService {
    @Override
    public List<ReportingWarranty> getList(ReportingWarranty reportingWarranty) {
        return baseMapper.getList( reportingWarranty );
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ReportingWarranty get(String id) {
        return baseMapper.get( id );
    }

    @Override
    public HomeScoreVO getcount(Long communityId) {
        //查询评价
        return baseMapper.getCount( communityId );
    }

    @Override
    public Map<String, List<Map<String, Object>>> getLogList(Map<String, Object> maps) {
        Map<String, List<Map<String, Object>>> map = new HashMap<>( 4 );
        List<SysDictData> eventTypeDictList = DictUtils.getDictCache( "event_type" );

        List<String> eventTypeList = eventTypeDictList.stream().map( SysDictData -> SysDictData.getDictValue() ).collect( Collectors.toList() );

        //获取月份的日期列表
        List<Integer> daysInMonth = getDaysInMonth( MapUtil.getInt( maps, "year" ), MapUtil.getInt( maps, "month" ) );
        //每日行人出入数据
        List<Map<String, Object>> userNumBig = baseMapper.getUserNumListDay( MapUtil.getLong( maps, "communityId" ), eventTypeList, MapUtil.getStr( maps, "year" ), MapUtil.getStr( maps, "month" ) );
        List<Map<String, Object>> list = getList( daysInMonth, userNumBig );
        List<Map<String, Object>> userNumBigMonth = baseMapper.getUserNumListMonth( MapUtil.getLong( maps, "communityId" ), eventTypeList, MapUtil.getStr( maps, "year" ), MapUtil.getStr( maps, "month" ) );
        //每日车辆出入数据
        List<Map<String, Object>> vehicleNumBig = baseMapper.getVehicleNumListDay( MapUtil.getLong( maps, "communityId" ), MapUtil.getStr( maps, "year" ), MapUtil.getStr( maps, "month" ) );
        List<Map<String, Object>> listVehicle = getList( daysInMonth, vehicleNumBig );
        List<Map<String, Object>> vehicleNumBigMonth = baseMapper.getVehicleNumListMonth( MapUtil.getLong( maps, "communityId" ), MapUtil.getStr( maps, "year" ), MapUtil.getStr( maps, "month" ) );

        map.put( "userNumBig", list );
        map.put( "userNumBigMonth", userNumBigMonth );
        map.put( "vehicleNumBig", listVehicle );
        map.put( "vehicleNumBigMonth", vehicleNumBigMonth );
        return map;
    }

    // 获取指定年份和月份的天数列表方法
    private  List<Integer> getDaysInMonth(int year, int month) {
        // 获取指定年份和月份的 YearMonth 对象
        YearMonth yearMonth = YearMonth.of(year, month);

        // 获取该月份的天数
        int daysInMonth = yearMonth.lengthOfMonth();

        // 构建天数列表
        List<Integer> dayList = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            dayList.add(i);
        }

        return dayList;
    }

    private List<Map<String, Object>> getList(List<Integer> daysInMonth,List<Map<String, Object>> userNumBig){
        List<Map<String, Object>> combinedList = new ArrayList<>();

        // 遍历每天的日期列表
        for (Integer day : daysInMonth) {
            String dailyTraffic = "0";

            // 在userNumBig中查找与当前天数匹配的daily_traffic
            for (Map<String, Object> userData : userNumBig) {
                Integer eventDay = (Integer) userData.get("event_day");
                if (eventDay != null && eventDay.equals(day)) {
                    dailyTraffic = MapUtil.getStr( userData, "daily_traffic" );
                    break;
                }
            }

            // 创建一个包含日期和对应daily_traffic的Map
            Map<String, Object> data = new HashMap<>();
            data.put("day", day);
            data.put("daily_traffic", dailyTraffic);

            // 添加到combinedList中
            combinedList.add(data);
        }
        return combinedList;
    }

    @Override
    public List<Map<String, Object>> findListBusDoor(Long communityId) {
        return baseMapper.findListBusDoor( communityId );
    }
}
