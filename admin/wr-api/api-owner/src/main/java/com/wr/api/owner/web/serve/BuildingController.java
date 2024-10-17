package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.ApiBuildingVo;
import com.wr.api.owner.service.serve.BuildingService;
import com.wr.common.core.business.annotation.AuthRequired;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 楼栋关联
 * @Author: lvzy
 * @Date: 2022-11-14 17:22:41
 * @Desc: 楼栋关联
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/Building")
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping("/buildingList")
    @AuthRequired
//    @EncryptResponse
    public List<ApiBuildingVo> buildingList(ApiBuildingVo apiBuildingVo) {
        return buildingService.buildingList(apiBuildingVo.getCommunityId());
    }

    @GetMapping("/unitList")
    @AuthRequired
//    @EncryptResponse
    public List<ApiBuildingVo> unitList(ApiBuildingVo apiBuildingVo) {
        return buildingService.unitList(apiBuildingVo.getBuildingId());
    }

    @GetMapping("/roomList")
    @AuthRequired
//    @EncryptResponse
    public List<ApiBuildingVo> roomList(ApiBuildingVo apiBuildingVo) {
        return buildingService.roomList(apiBuildingVo.getUnitId());
    }

    @GetMapping("/ownerList")
    @AuthRequired
    //    @EncryptResponse
    public List<ApiBuildingVo> ownerList(ApiBuildingVo apiBuildingVo) {
        return buildingService.ownerList(apiBuildingVo.getRoomId());
    }
}
