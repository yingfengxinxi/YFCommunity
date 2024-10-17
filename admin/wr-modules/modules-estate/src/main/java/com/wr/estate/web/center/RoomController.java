package com.wr.estate.web.center;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.entity.vo.RoomImportVo;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.center.BuildingService;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.customer.decorate.DecorationService;
import com.wr.remote.estate.center.vo.*;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.customer.decorate.BusDecorate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 15:37:58
 * @Desc:
 */
@RestController
@RequestMapping("/room")
@Api(tags = "物管平台-房屋管理")
public class RoomController extends BaseController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private DecorationService decorationService;

    /**
     * 树列表
     *
     * @return
     */
    @GetMapping("/treeRoom")
    public AjaxResult treeRoom() {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long estateId = sysUser.getEstateId();
        return AjaxResult.success(communityService.treeRoom(estateId));
    }

    /**
     * 分页列表
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/11 15:41
     */
    @GetMapping("/listPageRooms")
    public TableDataInfo listPageRooms(RoomQueryVO query) {
        startPage();
        query.setFlag(0);
        List<RoomVO> roomVOList = roomService.listRooms(query);
        if(CollectionUtils.isNotEmpty(roomVOList)){
            roomVOList.stream().forEach(roomVO -> {
                roomVO.setOwnerName(DesensitizationUtils.name(roomVO.getOwnerName()));
                roomVO.setOwnerPhone(DesensitizationUtils.phone(roomVO.getOwnerPhone()));
            });
        }
        return getDataTable(roomVOList);
    }

    @GetMapping("/getVoteListRooms")
    public TableDataInfo getVoteListRooms(RoomQueryVO query) {
        startPage();
        return getDataTable(roomService.getVoteListRooms(query));
    }


    /**
     * 办理装修列表
     *
     * @param query
     * @return
     */
    @GetMapping("/deListPageRooms")
    public TableDataInfo deListPageRooms(RoomQueryVO query) {
        startPage();
        query.setFlag(1);
        return getDataTable(roomService.listRooms(query));
    }

    /**
     * 过户查询业主信息
     *
     * @param query 查询条件
     * @return com.wr.common.core.web.page.TableDataInfo
     * @author yx
     * @date 2022/10/15 13:51
     */
    @GetMapping("/roomSearchListPage")
    public TableDataInfo roomSearchListPage(BusOwner query) {
        startPage();
        return getDataTable(roomService.roomSearchListPage(query));
    }

    /**
     * 根据条件查询所有房号
     *
     * @param query 查询条件
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 19:57
     */
    @GetMapping("/listRoomNamesByCondition")
    public AjaxResult listRoomNamesByCondition(RoomQueryVO query) {
        return AjaxResult.success(roomService.listRoomNamesByCondition(query));
    }

    /**
     * 查询业主信息以及家庭成员列表
     *
     * @param roomId 房屋id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/17 17:26
     */
    @GetMapping("/roomFamilyList/{roomId}")
    public AjaxResult roomFamilyList(@PathVariable("roomId") Long roomId) {
        return AjaxResult.success(roomService.roomFamilyList(roomId));
    }

    /**
     * 根据小区id查询单元列表
     *
     * @param communityId 社区id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 20:36
     */
    @GetMapping("/listUnitsByCommunityId/{communityId}")
    public AjaxResult listUnitsByCommunityId(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(roomService.listUnitsByCommunityId(communityId));
    }

    /**
     * 列表
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/11 15:44
     */
    @GetMapping("listRooms")
    public AjaxResult listRooms(@RequestBody RoomQueryVO query) {
        query.setFlag(0);
        roomService.listRooms(query);
        return AjaxResult.success();
    }

    /**
     * 根据条件查询图标数据
     *
     * @param query 查询条件
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/14 15:18
     */
    @GetMapping("roomGraphData")
    public AjaxResult roomGraphData(RoomQueryVO query) {
        return AjaxResult.success(roomService.roomGraphData(query));
    }

    /**
     * 获取房子详情
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/11 15:44
     */
    @ApiOperation("房屋详情")
    @GetMapping("/getRoomDetail/{id}/{communityId}")
    public AjaxResult getRoomDetail(@PathVariable("id") Long id,@PathVariable("communityId")Long communityId) {
        return AjaxResult.success(roomService.getDetailById(id,communityId));
    }

    /**
     * 批量新增楼栋
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/11 15:41
     */
    @Log(title = "批量新增楼栋", businessType = BusinessType.INSERT)
    @RequiresPermissions("manager:room:addBuildingBatch")
    @PostMapping("insertBuildingBatch")
    public AjaxResult insertBuildingBatch(@RequestBody BuildingInsertVO insertVO) {
        buildingService.insertBatch(insertVO);
        return AjaxResult.success();
    }

    /**
     * 修改楼栋信息
     *
     * @param updateVO 修改信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 13:58
     */
    @Deprecated
    @Log(title = "修改楼栋信息", businessType = BusinessType.UPDATE)
    @PostMapping("updateBuilding")
    public AjaxResult updateBuilding(@RequestBody BuildingUpdateVO updateVO) {
        buildingService.updateBuildingById(updateVO);
        return AjaxResult.success();
    }

    /**
     * @param communityId
     * @return
     */
    @GetMapping("getCommunityIdBuildingName")
    public AjaxResult getCommunityIdBuildingName(@RequestParam("communityId") Long communityId) {

        return AjaxResult.success(buildingService.getCommunityIdBuildingName(communityId));
    }

    /**
     * 删除楼栋信息
     *
     * @param buildingId 楼栋信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 14:43
     */
    @Log(title = "删除楼栋", businessType = BusinessType.DELETE)
    @RequiresPermissions("manager:room:deleteBuilding")
    @DeleteMapping("deleteBuilding/{buildingId}")
    public AjaxResult deleteBuilding(@PathVariable("buildingId") Long buildingId) {
        buildingService.delete(buildingId);
        return AjaxResult.success();
    }

    /**
     * 批量新增单元和房间
     *
     * @param insertVO 新增参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/12 16:05
     */
    @Log(title = "批量新增单元和房间", businessType = BusinessType.INSERT)
    @ApiOperation("批量新增单元和房间")
    @RequiresPermissions("manager:room:addUnitAndRoomBatch")
    @PostMapping("insertUnitAndRoomBatch")
    public AjaxResult insertUnitAndRoomBatch(@RequestBody UnitAndRoomInsertVO insertVO) {
        roomService.insertUnitAndRoomBatch(insertVO);
        return AjaxResult.success();
    }

    /**
     * 修改单元信息
     *
     * @param updateVO 修改信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 14:03
     */
    @Deprecated
    @Log(title = "修改单元", businessType = BusinessType.UPDATE)
    @PostMapping("updateUnit")
    public AjaxResult updateUnit(@RequestBody UnitUpdateVO updateVO) {
        roomService.updateUnitById(updateVO);
        return AjaxResult.success();
    }

    /**
     * 删除单元信息
     *
     * @param unitId 单元信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 14:43
     */
    @Log(title = "删除单元信息", businessType = BusinessType.DELETE)
    @RequiresPermissions("manager:room:deleteUnit")
    @DeleteMapping("deleteUnit/{unitId}")
    public AjaxResult deleteUnit(@PathVariable("unitId") Long unitId) {
        roomService.deleteUnit(unitId);
        return AjaxResult.success();
    }

    /**
     * 批量新增房间
     *
     * @param insertVO 新增参数
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 17:01
     */
    @Log(title = "批量新增房间", businessType = BusinessType.INSERT)
    @RequiresPermissions("manager:room:addRoomBatch")
    @PostMapping("insertRoomBatch")
    public AjaxResult insertRoomBatch(@RequestBody RoomInsertVO insertVO) {
        roomService.insertRoomBatch(insertVO);
        return AjaxResult.success();
    }

    /**
     * 批量删除房屋信息
     *
     * @param ids
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 21:44
     */
    @Log(title = "批量删除房屋", businessType = BusinessType.DELETE)
    @RequiresPermissions("manager:room:deleteRoomBatch")
    @DeleteMapping("deleteRoomBatch/{ids}")
    public AjaxResult deleteRoomBatch(@PathVariable("ids") List<Long> ids) {
        roomService.deleteRoomBatch(ids);
        return AjaxResult.success();
    }

    /**
     * 批量修改房屋
     *
     * @param updateVO 更新数据
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/13 21:20
     */
    @Log(title = "批量修改房屋", businessType = BusinessType.UPDATE)
    @RequiresPermissions("manager:room:updateRoomBatch")
    @PostMapping("updateRoomBatch")
    public AjaxResult updateRoomBatch(@RequestBody RoomUpdateBatchVO updateVO) {
        roomService.updateRoomBatch(updateVO);
        return AjaxResult.success();
    }

    /**
     * 修改房屋
     *
     * @param updateVO 修改数据
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/14 10:27
     */
    @Log(title = "修改房屋", businessType = BusinessType.UPDATE)
    @ApiOperation("修改房屋信息")
    @RequiresPermissions("manager:room:updateRoom")
    @PostMapping("updateRoom")
    public AjaxResult updateRoom(@RequestBody RoomUpdateVO updateVO) {
        roomService.updateRoom(updateVO);
        return AjaxResult.success();
    }


    /**
     * 新增房间
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/11 15:41
     */
    @PostMapping("addRoom")
    public AjaxResult addRoom() {
        return AjaxResult.success();
    }

    /**
     * 过户
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/17 9:33
     */
    @Log(title = "过户", businessType = BusinessType.OTHER)
    @RequiresPermissions("manager:room:transferOwnership")
    @GetMapping("transferOwnership")
    public AjaxResult transferOwnership(Long roomId, Long ownerId, String licenseUrl) {
        roomService.transferOwnership(roomId, ownerId, licenseUrl);
        return AjaxResult.success();
    }

    /**
     * 收房
     *
     * @param roomId 房屋id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author fq
     * @date 2023/08/26 14:43
     */
    @Log(title = "收房", businessType = BusinessType.UPDATE)
    @GetMapping("/confirmRoom/{roomId}")
    public AjaxResult confirmRoom(@PathVariable("roomId") Long roomId) {
        roomService.confirmRoom(roomId);
        return AjaxResult.success();
    }

    /**
     * @param unitId
     * @return
     */
    @GetMapping("getUnitDetailById")
    public AjaxResult getUnitDetailById(@RequestParam("unitId") Long unitId) {

        return AjaxResult.success(roomService.getUnitDetailById(unitId));
    }

    /**
     * @param unitId
     * @return
     */
    @GetMapping("getByUnitIdDetail")
    public AjaxResult getByUnitIdDetail(@RequestParam("unitId") Long unitId) {

        return AjaxResult.success(roomService.getByUnitIdDetail(unitId));
    }


    @Log(title = "导出房屋登记模板", businessType = BusinessType.EXPORT)
    @ApiOperation("导出房屋登记模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<RoomImportVo> util = new ExcelUtil<>(RoomImportVo.class);
        util.importTemplateExcel(response, "导出房屋登记模板");

    }

    /**
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "导入房屋", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, @RequestParam("communityId") Long communityId) throws Exception {
        ExcelUtil<RoomImportVo> util = new ExcelUtil<RoomImportVo>(RoomImportVo.class);
        List<RoomImportVo> userList = util.importExcel(file.getInputStream());
        String message = roomService.importRoom(userList, communityId);
        return success(message);
    }

    /**
     * @param roomIds
     * @param roomStatus
     * @return
     */
    @ApiOperation("批量修改状态")
    @GetMapping("bachUpdateRoomStatus")
    public AjaxResult bachUpdateRoomStatus(@RequestParam("roomIds") Long[] roomIds, @RequestParam("roomStatus") String roomStatus) {
        for (Long roomId : roomIds) {
            String status = roomService.getByRoomIdRoomStatus(roomId);
            if (!StringUtils.equals(status, "3") && !StringUtils.equals(status, "5")) {
                roomService.updateByRoomIdRoomStatus(roomId, roomStatus);
            }
        }
        return AjaxResult.success();
    }
}
