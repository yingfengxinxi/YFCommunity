package com.wr.estate.web.manage;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.manage.SalaryService;
import com.wr.remote.estate.manage.Salary;
import com.wr.remote.estate.manage.SalaryTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:51:03
 * @Desc:
 */
@RestController
@RequestMapping("/salary")
public class SalaryController extends BaseController {
    @Autowired
    private SalaryService salaryService;
    
    @GetMapping("/salaryList")
    @RequiresPermissions("manage:salary:salaryList")
    public TableDataInfo salaryList()
    {
        startPage();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        List<SalaryTotal> list = salaryService.selectSumList(estateId);
        return getDataTable(list);
    }

    @GetMapping("/list")
    @RequiresPermissions("manage:salary:list")
    public TableDataInfo list(Salary salary)
    {
        startPage();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        salary.setEstateId(estateId);
        List<Salary> list = salaryService.selectList(salary);
        return getDataTable(list);
    }

    @GetMapping("/listName")
    @RequiresPermissions("manage:salary:list")
    public TableDataInfo listName()
    {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        List<Map<String,Object>> list = salaryService.listName(estateId);
        return getDataTable(list);
    }

    @PostMapping("/add")
    @RequiresPermissions("manage:salary:add")
    public AjaxResult addSalary(Salary salary)
    {
        if (UserConstants.NOT_UNIQUE.equals(salaryService.checkUnique(salary)))
        {
            return AjaxResult.error("此人员当月薪资已存在,请勿重复添加");
        }
        String username = SecurityUtils.getUsername();
        salary.setCreateBy(username);
        boolean result = salaryService.addSalary(salary);
        if (result){

            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
    @PutMapping("/update")
    @RequiresPermissions("manage:salary:update")
    public AjaxResult updateSalary(Salary salary)
    {
        String username = SecurityUtils.getUsername();
        salary.setUpdateBy(username);
        boolean result = salaryService.updateSalary(salary);
        if (result){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    //@PreAuthorize(hasPermi = "manage:salary:import")
    @RequiresPermissions("manage:salary:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        List<Salary> salList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = salaryService.importSalary(salList, updateSupport, operName);
        return AjaxResult.success(message);
    }



    @PostMapping("/importTemplate")

    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        util.importTemplateExcel(response, "薪资数据");
    }

    @PostMapping("/export")
    @RequiresPermissions("manage:salary:export")
    public void export(HttpServletResponse response, Salary salary)
    {
        List<Salary> list = salaryService.selectList(salary);
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        util.exportExcel(response, list, salary.getYearMonth()+"薪资数据");
    }

    /**
     * 删除薪资
     */
    @Log(title = "薪资删除", businessType = BusinessType.DELETE)
    @RequiresPermissions("manage:salary:delete")
    @DeleteMapping("delete/{salaryIds}")
    public AjaxResult remove(@PathVariable("salaryIds") Long[] salaryIds)
    {
        return toAjax(salaryService.deleteSalaryByIds(salaryIds));
    }
}
