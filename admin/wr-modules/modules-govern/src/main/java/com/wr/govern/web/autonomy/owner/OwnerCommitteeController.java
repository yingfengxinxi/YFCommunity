package com.wr.govern.web.autonomy.owner;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.autonomy.owner.OwnerCommitteeService;
import com.wr.remote.govern.autonomy.owner.OwnerCommittee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 业主委员会信息
 *
 * @author liubei
 * @createDate 2022/10/26
 */
@RestController
@RequestMapping("/ownerCommittee")
public class OwnerCommitteeController extends BaseController {
    @Autowired
    private OwnerCommitteeService ownerCommitteeService;

    /**
     * 获取业主委员会信息列表
     */
    @RequiresPermissions("govern:ownerCommittee:list")
    @GetMapping("/list")
    public TableDataInfo list(OwnerCommittee ownerCommittee) {
        startPage();
        List<OwnerCommittee> depts = ownerCommitteeService.selectBusCommitteeList(ownerCommittee);
        return getDataTable(depts);
    }

    /**
     * 查询委员会信息
     *
     * @return
     */
    @RequiresPermissions("govern:ownerCommittee:query")
    @GetMapping(value = "/queryCommit/{committeeId}")
    public AjaxResult getInfo(@PathVariable("committeeId")Long committeeId) {
        return AjaxResult.success(ownerCommitteeService.selectBusCommitteeById(committeeId));
    }

    /**
     * 新增业主委员会
     */
    @RequiresPermissions("govern:ownerCommittee:add")
    @Log(title = "业主委员会管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody OwnerCommittee ownerCommittee) {

        ownerCommittee.setCreateBy(SecurityUtils.getUsername());
        int i=ownerCommitteeService.insertBusCommittee(ownerCommittee);
        return i > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("社区已添加委员会，请勿重新添加！");
    }

    /**
     * 委员会绑定业主
     *
     * @param ownerCommittee
     * @return
     */
    @RequiresPermissions("govern:ownerCommittee:bind")
    @Log(title = "业主委员会绑定", businessType = BusinessType.INSERT)
    @PostMapping("/bind")
    public AjaxResult bind(@Validated @RequestBody OwnerCommittee ownerCommittee) {
        int i= ownerCommitteeService.bind(ownerCommittee);
        return i > 0 ? AjaxResult.success("绑定成功") : AjaxResult.error("业主已绑定，请勿重复绑定！");
    }


    /**
     * 修改业主委员会
     */
    @RequiresPermissions("govern:ownerCommittee:edit")
    @Log(title = "业主委员会管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody OwnerCommittee ownerCommittee) {
        ownerCommittee.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(ownerCommitteeService.updateBusCommittee(ownerCommittee));
    }


    /**
     * 查询业主信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("govern:ownerCommittee:queryOwner")
    @GetMapping("queryOwner")
    public TableDataInfo query(@RequestParam Map<String, Object> params) {
        startPage();
        return getDataTable(ownerCommitteeService.queryByCommunityId(params));
    }

    /**
     * 查询业主列表
     *
     * @param params
     * @return
     */
/*    @RequiresPermissions("govern:ownerCommittee:queryList")*/
    @GetMapping("queryList")
    public TableDataInfo queryList(@RequestParam Map<String, Object> params) {
        startPage();
        return getDataTable(ownerCommitteeService.queryByCommunityIds(params));
    }

    /**
     * 查询房间下的人员信息
     *
     * @param
     * @return
     */
    @GetMapping("queryByRoomId")
    public AjaxResult queryByRoomId(@RequestParam Map<String, Object> params) {

        return AjaxResult.success(ownerCommitteeService.queryByRoomId(params));
    }

    /**
     * 查询业主房产信息
     *
     * @param
     * @return
     */
    @GetMapping("queryRoomByOwnerId")
    public AjaxResult queryRoomByOwnerId(@RequestParam Long id) {
        return AjaxResult.success(ownerCommitteeService.queryRoomByOwnerId(id));
    }


    @RequiresPermissions("govern:ownerCommittee:delete")
    @Log(title = "删除业主委员会绑定业主信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ownerId}")
    public AjaxResult deleteOwner(@PathVariable("ownerId") Long[] ownerId) {
        return toAjax(ownerCommitteeService.deleteOwner(ownerId));
    }

    @RequiresPermissions("govern:ownerCommittee:remove")
    @Log(title = "删除业主委员会绑定业主信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeCommittee/{committeeId}")
    public AjaxResult removeCommittee(@PathVariable("committeeId") Long[] committeeId) {
        return toAjax(ownerCommitteeService.deleteBusCommitteeById(committeeId));
    }

    /**
     * 字典证件
     * @return
     */
    @GetMapping("queryDict")
    public AjaxResult queryDict(){

        return AjaxResult.success(ownerCommitteeService.getDictData());
    }

}
