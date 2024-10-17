package com.wr.api.estate.mapper.application.mine;

import com.wr.api.estate.entity.vo.application.mine.SalaryVO;
import com.wr.api.estate.entity.vo.application.mine.StaffVO;

import java.util.List;

/**
 * @author DIANWEI
 * 我的
 * @date 2022/11/16 15:42
 */
public interface MineMapper {

    /**
     * @return com.wr.api.estate.entity.vo.application.mine.SalaryVO
     * @Description 员工薪资查询
     * @Param [salaryVO]
     * @Date 2022/11/16 16:56
     * @Author DIANWEI
     **/
    public List<SalaryVO> selectSalaryList(SalaryVO salaryVO);

    /**
     * @return java.lang.Integer
     * @Description 修改员工信息
     * @Param [staffVO]
     * @Date 2022/11/22 17:22
     * @Author DIANWEI
     **/
    public Integer updateStaff(StaffVO staffVO);

    /**
     * @return java.lang.Long
     * @Description 根据用户id 查询 员工id
     * @Param [staffVO]
     * @Date 2022/11/23 19:39
     * @Author DIANWEI
     **/
    public Long getStaffId(StaffVO staffVO);

    /**
     * @return java.lang.Integer
     * @Description 修改用户信息
     * @Param [staffVO]
     * @Date 2022/11/22 17:22
     * @Author DIANWEI
     **/
    public Integer updateUser(StaffVO staffVO);
}
