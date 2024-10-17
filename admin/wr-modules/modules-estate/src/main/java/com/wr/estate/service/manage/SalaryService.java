package com.wr.estate.service.manage;

import com.wr.remote.estate.manage.Salary;
import com.wr.remote.estate.manage.SalaryTotal;

import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:08:29
 * @Desc: 薪酬
 */
public interface SalaryService {
    /**
     * 根据条件分页查询角色数据
     *
     * @param
     * @return 员工信息数据集合信息
     */
    public List<SalaryTotal> selectSumList(Long estateId);

     List<Salary> selectList(Salary salary);

     List<SalaryTotal> selectSalaryList(List<String> list);

     List<Map<String,Object>> listName(Long estateId);

     boolean addSalary(Salary salary);

     boolean updateSalary(Salary salary);
    /**
     * 批量删除
     *
     * @param salaryIds 需要删除的数据主键集合
     * @return 结果
     */
     int deleteSalaryByIds(Long[] salaryIds);

    String checkUnique(Salary salary);

    String importSalary(List<Salary> salList, Boolean isUpdateSupport, String operName);
}
