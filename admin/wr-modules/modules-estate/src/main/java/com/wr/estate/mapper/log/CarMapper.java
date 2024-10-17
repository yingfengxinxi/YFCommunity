package com.wr.estate.mapper.log;

import com.wr.remote.estate.car.Car;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/13 9:59
 * @Version 1.5
 */
@Mapper
public interface CarMapper {


    /**
     * 查询集合
     * @param car
     * @return
     */
    List<Car> selectList(Car car);


    /**
     * 查寻车辆集合
     * @return
     */
    List<Car> selectCommunityIdList();

    /**
     * 根据id查询数据
     * @param car
     * @return
     */
    Car selectById(Car car);


    /**
     * 查询第一个小区id
     * @return
     */
    Long selectMinId();
}
