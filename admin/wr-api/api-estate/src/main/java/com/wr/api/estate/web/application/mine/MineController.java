package com.wr.api.estate.web.application.mine;

import com.wr.api.estate.entity.vo.application.mine.SalaryVO;
import com.wr.api.estate.entity.vo.application.mine.StaffVO;
import com.wr.api.estate.service.application.mine.MineService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DIANWEI
 * @Description 我的   Controller
 * @date 2022/11/16 9:42
 */
@RestController
@RequestMapping("/v2/mine")
public class MineController {

    @Autowired
    private MineService mineService;

    /**
     * @Description 薪资查询
     * @Date 2022/11/16 15:08
     * @Author DIANWEI
     **/
    @GetMapping("/salaryList")
    @AuthRequired
    public AjaxResult selectSalaryList(SalaryVO salaryVO) {
        return AjaxResult.success(mineService.selectSalaryList(salaryVO));
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Description 修改个人信息
     * @Param [staffVO]
     * @Date 2022/11/22 17:56
     * @Author DIANWEI
     **/
    @PostMapping("/staffEdit")
    @AuthRequired
    public AjaxResult updateStaff(@RequestBody StaffVO staffVO) {

        return AjaxResult.success(mineService.updateStaff(staffVO));
    }
}
