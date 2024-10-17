package com.wr.estate.web.property;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.BusStaffPropertyVo;
import com.wr.estate.entity.vo.CarportImportVo;
import com.wr.estate.entity.vo.StaffPropertyVo;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.property.BusStaffPropertyService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.property.StaffProperty;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/19 20:02
 */
@RestController
@RequestMapping("staffProperty")
@Api(tags = "物管平台-物业人员个人资产管理")
public class BusStaffPropertyController extends BaseController {

    @Autowired
    private BusStaffPropertyService staffPropertyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    /**
     * @param busStaffPropertyVo
     * @return
     */
    @GetMapping("list")
    @ApiOperation("物业人员个人资产管理列表")
    public TableDataInfo list(BusStaffPropertyVo busStaffPropertyVo) {
        busStaffPropertyVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        startPage();
        return getDataTable(staffPropertyService.list(busStaffPropertyVo));
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @GetMapping("updateStatusPass")
    @ApiOperation("物业人员个人资产管理审核通过")
    public AjaxResult updateStatusPass(@RequestParam("id") String id) {
        StaffProperty staffProperty = staffPropertyService.getById(id);
        staffProperty.setAuditStatus("1");
        staffProperty.setAuditBy(SecurityUtils.getUsername());
        staffProperty.setUpdateBy(SecurityUtils.getUsername());
        staffProperty.setUpdateTime(new Date());
        staffProperty.setAuditTime(new Date());
        staffPropertyService.updateById(staffProperty);
        return AjaxResult.success();
    }

    /**
     * 审核驳回
     *
     * @param staffProperty
     * @return
     */
    @ApiOperation("物业人员个人资产管理审核驳回")
    @PostMapping("updateStatusReject")
    public AjaxResult updateStatusReject(@RequestBody StaffProperty staffProperty) {
        StaffProperty sp = staffPropertyService.getById(staffProperty.getId());
        sp.setAuditStatus("2");
        sp.setRejectReason(staffProperty.getRejectReason());
        sp.setUpdateTime(new Date());
        sp.setAuditBy(SecurityUtils.getUsername());
        sp.setUpdateBy(SecurityUtils.getUsername());
        sp.setAuditTime(new Date());
        staffPropertyService.updateById(sp);
        return AjaxResult.success();
    }

    /**
     * @param staffProperty
     * @return
     */
    @ApiOperation("物业人员个人资产认证提交")
    @PostMapping("add")
    public AjaxResult add(@RequestBody StaffProperty staffProperty) {
        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StaffProperty::getPlateNo, staffProperty.getPlateNo());
        queryWrapper.eq(StaffProperty::getUserId, staffProperty.getUserId());
        List<StaffProperty> list = staffPropertyService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            long count = list.stream().filter(sp -> sp.getAuditStatus().equals("1")).count();
            if (count >= 1) {
                return AjaxResult.error("当前车牌号资产已认证");
            }
            if (list.size() > count) {
                staffProperty.setId(list.get(0).getId());
            }
        }
        staffProperty.setCreateTime(new Date());
        staffProperty.setAuditStatus("1");
        staffProperty.setVehicleStatus("0");
        staffProperty.setDelFlag("0");
        staffProperty.setAuditTime(new Date());
        staffProperty.setAuditBy(SecurityUtils.getUsername());
        staffProperty.setUpdateBy(null);
        staffProperty.setRejectReason(null);
        staffProperty.setUpdateTime(null);
        staffPropertyService.save(staffProperty);
        return AjaxResult.success();
    }

    /**
     * @param staffProperty
     * @return
     */
    @ApiOperation("物业人员个人资产认证修改")
    @PostMapping("update")
    public AjaxResult update(@RequestBody StaffProperty staffProperty) {
        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StaffProperty::getPlateNo, staffProperty.getPlateNo());
        queryWrapper.eq(StaffProperty::getUserId, staffProperty.getUserId());
        if (StringUtils.isNotEmpty(staffProperty.getId())) {
            queryWrapper.apply(" id!='" + staffProperty.getId() + "'");
        }
        List<StaffProperty> list = staffPropertyService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            long count = list.stream().filter(sp -> sp.getAuditStatus().equals("1")).count();
            if (count >= 1) {
                return AjaxResult.error("当前车牌号资产已认证");
            }
            if (list.size() > count) {
                staffProperty.setId(list.get(0).getId());
            }
        }
        staffProperty.setUpdateBy(SecurityUtils.getUsername());
        staffProperty.setUpdateTime(new Date());
        staffPropertyService.updateById(staffProperty);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        StaffProperty staffProperty = staffPropertyService.getById(id);
        if (staffProperty != null) {
            Long userId = staffProperty.getUserId();
            SysUser user = userService.selectUserById(userId);
            if (user != null) {
                staffProperty.setPhone(user.getPhonenumber());
                staffProperty.setUserName(user.getNickName());
            }

            String communityName = communityService.getCommunityName(staffProperty.getCommunityId());
            staffProperty.setCommunityName(communityName);
        }
        return AjaxResult.success(staffProperty);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("deleteById")
    public AjaxResult deleteById(@RequestParam("id") String id) {
        staffPropertyService.removeById(id);
        return AjaxResult.success();
    }

    @ApiOperation("根据物业人员userId查询认证车位")
    @GetMapping("getUserIdVehicleNoList")
    public AjaxResult getUserIdVehicleNoList(@RequestParam("userId") String userId) {

        return AjaxResult.success(staffPropertyService.getUserIdVehicleNo(userId));
    }


    @ApiOperation("导出模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<StaffPropertyVo> util = new ExcelUtil<StaffPropertyVo>(StaffPropertyVo.class);
        util.importTemplateExcel(response, "物业人员车辆资产信息导入模板");
    }


    @Log(title = "批量导入物业人员车辆资产信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<StaffPropertyVo> util = new ExcelUtil<StaffPropertyVo>(StaffPropertyVo.class);
        List<StaffPropertyVo> dataList = util.importExcel(file.getInputStream());
        String message = staffPropertyService.importData(dataList);
        return AjaxResult.success(message);
    }
}
