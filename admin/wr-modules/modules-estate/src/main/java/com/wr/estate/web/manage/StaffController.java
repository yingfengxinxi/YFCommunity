package com.wr.estate.web.manage;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.estate.EstateStaffRoleMapper;
import com.wr.estate.service.estate.EstateStaffRoleService;
import com.wr.estate.service.manage.StaffService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.StaffVo;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.system.RemoteFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:51:03
 * @Desc:
 */
@RestController
@RequestMapping("/staff")
@Api(tags = "物管平台-物业人员管理")
public class StaffController extends BaseController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private EstateStaffRoleService estateStaffRoleService;

    @ApiOperation("物业人员管理列表")
    @GetMapping("/list")
    @RequiresPermissions("manage:staff:list")
    public TableDataInfo list(Staff staff) {
        staff.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        startPage();
        List<Staff> list = staffService.selectStaffList(staff);
        return getDataTable(list);
    }

    @ApiOperation("物业人员管理详情")
    @GetMapping("/id")
    public TableDataInfo getStaffById(Long staffId) {
        //startPage();
        List<Staff> list = staffService.getStaffById(staffId);
        return getDataTable(list);
    }

    @ApiOperation("物业人员管理新增")
    @PostMapping("/add")
    public AjaxResult addStaff(@RequestBody Staff staff) {
        if (UserConstants.NOT_UNIQUE.equals(staffService.selectPhone(staff.getStaffPhone()))) {
            return AjaxResult.error(510,"此手机号已存在,请重新输入");
        }
        if (UserConstants.NOT_UNIQUE.equals(staffService.selectCardNo(staff.getCardNo()))) {
            return AjaxResult.error(510,"此身份证号已存在,请重新输入");
        }
        String username = SecurityUtils.getUsername();
        staff.setCreateBy(username);
        staff.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return toAjax(staffService.addStaff(staff));
    }

    @ApiOperation("物业人员管理修改")
    @PutMapping("/update")
    public AjaxResult updateStaff(@RequestBody Staff staff) {
        if (UserConstants.NOT_UNIQUE.equals(staffService.selectPhoneNo(staff))) {
            return AjaxResult.error("此手机号已存在,请重新输入");
        }
        if (UserConstants.NOT_UNIQUE.equals(staffService.selectCardNum(staff))) {
            return AjaxResult.error("此身份证号已存在,请重新输入");
        }
        String username = SecurityUtils.getUsername();
        staff.setUpdateBy(username);
        return toAjax(staffService.updateStaff(staff));
    }

    @ApiOperation("物业人员管理删除")
    @DeleteMapping("delete/{staffIds}")
    @RequiresPermissions("manage:staff:delete")
    public AjaxResult removeStaff(@PathVariable("staffIds") Long[] staffIds) {
        for (Long id : staffIds) {
            List<Staff> staff = staffService.getStaffById(id);
            if (null == staff.get(0).getDimissionTime()) {
                return AjaxResult.error("存在未离职员工,不允许删除");
            }
        }
        staffService.deleteUserByIds(staffIds);
        return toAjax(staffService.deleteStaffByIds(staffIds));
    }

    @ApiOperation("物业人员管理修改状态")
    @DeleteMapping("updateStatus/{staffIds}")
    @RequiresPermissions("manage:staff:update")
    public AjaxResult updateStatus(@PathVariable("staffIds") Long[] staffIds) {
        //删除系统用户表数据
        staffService.deleteUserByIds(staffIds);
        for (Long staffId : staffIds) {
            //删除物业移动端权限
            estateStaffRoleService.deleteByStaffId(staffId);
        }
        //修改物业员工表离职时间
        return toAjax(staffService.updateStatusByIds(staffIds));
    }

    /**
     * 图片上传
     */
    @ApiOperation("物业人员管理图片上传")
    @Log(title = "图片上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    @RequiresPermissions("manage:staff:upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if (R.FAIL == fileResult.getCode()) {
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("imgUrl", url);
            return ajaxResult;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 修改员工密码
     */
    @ApiOperation("修改员工密码")
    @Log(title = "员工密码修改", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPass")
    @RequiresPermissions("manage:staff:update")
    public AjaxResult resetPass(Staff staff) {
        staff.setPassword(staff.getPassword());
        return toAjax(staffService.resetPass(staff));
    }

    /**
     * 导出员工信息列表
     */
    @ApiOperation("员工信息导出")
    //@PreAuthorize(hasPermi = "system:employee:export")
    @Log(title = "员工信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @RequiresPermissions("manage:staff:export")
    public void export(HttpServletResponse response, Staff staff) throws IOException {
        List<Staff> list = staffService.selectStaffList(staff);
        /*List<Staff> exportList = Lists.newArrayList();
        if(staff.getIds().length > 0){
            for (Long id : staff.getIds()) {
                for (Staff sta : list) {
                    if(sta.getStaffId().equals(id)){
                        exportList.add(sta);
                        break;
                    }
                }
            }
        }else {
            exportList.addAll(list);
        }*/
        ExcelUtil<Staff> util = new ExcelUtil<Staff>(Staff.class);
        util.exportExcel(response, list, "员工信息数据");
       /* List<MedacalOlderEntity> list = medicalEntityService.lookoldere(medicalEntity.getMedicalId());
        ExcelUtil<MedacalOlderEntity> util = new ExcelUtil<MedacalOlderEntity>(MedacalOlderEntity.class);
        util.exportExcel(response, list, "医疗检测通告数据");*/
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Description 查询 空闲 繁忙 的人员
     * @Param [allotType, communityId] 分类工单类型 , 社区id
     * @Date 2022/11/4 15:08
     * @Author DIANWEI
     **/
    @ApiOperation("分类工单类型")
    @GetMapping("/listLeisureBusyStaffs")
    public AjaxResult listLeisureBusyStaffs(@RequestParam("allotType") String allotType,
                                            @RequestParam("communityId") Long communityId) {
        List<Staff> list = staffService.listLeisureBusyStaffs(allotType,
                communityId);
        return AjaxResult.success(list);
    }

    /**
     * @param communityId
     * @param workPostList
     * @return
     */
    @ApiOperation("根据小区工作类型查询工作人员")
    @GetMapping("getCommunityIdStaffList")
    public AjaxResult getCommunityIdStaffList(
            @RequestParam("communityId") Long communityId,
            @RequestParam(value = "workPostList", required = false) List<String> workPostList
    ) {

        return AjaxResult.success(staffService.getCommunityIdWorkPostStaff(communityId, workPostList));
    }


    // 上传模板
    @ApiOperation("导入物业员工信息")
    @Log(title = "批量导入物业员工信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<StaffVo> util = new ExcelUtil<StaffVo>(StaffVo.class);
        List<StaffVo> dataList = util.importExcel(file.getInputStream());
        String message = staffService.importData(dataList);
        return AjaxResult.success(message);
    }


    // 下载模板
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<StaffVo> util = new ExcelUtil<StaffVo>(StaffVo.class);
        util.importTemplateExcel(response, "物业员工");
    }

}
