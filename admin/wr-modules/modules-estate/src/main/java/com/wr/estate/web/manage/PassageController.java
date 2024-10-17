package com.wr.estate.web.manage;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.manage.PassageSerive;
import com.wr.remote.estate.manage.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-13 14:36:06
 * @Desc:
 */
@RestController
@RequestMapping("/passage")
public class PassageController extends BaseController {

    @Autowired
    private PassageSerive passageSerive;

    /**
     * 出入口列表
     * @param
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("manage:passage:list")
    public TableDataInfo list(Passage passage){
        startPage();
        List<Passage> list = passageSerive.selectPassageList(passage);
        return getDataTable(list);
    }

    /**
     * 新增出入口数据
     *
     * @param passage 实体
     * @return 新增结果
     */
    @Log(title = "出入口新增", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    @RequiresPermissions("manage:passage:add")
    public AjaxResult insert(@RequestBody Passage passage) {
        if(null ==  passage.getCommunityId()){
            return AjaxResult.error("请选择小区");
        }
        if (UserConstants.NOT_UNIQUE.equals(passageSerive.checkPassageNameUnique(passage)))
        {
            return AjaxResult.error("新增出入口'" + passage.getPassageName() + "'失败，出入口名称已存在");
        }
        String username = SecurityUtils.getUsername();
        passage.setCreateBy(username);
        return toAjax(passageSerive.insertPassage(passage));
    }

    /**
     * 修改数据
     *
     * @param passage 实体
     * @return 修改结果
     */
    @Log(title = "出入口修改", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @RequiresPermissions("manage:passage:update")
    public AjaxResult update(@RequestBody Passage passage) {
        if (UserConstants.NOT_UNIQUE.equals(passageSerive.checkPassageNameUnique(passage)))
        {
            return AjaxResult.error("修改出入口'" + passage.getPassageName() + "'失败，出入口名称已存在");
        }
        String username = SecurityUtils.getUsername();
        passage.setUpdateBy(username);
        return toAjax(passageSerive.updatePassage(passage));
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{passageId}")
    @RequiresPermissions("manage:passage:detail")
    public AjaxResult getInfo(@PathVariable("passageId") Long passageId) {
        return AjaxResult.success(passageSerive.selectPassageById(passageId));
    }

    /**
     * 删除出入口
     */
    //@RequiresPermissions("system:user:remove")
    @Log(title = "删除出入口", businessType = BusinessType.DELETE)
    @DeleteMapping("delete/{passageIds}")
    @RequiresPermissions("manage:passage:delete")
    public AjaxResult remove(@PathVariable Long[] passageIds)
    {
        return toAjax(passageSerive.deletePassageByIds(passageIds));
    }


    /**
     *
     * @param passage 筛选条件
     * @return 查询结果
     */
    @GetMapping("/allList")
    @RequiresPermissions("manage:passage:allList")
    public AjaxResult allList(Passage passage){
        return AjaxResult.success(passageSerive.selectPassageList(passage));
    }
}
