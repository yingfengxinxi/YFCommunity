package com.wr.api.estate.entity.vo.application.mine;

import com.wr.api.estate.entity.application.mine.Salary;
import lombok.Data;

/**
 * @author DIANWEI
 * 员工薪酬对象
 * @date 2022/11/16 16:53
 */
@Data
public class SalaryVO extends Salary {
    /**
     * 员工姓名
     */
    private String staffName;
    /**
     * 员工手机号
     */
    private String staffPhone;
    /**
     * 年
     */
    private String year;
}
