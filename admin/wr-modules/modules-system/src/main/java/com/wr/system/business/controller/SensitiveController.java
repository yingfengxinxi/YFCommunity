package com.wr.system.business.controller;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.Sensitive;
import com.wr.system.business.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 敏感词管理
 * @Date 2022/9/19 11:39
 * @Author DIANWEI
 **/
@RestController
@RequestMapping("/sensitive")
public class SensitiveController extends BaseController {

    @Autowired
    private SensitiveService sensitiveService;

    @RequiresPermissions("system:sensitive:list")
    @GetMapping("/list")
    public TableDataInfo list(Sensitive sensitive) {
        startPage();
        List<Sensitive> list = sensitiveService.getListBusSensitive(sensitive);
        return getDataTable(list);
    }

    /**
     * 新增
     */
    @RequiresPermissions("system:sensitive:add")
    @Log(title = "敏感词管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sensitive sensitive) {
        if (UserConstants.NOT_UNIQUE.equals(sensitiveService.checkPhrasesUnique(sensitive))) {
            return AjaxResult.error("新增敏感词'" + sensitive.getPhrases() + "'失败，敏感词已存在");
        }
        sensitive.setCreateBy(SecurityUtils.getUsername());
        return toAjax(sensitiveService.insertBusSensitive(sensitive));
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @RequiresPermissions("system:sensitive:query")
    @GetMapping(value = "/{sensitiveId}")
    public AjaxResult getInfo(@PathVariable Long sensitiveId) {

        return AjaxResult.success(sensitiveService.selectSensitiveById(sensitiveId));
    }

    /**
     * 修改
     */
    @RequiresPermissions("system:sensitive:edit")
    @Log(title = "敏感词管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sensitive sensitive) {
        if (UserConstants.NOT_UNIQUE.equals(sensitiveService.checkPhrasesUnique(sensitive))) {
            return AjaxResult.error("修改敏感词'" + sensitive.getPhrases() + "'失败，敏感词已存在");
        }
        sensitive.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(sensitiveService.updateBusSensitive(sensitive));
    }

    /**
     * 删除
     */
    @RequiresPermissions("system:sensitive:remove")
    @Log(title = "敏感词管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sensitiveIds}")
    public AjaxResult remove(@PathVariable Long[] sensitiveIds) {

        return toAjax(sensitiveService.deleteSensitiveByIds(sensitiveIds));
    }
}
