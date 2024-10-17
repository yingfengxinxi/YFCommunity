package com.wr.api.estate.service.application.mine;

import com.wr.api.estate.entity.vo.application.mine.SalaryVO;
import com.wr.api.estate.entity.vo.application.mine.StaffVO;

import java.util.List;

/**
 * @author DIANWEI
 * 我的
 * @date 2022/11/16 15:43
 */
public interface MineService {

    /**
     * @Description 员工薪资查询
     * @Param [salaryVO]
     * @return com.wr.api.estate.entity.vo.application.mine.SalaryVO
     * @Date 2022/11/16 16:56
     * @Author DIANWEI
     **/
    public List<SalaryVO> selectSalaryList(SalaryVO salaryVO);

    /**
     * @Description 修改个人信息
     * @Param [staffVO]
     * @return java.lang.Integer
     * @Date 2022/11/22 17:22
     * @Author DIANWEI
     **/
    public String updateStaff(StaffVO staffVO);
}
