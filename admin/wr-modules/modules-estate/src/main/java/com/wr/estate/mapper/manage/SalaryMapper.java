package com.wr.estate.mapper.manage;

import com.wr.remote.estate.manage.Salary;
import com.wr.remote.estate.manage.SalaryTotal;

import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 11:29:12
 * @Desc: 薪酬
 */
public interface SalaryMapper {
    /**
     * 根据条件分页查询角色数据
     *
     * @param
     * @return 员工数据集合信息
     */
    List<SalaryTotal> selectSumList(Long estateId);

    List<Salary> selectList(Salary salary);

    List<Map<String, Object>> listName(Long estateId);
    /*List<Map<String,Object>> listName();*/

    boolean addSalary(Salary salary);

    boolean updateSalary(Salary salary);

    /**
     * 批量删除
     *
     * @param salaryIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSalaryByIds(Long[] salaryIds);

    int checkUnique(Salary salary);

    /* int insoldere (List<Salary> list);*/

}
