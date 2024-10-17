package com.wr.estate.web.log;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.service.log.CarService;
import com.wr.remote.estate.car.Car;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2022/10/13 10:25
 * @Version 1.5
 */
@RestController
@RequestMapping("/car")
public class CarController extends BaseController {
    @Autowired
    private CarService carService;

    @Autowired
    private TableMapper tableMapper;

    @PreAuthorize("@ss.hasPermi('system:car:list')")
    @GetMapping("/list")
    public TableDataInfo selectList(Car car){
        Map<String, Object> map = car.getParams();
        String date= (String) map.get("beginTime");
        if (StringUtils.isNull(car.getCommunityId())){
            return getDataTable(new ArrayList<>());
        }
        if (StringUtils.isNull(date)){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VEHICLE_LOG,car.getCommunityId(), new Date());
            car.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                startPage();
                List<Car>list=carService.selectList(car);
                for (Car car1 : list) {
                    car1.setTableName(tableName);
                    car1.setUserPhone(around(car1.getUserPhone(), 3, 4));
                    car1.setIdCardNo(around(car1.getIdCardNo(), 6, 4));
                }
                return getDataTable(list);
            }
            return getDataTable(new ArrayList<>());
        }
        if (StringUtils.isNotNull(date)){
            String tableName = TableUtils.getDbTableName(TableConstants.PREFIX_VEHICLE_LOG,car.getCommunityId(), date);
            car.setTableName(tableName);
            String tableName1=tableName.substring(tableName.indexOf(".")+1);
            int exist =tableMapper.existTable(tableName1);
            if (0!=exist){
                startPage();
                List<Car>list=carService.selectList(car);
                for (Car car1 : list) {
                    car1.setTableName(tableName);
                    car1.setUserPhone(around(car1.getUserPhone(), 3, 4));
                    car1.setIdCardNo(around(car1.getIdCardNo(), 6, 4));
                }
                return getDataTable(list);
            }
            return getDataTable(new ArrayList<>());
        }
        return getDataTable(new ArrayList<>());
        /*startPage();
        List<Car> list=carService.selectList(car);
        return getDataTable(list);*/
    }

    @GetMapping("/communityId")
    public List<Car> selectList1(){
        return carService.selectCommunityIdList();
    }

    @GetMapping("/id")
    public AjaxResult selectById(Car car){
        return AjaxResult.success(carService.selectById(car));
    }

    public static String around(String str, int index, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return StringUtils.left(str, index).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(str, end), StringUtils.length(str), "*"), "***"));
    }
}
