package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.remote.domain.RentalVo;
import com.wr.api.owner.service.serve.RentalService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 09:11:13
 * @Desc: 出租屋
 */
@RestController
@RequestMapping("/v2/rental")
public class RentalController extends BaseController {
    @Autowired
    private RentalService rentalService;

    /**
     * 获取所有在租的出租信息
     *
     * @param rentalVo
     * @return
     */
    @GetMapping("/rentalList")
    @AuthRequired
    public TableDataInfo getRental(RentalVo rentalVo) {
        if (ObjectUtils.isEmpty(rentalVo)) {
            return new TableDataInfo();
        }
        startPage();
        List<RentalVo> rental = rentalService.getRental(rentalVo);
        return getDataTable(rental);
    }


    /**
     * 获取我得房屋出租信息
     *
     * @param rentalVo
     * @return
     */
    @GetMapping("/rentalMyList")
    @AuthRequired
    public TableDataInfo getMyRental(RentalVo rentalVo) {
        if (ObjectUtils.isEmpty(rentalVo)) {
            return new TableDataInfo();
        }
        startPage();
        List<RentalVo> rental = rentalService.getMyRental(rentalVo);
        return getDataTable(rental);
    }

    /**
     * 我要出租
     *
     * @param rentalVo
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    @ApiLog(title = "新增我得出租屋", businessType = BusinessType.INSERT)
    public AjaxResult insertRenTal(@RequestBody(required = false) RentalVo rentalVo) {
        return rentalService.insertRenTal(rentalVo);
    }

    /**
     * 获取我得房屋出租详细信息
     *
     * @param rentalVo
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult getRentalInfo(RentalVo rentalVo) {
        List<RentalVo> rental = rentalService.getRentalInfo(rentalVo.getRentId());
        return AjaxResult.success(rental);
    }


    /**
     * 小区树
     *
     * @param
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getTreeSelects(Long ownerId) {
        //小区
        List<TreeSelectVo> communityTree = rentalService.getCommunityTree(ownerId);
        communityTree.forEach(c -> {
            c.setFlag("0");
            //楼栋
            List<TreeSelectVo> buildingTree = rentalService.getBuildingTree(c.getId(), ownerId);
            c.setChildren(buildingTree);
            buildingTree.forEach(b -> {
                b.setFlag("1");
                //单元
                List<TreeSelectVo> unitTree = rentalService.getUnitTree(b.getId(), ownerId);
                b.setChildren(unitTree);
                unitTree.forEach(u -> {
                    u.setFlag("2");
                    //房间号
                    List<TreeSelectVo> roomTree = rentalService.getRoomTree(u.getId(), ownerId);
                    u.setChildren(roomTree);
                    roomTree.forEach(r -> {
                        r.setFlag("3");
                    });
                });
            });
        });
        return AjaxResult.success(communityTree);
    }

    /**
     * 出租下架、已出售
     * @param handId
     * @param rentStatus
     * @return: com.wr.common.core.web.domain.AjaxResult
     * @date: 2023/4/20 16:59
     * @author: SJiang
     **/
    @GetMapping("/outStack")
    @AuthRequired
    public AjaxResult outStack(Long handId, String rentStatus) {
        if (ObjectUtils.allNull(handId)){
            return AjaxResult.error("参数不能为空！");
        }
        if (ObjectUtils.allNull(rentStatus)){
            return AjaxResult.error("参数不能为空！");
        }
        Integer num = rentalService.outStack(handId, rentStatus);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
