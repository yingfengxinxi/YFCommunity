package com.wr.api.owner.web.home.icon;

import com.wr.api.owner.entity.home.icon.XcIcon;
import com.wr.api.owner.service.home.icon.IXcIconService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * icon设置Controller
 *
 * @author ruoyi
 * @date 2022-12-09
 */
@RestController
@RequestMapping("/icon")
public class XcIconController extends BaseController {
    @Autowired
    private IXcIconService xcIconService;

    /**
     * 查询icon设置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(XcIcon xcIcon) {
        startPage();
        List<XcIcon> list = xcIconService.selectXcIconList(xcIcon);
        return getDataTable(list);
    }

    /**
     * 导出icon设置列表
     */
    @Log(title = "icon设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XcIcon xcIcon) {
        List<XcIcon> list = xcIconService.selectXcIconList(xcIcon);
        ExcelUtil<XcIcon> util = new ExcelUtil<XcIcon>(XcIcon.class);
        util.exportExcel(response, list, "icon设置数据");
    }

    /**
     * 获取icon设置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(xcIconService.selectXcIconById(id));
    }

    /**
     * 新增icon设置
     */
    @Log(title = "icon设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody XcIcon xcIcon) {
        int n = xcIconService.insertXcIcon(xcIcon);
        return toAjax(n);
    }

    /**
     * 修改icon设置
     */
    @Log(title = "icon设置", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public AjaxResult edit(@RequestBody XcIcon xcIcon) {
        return toAjax(xcIconService.updateXcIcon(xcIcon));
    }



    @Log(title = "启用禁用icon展示", businessType = BusinessType.UPDATE)
    @PostMapping("/switchStatus")
    public AjaxResult switchStatus(@RequestBody XcIcon icon) {
        return toAjax(xcIconService.updateXcIcon(icon));
    }


    @Log(title = "置顶应用", businessType = BusinessType.UPDATE)
    @PostMapping("/switchStatus/top")
    public AjaxResult switchStatusTop(@RequestBody XcIcon icon) {

        return toAjax(xcIconService.updateXcIcon(icon));
    }

    /**
     * 删除icon设置
     */
    @Log(title = "icon设置", businessType = BusinessType.DELETE)
    @GetMapping("/removeIcon")
    public AjaxResult remove(@RequestParam String ids) {
        return toAjax(xcIconService.deleteXcIconByIds(ids));
    }

    /**
     * 上架第三方应用
     *
     * @param xcIcon
     * @return
     */
    @PostMapping("/insert/tenantApp")
    public R<Boolean> insertTenantApp(@RequestBody XcIcon xcIcon) {

//        // 根据appid 查询第三方应用信息，并
//        XcOpenTenant tenant = openTenantService.selectXcOpenTenantByAppId(xcIcon.getAppId());
//        if (tenant == null) {
//            return R.ok(false, "数据不存在");
//        }
//        // 查询所有的主题
//        XcAppTheme theme = new XcAppTheme();
////        theme.setStatus(1L);
//        List<XcAppTheme> themeList = themeService.selectXcAppThemeList(theme);
//
//        for (XcAppTheme appTheme : themeList) {
//
//            XcIcon icon = new XcIcon();
//            if (StringUtils.isNotEmpty(tenant.getTypeId())) {
//                icon.setClassify(Integer.valueOf(tenant.getTypeId()));
//            }
//            icon.setGoType(0L);
//            icon.setGoTo(tenant.getProdLink());
//            icon.setUrl(tenant.getLogo());
//            if ("inkWind".equals(appTheme.getCode())) {
//                icon.setUrl(tenant.getInkLogo());
//            } else if ("newYear".equals(appTheme.getCode())) {
//                icon.setUrl(tenant.getRedLogo());
//            }
//            icon.setName(tenant.getAppName());
//            icon.setThemeCode(appTheme.getCode());
//            icon.setStatus(1L);
//            icon.setAppId(tenant.getAppId());
//            xcIconService.insertXcIcon(icon);
//            // 存入ES
//            reactiveElasticsearchTemplate.save(icon).subscribe();
//
//        }

        return R.ok(true);
    }

    /**
     * 下架第三方应用
     *
     * @param
     * @return
     */
    @PostMapping("/delete/tenantApp")
    public R<Boolean> deleteTenantApp(@RequestBody Map<String, String> map) {

//        String ids = map.get("ids");
//
//        if (map.isEmpty()) {
//            return R.fail();
//        }
//
//        // 根据appid 查询第三方应用信息，并
//        List<XcOpenTenant> tenantList = openTenantService.selectXcOpenTenantByIds(ids);
//
//        for (XcOpenTenant tenant : tenantList) {
//
//            // 根据appId 查询 icon信息 并从es中删除
//            XcIcon xcIcon = new XcIcon();
//            xcIcon.setAppId(tenant.getAppId());
//            List<XcIcon> iconList = xcIconService.selectXcIconList(xcIcon);
//
//            for (XcIcon icon : iconList) {
//                reactiveElasticsearchTemplate.delete(icon.getId() + "", IndexCoordinates.of("icon")).subscribe();
//                xcIconService.deleteXcIconById(icon.getId());
//            }
//
//        }

        return R.ok(true);
    }


    /**
     * 查询icon列表对外接口
     */
    @GetMapping("/out/list")
    public R<List<XcIcon>> iconList() {
        XcIcon xcIcon = new XcIcon();
        xcIcon.setStatus(1L);
        List<XcIcon> list = xcIconService.selectXcIconList(xcIcon);
        return R.ok(list);
    }
}
