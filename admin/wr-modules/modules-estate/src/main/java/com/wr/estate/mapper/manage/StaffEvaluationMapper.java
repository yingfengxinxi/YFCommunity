package com.wr.estate.mapper.manage;

import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.StaffEvaluationVo;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 11:28:34
 * @Desc: 员工评价
 */
@Mapper
public interface StaffEvaluationMapper {

    /**
     * @param staffId
     * @return
     */
    List<StaffEvaluationVo> getList(@Param("staffId") Long staffId);

}
