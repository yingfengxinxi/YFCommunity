package com.wr.estate.service.impl.log;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.estate.mapper.log.CarMapper;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.service.log.CarService;
import com.wr.remote.estate.car.Car;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author lvzy
 * @Date 2022/10/13 10:12
 * @Version 1.5
 */
@Service
public class CarServiceImpI implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Car> selectList(Car car) {
       /* Map<String, Object> map = car.getParams();
        String date= (String) map.get("beginTime");
        if (StringUtils.isNull(car.getCommunityId())){
            return Lists.newArrayList();
        }
        if (StringUtils.isNull(date)){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VEHICLE_LOG,car.getCommunityId(), new Date());
            car.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                List<Car>list=carMapper.selectList(car);
                for (Car car1 : list) {
                    car1.setTableName(tableName);
                    car1.setUserPhone(around(car1.getUserPhone(), 3, 4));
                    car1.setIdCardNo(around(car1.getIdCardNo(), 6, 4));
                }
                return list;
            }
            return Lists.newArrayList();
        }
        if (StringUtils.isNotNull(date)){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VEHICLE_LOG,car.getCommunityId(), date);
            car.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                List<Car>list=carMapper.selectList(car);
                for (Car car1 : list) {
                    car1.setTableName(tableName);
                    car1.setUserPhone(around(car1.getUserPhone(), 3, 4));
                    car1.setIdCardNo(around(car1.getIdCardNo(), 6, 4));
                }
                return list;
            }
            return Lists.newArrayList();
        }*/
//        if (StringUtils.isNotNull(car.getCommunityId())&&StringUtils.isNull(date)){
//            String tableName = TableUtils.getTableName(TableConstants.PREFIX_VEHICLE_LOG,car.getCommunityId(), new Date());
//            int exist = tableMapper.existTable(tableName);
//            if(0 == exist){
//                return Lists.newArrayList();
//            }
//            car.setTableName(TableConstants.LOG_DB_NAME + "." + tableName);
//            List<Car>list=carMapper.selectList(car);
//            for (Car car1 : list) {
//                car1.setTableName(tableName);
//                car1.setUserPhone(around(car1.getUserPhone(), 3, 4));
//                car1.setIdCardNo(around(car1.getIdCardNo(), 6, 4));
//            }
//            return list;
//        }
        //return Lists.newArrayList();
        return carMapper.selectList(car);
    }

    @Override
    public List<Car> selectCommunityIdList() {
        return carMapper.selectCommunityIdList();
    }

    @Override
    public Car selectById(Car car) {
        return carMapper.selectById(car);
    }

    /*public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }*/
}
