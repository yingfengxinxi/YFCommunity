package com.wr.estate.web.center.personnelfilemanage;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.vo.CarportImportVo;
import com.wr.estate.entity.vo.OwnerFileImportVo;
import com.wr.estate.service.center.personnelfilemanage.OwnerFileService;
import com.wr.remote.estate.center.vo.OwnerFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 17:20:41
 * @Desc: 业主档案
 */
@RestController
@RequestMapping("file/owner")
@Api(tags = "物管平台-业主档案")
public class OwnerFileController extends BaseController {

    @Resource
    private OwnerFileService ownerFileService;

    /**
     * 查询 业主
     *
     * @param
     * @return
     */
    @GetMapping("query")
    @ApiOperation("分页列表")
    @RequiresPermissions("center:ownerfile:query")
    public TableDataInfo query(@RequestParam Map<String, Object> params) {
        startPage();
        return getDataTable(ownerFileService.queryByCommunityId(params));
    }

    /**
     * 查询 房间下的人员
     *
     * @param
     * @return
     */
    @GetMapping("person")
    public AjaxResult queryByRoomId(@RequestParam Map<String, Object> params) {

        return AjaxResult.success(ownerFileService.queryByRoomId(params));
    }

    @ApiOperation("查询当前房间下是否存在业主本人信息")
    @GetMapping("getByRoomIdOwnerRelationCount")
    public AjaxResult getByRoomIdOwnerRelationCount(
            @RequestParam("roomId") Long roomId,
            @RequestParam("ownerRelation") String ownerRelation,
            @RequestParam(value = "ownerId", required = false) Long ownerId
    ) {
        if (ownerRelation.equals("0")) {
            //本人
            Integer relationCount = ownerFileService.getByRoomIdOwnerRelationCount(roomId, ownerId);
            if (relationCount >= 1) {
                return AjaxResult.success("0");
            }
        }
        return AjaxResult.success("1");
    }

    /**
     * 查询 业主房产
     *
     * @param
     * @return
     */
    @GetMapping("room")
    public AjaxResult queryRoomByOwnerId(@RequestParam Long id) {
        return AjaxResult.success(ownerFileService.queryRoomByOwnerId(id));
    }

    /**
     * @param ownerId
     * @return
     */
    @ApiOperation("业主档案详情")
    @GetMapping("getDetailByOwnerId")
    public AjaxResult getDetailByOwnerId(@RequestParam("ownerId") Long ownerId,@RequestParam("communityId") Long communityId) {

        return AjaxResult.success(ownerFileService.getDetailByOwnerId(ownerId,communityId));
    }

    /**
     * 查询 业主车位
     *
     * @param
     * @return
     */
    @GetMapping("car_port")
    public AjaxResult queryCarPortByOwnerId(@RequestParam Long id) {

        return AjaxResult.success(ownerFileService.queryCarPortByOwnerId(id));
    }

    /**
     * 查询 业主车辆
     *
     * @param
     * @return
     */
    @GetMapping("car")
    public AjaxResult queryCarByOwnerId(@RequestParam Long id) {

        return AjaxResult.success(ownerFileService.queryCarByOwnerId(id));
    }

    /**
     * 查询 业主的租客
     *
     * @param
     * @return
     */
    @GetMapping("tenant")
    public AjaxResult queryTenantByOwnerId(@RequestParam Long id) {

        return AjaxResult.success(ownerFileService.queryTenantByOwnerId(id));
    }

    /**
     * 新增
     *
     * @param params
     * @return
     */
    @RequiresPermissions("center:ownerfile:add")
    @Log(title = "新增业主", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@RequestBody Map<String, Object> params) {

        if (ownerFileService.getPhoneNum(String.valueOf(params.get("ownerPhone"))) > 0) {
            return AjaxResult.error("手机号已存在！！");
        }
        if (ownerFileService.getCardNoNum(String.valueOf(params.get("cardNo"))) > 0) {
            return AjaxResult.error("身份证号已存在！！");
        }
        return AjaxResult.success(ownerFileService.insert(params));
    }

    /**
     * 修改
     *
     * @param params
     * @return
     */
    @RequiresPermissions("center:ownerfile:update")
    @Log(title = "修改业主", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public AjaxResult update(@RequestBody Map<String, Object> params) {

        return AjaxResult.success(ownerFileService.update(params));
    }

    @RequiresPermissions("center:ownerfile:deleteds")
    @Log(title = "删除多个业主", businessType = BusinessType.DELETE)
    @PostMapping("deleteds")
    public AjaxResult deleteds(@RequestBody Long[] ids) {

        return AjaxResult.success(ownerFileService.deleteds(ids));
    }

    @RequiresPermissions("center:ownerfile:deleted")
    @Log(title = "删除单个业主", businessType = BusinessType.DELETE)
    @PostMapping("deleted/{id}")
    public AjaxResult deleted(@PathVariable Long id) {

        return AjaxResult.success(ownerFileService.deleted(id));
    }

    /**
     * 导出
     */
    @RequiresPermissions("center:ownerfile:export")
    @Log(title = "业主档案信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OwnerFileVo vop) throws IOException {


        Map<String, Object> params = new HashMap<>(6);
        params.put("ids", vop.getIds());
        params.put("communityId", vop.getCommunityId());
        params.put("name", vop.getName());
        params.put("phone", vop.getPhone());
        params.put("isOutIn", vop.getIsOutIn());
        params.put("isCar", vop.getIsCat());
        List<OwnerFileVo> voArrList = ownerFileService.queryByCommunityId(params);
        ExcelUtil<OwnerFileVo> util = new ExcelUtil(OwnerFileVo.class);
        util.exportExcel(response, voArrList, "业主档案数据");

    }

    /**
     * 字典证件
     *
     * @return
     */
    @GetMapping("queryDict")
    public AjaxResult queryDict() {

        return AjaxResult.success(ownerFileService.getDictData());
    }

    /**
     * 查出设备及人员列表
     *
     * @param communityId 小区id
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-05-18 16:15:14
     * @ver v1.0.0
     */
    @GetMapping("/getDevice/{communityId}")
    public AjaxResult getDevice(@PathVariable("communityId") Long communityId) {

        return ownerFileService.selectDeviceList(communityId);
    }

    /**
     * 人员授权
     *
     * @param authDto
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    @PostMapping("/auth")
    @RequiresPermissions("center:ownerfile:auth")
    public AjaxResult authOwner(@RequestBody AuthDto authDto) {

        return ownerFileService.authOwner(authDto);
    }


    @ApiOperation("导出模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<OwnerFileImportVo> util = new ExcelUtil<OwnerFileImportVo>(OwnerFileImportVo.class);
        util.importTemplateExcel(response, "业主信息");
    }


    @Log(title = "批量导入业主信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ApiOperation("批量导入业主信息")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<OwnerFileImportVo> util = new ExcelUtil<OwnerFileImportVo>(OwnerFileImportVo.class);
        List<OwnerFileImportVo> dataList = util.importExcel(file.getInputStream());
        String message = ownerFileService.importData(dataList, communityId);
        return AjaxResult.success(message);
    }


    /**
     * @return
     */
    @ApiOperation("查询所有业主")
    @GetMapping("getAllOwnerList")
    public AjaxResult getAllOwnerList(@RequestParam("communityId") Long communityId, @RequestParam("voteScopeId") String voteScopeId) {

        return AjaxResult.success(ownerFileService.getAllOwnerList(communityId, voteScopeId));
    }
}
