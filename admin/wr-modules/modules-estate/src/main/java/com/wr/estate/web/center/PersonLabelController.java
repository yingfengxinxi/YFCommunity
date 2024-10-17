package com.wr.estate.web.center;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.PersonLabelService;
import com.wr.remote.estate.center.PersonLabel;
import com.wr.remote.estate.center.vo.PersonLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:51:03
 * @Desc:
 */
@RestController
@RequestMapping("/personLabel")
public class PersonLabelController extends BaseController {
    @Autowired
    private PersonLabelService personLabelService;
    /**
     * 标签列表
     * @param
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("center:personLabel:list")
    public TableDataInfo list(PersonLabelVo personLabelVo){
        startPage();
        List<PersonLabelVo> list = personLabelService.selectPersonLabelList(personLabelVo);
        return getDataTable(list);
    }

    /**
     * 人员列表
     * @param
     * @return
     */
    @GetMapping("/nameList")
    @RequiresPermissions("center:personLabel:nameList")
    public TableDataInfo nameList(PersonLabelVo personLabelVo){
        List<PersonLabelVo> nameList = personLabelService.selectNameList(personLabelVo);
        return getDataTable(nameList);
    }

    /**
     * 新增标签数据
     *
     * @param personLabelVo 实体
     * @return 新增结果
     */
    @Log(title = "标签新增", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    @RequiresPermissions("center:personLabel:add")
    public AjaxResult insert(@RequestBody PersonLabelVo personLabelVo) {
        if (UserConstants.NOT_UNIQUE.equals(personLabelService.checkUnique(personLabelVo.getNameId())))
        {
            return AjaxResult.error("此人员已存在标签,请勿重复添加");
        }
        String username = SecurityUtils.getUsername();
        personLabelVo.setCreateBy(username);
        return toAjax(personLabelService.insertPersonLabel(personLabelVo));
    }

    /**
     * 修改数据
     *
     * @param personLabel 实体
     * @return 修改结果
     */
    @Log(title = "标签修改", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @RequiresPermissions("center:personLabel:update")
    public AjaxResult update(@RequestBody PersonLabel personLabel) {
        String username = SecurityUtils.getUsername();
        personLabel.setUpdateBy(username);
        return toAjax(personLabelService.updatePersonLabel(personLabel));
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{labelId}")
    @RequiresPermissions("center:personLabel:query")
    public AjaxResult getInfo(@PathVariable("labelId") Long labelId) {
        return AjaxResult.success(personLabelService.selectPersonLabelByLabelId(labelId));
    }
    /*@GetMapping(value = "/getInfoById")
    public AjaxResult getInfo(PersonLabelVo personLabelVo) {
        return AjaxResult.success(personLabelService.selectPersonLabelByLabelId(personLabelVo));
    }*/

    /**
     * 删除标签
     */
    //@RequiresPermissions("system:user:remove")
    @Log(title = "标签删除", businessType = BusinessType.DELETE)
    @DeleteMapping("delete/{labelIds}")
    @RequiresPermissions("center:personLabel:delete")
    public AjaxResult remove(@PathVariable("labelIds") Long[] labelIds)
    {
        return toAjax(personLabelService.deletePersonLabelByLabelIds(labelIds));
    }
}
