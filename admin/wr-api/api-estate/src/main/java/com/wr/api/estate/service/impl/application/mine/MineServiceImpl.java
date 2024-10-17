package com.wr.api.estate.service.impl.application.mine;

import com.wr.api.estate.entity.vo.application.mine.SalaryVO;
import com.wr.api.estate.entity.vo.application.mine.StaffVO;
import com.wr.api.estate.mapper.application.mine.MineMapper;
import com.wr.api.estate.service.application.mine.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author DIANWEI
 * 我的
 * @date 2022/11/16 17:15
 */
@Service
public class MineServiceImpl implements MineService {
    @Autowired
    private MineMapper mineMapper;

    @Override
    public List<SalaryVO> selectSalaryList(SalaryVO salaryVO) {

        return mineMapper.selectSalaryList(salaryVO);
    }

    @Override
    @Transactional(readOnly = false)
    public String updateStaff(StaffVO staffVO) {
        if ("12".equals(staffVO.getUserType())) {
            staffVO.setStaffId(mineMapper.getStaffId(staffVO));
            staffVO.setStaffName(staffVO.getUserName());
            staffVO.setGender(staffVO.getSex());
            Integer staff = mineMapper.updateStaff(staffVO);
            if (staff == 0) {
                return "员工信息修改失败";
            }
        }
        Integer user = mineMapper.updateUser(staffVO);
        if (user == 0) {
            return "用户信息修改失败";
        }
        return "修改成功！";
    }
}
