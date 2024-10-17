package com.wr.api.owner.mapper.serve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.api.owner.entity.serve.BusStaffEvaluation;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-11 11:56:45
 * @Desc: 车位管理
 */
@Mapper
public interface BusStaffEvaluationMapper extends BaseMapper<BusStaffEvaluation> {

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Author SUNk
     * @Description 根据员工ID查询员工信息
     * @Date 14:53 2024/3/17
     * @Param [id]
     **/
    @Select("SELECT * FROM xccj_community.bus_staff WHERE staff_id = #{staffId}")
    Staff findOneBusStaff(Long staffId);
}