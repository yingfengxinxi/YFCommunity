package com.wr.api.owner.web.home.icon;

import com.wr.api.owner.entity.home.icon.XcIconType;
import com.wr.api.owner.service.home.icon.IXcIconTypeService;
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

/**
 * icon分类Controller
 *
 * @author ruoyi
 * @date 2023-03-25
 */
@RestController
@RequestMapping("/icon/type")
public class XcIconTypeController extends BaseController {
    @Autowired
    private IXcIconTypeService xcIconTypeService;

    /**
     * 查询icon分类列表
     */
    @GetMapping("/list")
    public TableDataInfo list(XcIconType xcIconType) {
        startPage();
        List<XcIconType> list = xcIconTypeService.selectXcIconTypeList(xcIconType);
        return getDataTable(list);
    }

    @GetMapping("/list/all")
    public R listAll(XcIconType xcIconType) {
        List<XcIconType> list = xcIconTypeService.selectXcIconTypeList(xcIconType);
        return R.ok(list);
    }

    /**
     * 导出icon分类列表
     */
    @Log(title = "icon分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XcIconType xcIconType) {
        List<XcIconType> list = xcIconTypeService.selectXcIconTypeList(xcIconType);
        ExcelUtil<XcIconType> util = new ExcelUtil<XcIconType>(XcIconType.class);
        util.exportExcel(response, list, "icon分类数据");
    }

    /**
     * 获取icon分类详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(xcIconTypeService.selectXcIconTypeById(id));
    }

    /**
     * 新增icon分类
     */
    @Log(title = "icon分类", businessType = BusinessType.INSERT)
    @PostMapping("/saveIconType")
    public AjaxResult saveIconType(@RequestBody XcIconType xcIconType) {
        return toAjax(xcIconTypeService.insertXcIconType(xcIconType));
    }

    /**
     * 修改icon分类
     */
    @Log(title = "icon分类", businessType = BusinessType.UPDATE)
    @PostMapping("/updateIconType")
    public AjaxResult updateIconType(@RequestBody XcIconType xcIconType) {
        return toAjax(xcIconTypeService.updateXcIconType(xcIconType));
    }

    /**
     * 删除icon分类
     */
    @Log(title = "icon分类", businessType = BusinessType.DELETE)
    @GetMapping("/removeIds")
    public AjaxResult removeIds(@RequestParam String ids) {
        return toAjax(xcIconTypeService.deleteXcIconTypeByIds(ids));
    }
}
