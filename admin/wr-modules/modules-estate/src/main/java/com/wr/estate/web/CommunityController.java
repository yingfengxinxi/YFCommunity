package com.wr.estate.web;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.CommunityService;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:05:01
 * @Desc: 查询当前登录人所在物业下的所有小区
 */
@RestController
@RequestMapping("/community")
@Api(tags = "查询当前登录人所在物业下的所有小区")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    /**
     * 查询当前登录人所在物业下的所有小区
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/current")
    public AjaxResult current() {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        return AjaxResult.success(communityService.selectCommunityList(sysUser.getEstateId()));
    }

    /**
     * 查询当前物业下的所有小区
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/currents/{estateId}")
    public AjaxResult currents(@PathVariable("estateId") Long estateId) {
        if (ObjectUtils.allNull(estateId)) {
            return AjaxResult.error("未获取到物业信息！");
        }
        return AjaxResult.success(communityService.selectCommunityLists(estateId));
    }

    @GetMapping("/getAllCommunityList")
    public AjaxResult getAllCommunityList() {
        return AjaxResult.success(communityService.selectAllCommunityList());
    }

    /**
     * 根据小区id查询楼栋
     *
     * @param communityId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/building/{communityId}")
    public AjaxResult building(@PathVariable("communityId") Long communityId) {

        return AjaxResult.success(communityService.selectBuildingList(communityId));
    }

    @ApiOperation("物业缴费楼栋下拉")
    @GetMapping("/getPropertyFeeBuilding/{communityId}")
    public AjaxResult getPropertyFeeBuilding(@PathVariable("communityId") Long communityId) {

        return AjaxResult.success(communityService.getPropertyFeeBuilding(communityId));
    }

    /**
     * 根据楼栋id查询单元
     *
     * @param buildingId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/unit/{buildingId}")
    public AjaxResult unit(@PathVariable("buildingId") Long buildingId) {

        return AjaxResult.success(communityService.selectUnitList(buildingId));
    }

    /**
     * 根据单元id查询房间
     *
     * @param unitId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/room/{unitId}")
    public AjaxResult room(@PathVariable("unitId") Long unitId) {

        return AjaxResult.success(communityService.selectRoomList(unitId));
    }

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询业主
     *
     * @param owner communityId 小区id
     *              buildingId 楼栋id
     *              unitId 单元id
     *              roomId 房间id
     *              至少一个
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/owner")
    public AjaxResult room(Owner owner) {

        return AjaxResult.success(communityService.selectOwnerList(owner));
    }

    /**
     * 根据小区id/楼栋id/单元id/房间id 查询租客
     *
     * @param tenant communityId 小区id
     *               buildingId 楼栋id
     *               unitId 单元id
     *               roomId 房间id
     *               至少一个
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/tenant")
    public AjaxResult room(Tenant tenant) {

        return AjaxResult.success(communityService.selectTenantList(tenant));
    }

    @GetMapping("/person")
    public AjaxResult person(Owner owner) {

        return AjaxResult.success(communityService.selectOwner(owner));
    }
}
