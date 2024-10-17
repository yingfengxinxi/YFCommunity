package com.wr.estate.service.impl.manage;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.CustomException;
import com.wr.common.core.utils.StringUtils;
import com.wr.estate.mapper.manage.SalaryMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.service.manage.SalaryService;
import com.wr.remote.estate.manage.Salary;
import com.wr.remote.estate.manage.SalaryTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:08:03
 * @Desc:
 */
@Service
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private SalaryMapper salaryMapper;
    @Autowired
    private StaffMapper staffMapper;

    /**
     * 根据条件分页查询员工数据
     *
     * @param
     * @return 员工数据集合信息
     */
    @Override
    //@DataScope(deptAlias = "d")
    public List<SalaryTotal> selectSumList(Long estateId)
    {
        return salaryMapper.selectSumList(estateId);
    }

    @Override
    public List<Salary> selectList(Salary salary) {
        return salaryMapper.selectList(salary);
    }

    @Override
    public List<SalaryTotal> selectSalaryList(List<String> list) {
        return null;
    }

    @Override
    public List<Map<String, Object>> listName(Long estateId) {
        return salaryMapper.listName(estateId);
    }

    @Override
    @Transactional
    public boolean addSalary(Salary salary) {
        if("".equals(salary.getFullDay()) || null == salary.getFullDay() ){
            salary.setFullDay(0L);
        }
        if("".equals(salary.getAttendDay()) || null ==salary.getAttendDay()){
            salary.setAttendDay(0L);
        }
        if("".equals(salary.getFullAward()) || null ==salary.getFullAward()){
            salary.setFullAward(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getAgeAward()) || null ==salary.getAgeAward()){
            salary.setAgeAward(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getGrossSalary()) || null ==salary.getGrossSalary()){
            salary.setGrossSalary(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getSocialFee()) || null ==salary.getSocialFee()){
            salary.setSocialFee(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getLateFee()) || null ==salary.getLateFee()){
            salary.setLateFee(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getTaxFee()) || null ==salary.getTaxFee()){
            salary.setTaxFee(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getActuallySalary()) || null ==salary.getActuallySalary()){
            salary.setActuallySalary(new BigDecimal(String.valueOf(0.00)));
        }
        if("".equals(salary.getRemark()) || null ==salary.getRemark()){
            salary.setRemark("");
        }

        return salaryMapper.addSalary(salary);
    }

    @Override
    @Transactional
    public boolean updateSalary(Salary salary) {
        return salaryMapper.updateSalary(salary);
    }

    @Override
    @Transactional
    public int deleteSalaryByIds(Long[] salaryIds) {
        return salaryMapper.deleteSalaryByIds(salaryIds);
    }

    @Override
    public String checkUnique(Salary salary) {
        int count = salaryMapper.checkUnique(salary);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public String importUser(List<Salary> salList, Boolean isUpdateSupport, String operName) {
        for (int i = 0; i <salList.size(); i++) {
            if (salList.get(i).getCreateBy() == null){
                salList.get(i).setCreateBy(operName);
            }

        }
        int i = salaryMapper.insoldere(salList);
        if (i>0){
            return "导入成功";
        }
        else {
            return "导入失败";
        }
    }*/
    @Override
    @Transactional
    public String importSalary(List<Salary> salList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(salList) || salList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Salary sal : salList)
        {
            try
            {
                // 验证是否存在这个用户
                int no = staffMapper.selectCardNo(sal.getCardNo());
                if(no>0){
                    sal.setStaffId(staffMapper.selectStaffId(sal.getCardNo()));
                    sal.setCreateBy(operName);
                }
                //sal.setStaffId(staffMapper.selectStaffId(sal.getCardNo()));
                int no1 = salaryMapper.checkUnique(sal) ;
                if (no>0 && no1==0)
                {

                    this.addSalary(sal);
                    successNum++;
                    successMsg.append("<br/>" + successNum+ "、" + sal.getStaffName()+ "薪资 "  + " 导入成功");
                }
                else if (no ==0)
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、"+  sal.getStaffName()+ "员工 " + " 不存在");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、"+  sal.getStaffName()+ "当月薪资 " + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、"+ sal.getStaffName()+ "薪资 " + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
