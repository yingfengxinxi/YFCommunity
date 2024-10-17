package com.wr.estate.web.customer;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.PhoneService;
import com.wr.remote.estate.customer.phone.BusContactImport;
import com.wr.remote.estate.phone.BusContact;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 荀彧
 * @description 常用电话controller
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/phone")
@Api(tags = "物管平台-常用电话")
public class PhoneController extends BaseController {

    @Resource
    private PhoneService phoneService;

    @GetMapping("/getList")
    @ApiOperation("常用电话分页查询")
    @PreAuthorize("@ss.hasPermi('bussiness:phone:list')")
    @Log(title = "常用电话查询", businessType = BusinessType.OTHER)
    public TableDataInfo getList(BusContact busContact) {
        startPage();
        List<BusContact> list = phoneService.pageList(busContact);
        return getDataTable(list);
    }
    @ApiOperation("常用电话删除")
    @PostMapping("/del/{contactId}")
    @Log(title = "常用电话删除", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:remove')")
    public AjaxResult del(@PathVariable Long[] contactId) {
        if (phoneService.deleteByIds(contactId) > 0) {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }
    @ApiOperation("常用电话导出")
    @Log(title = "常用电话导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:export')")
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, BusContact busContact) {
        List<BusContact> list = phoneService.pageList(busContact);
        ExcelUtil<BusContact> util = new ExcelUtil<BusContact>(BusContact.class);
        util.exportExcel(response, list, "常用电话");
    }

    @ApiOperation("常用电话导入模板下载")
    @Log(title = "常用电话导入模板下载", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:import')")
    @PostMapping("/theImport")
    @ResponseBody
    public void theImport(HttpServletResponse response) {
        ExcelUtil<BusContactImport> util = new ExcelUtil<BusContactImport>(BusContactImport.class);
        util.exportExcel(response, null, "常用电话");
    }

    @ApiOperation("常用电话导入")
    @Log(title = "常用电话导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<BusContact> util = new ExcelUtil<BusContact>(BusContact.class);
        //
        BusContact busContact = new BusContact();
        busContact.setCommunityId(communityId);
        //查询所有的常用电话
        List<BusContact> list = phoneService.pageList(busContact);
        List<BusContact> phoneList = util.importExcel(file.getInputStream());
        List<BusContact> phoneLists = phoneList.stream().filter(bd -> {
            return getContactPhones(bd.getContactPhone(), list);
        }).collect(Collectors.toList());
        // 查询导入数据手机号是否重复
        if (phoneLists.size() != phoneList.size()) {
            return AjaxResult.error("手机号已存在请查看文档并进行修改！！");
        }
        long count = phoneList.stream().map(BusContact::getContactPhone).collect(Collectors.toList()).stream().distinct().count();
        // 查询导入数据手机号是否重复
        if (phoneList.size() != count) {
            return AjaxResult.error("导入文档手机号重复请查看文档并进行修改！！");
        }

        phoneList = phoneList.stream().filter(x -> {
            return x.getContactPhone().length() == 11;
        }).collect(Collectors.toList());

        String operName = SecurityUtils.getUsername();
        String message = phoneService.importUser(phoneList, communityId, operName);
        return AjaxResult.success(message);
    }

    private Boolean getContactPhones(String contactPhone, List<BusContact> temp) {
        for (BusContact v : temp) {
            if (contactPhone.equals(v.getContactPhone())) {
                return false;
            }
        }
        return true;
    }

    @ApiOperation("常用电话新增")
    @Log(title = "常用电话", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody BusContact busContact) {
        String operName = SecurityUtils.getUsername();
        busContact.setCreateBy(operName);
        busContact.setUpdateBy(operName);
        BusContact busContacts = new BusContact();
        busContacts.setCommunityId(busContact.getCommunityId());
        busContacts.setContactPhone(busContact.getContactPhone());
        List<BusContact> list = phoneService.pageList(busContacts);
        if (list.size() > 0) {
            return AjaxResult.error("新增失败手机号重复！！！");
        }
        if (phoneService.add(busContact) > 0) {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }

    @ApiOperation("常用电话详情")
    @PostMapping("/query/{contactId}")
    @PreAuthorize("@ss.hasPermi('bussiness:phone:list')")
    @Log(title = "常用电话查询", businessType = BusinessType.OTHER)
    public AjaxResult query(@PathVariable Long contactId) {
        if (null == contactId) {
            return AjaxResult.error("未知错误，请联系管理员");
        }
        return AjaxResult.success(phoneService.queryDeatil(contactId));
    }

    @ApiOperation("常用电话修改")
    @PostMapping("/update")
    @Log(title = "常用电话修改", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('bussiness:phone:edit')")
    public AjaxResult update(@RequestBody BusContact busContact) {
        if (null == busContact) {
            return AjaxResult.error("未知错误，请联系管理员");
        }
        if (phoneService.updatePhone(busContact) > 0) {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败，请联系管理员");
    }
}
