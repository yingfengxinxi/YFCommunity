package com.wr.api.payment.web;

import com.wr.api.payment.service.CommunityService;
import com.wr.api.payment.service.ParkService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 停车信息查询
 * @Author RainCity
 * @Date 2021-11-08 12:52:43
 */
@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ParkingController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ParkingController.class);

    private final ParkService parkService;
    private final CommunityService communityService;

    @GetMapping("/newest/{parkId}")
    public AjaxResult newestParking(@PathVariable("parkId") Long parkId){
        log.info("车库id===>{}",parkId);

        return parkService.getNewestVehicleLog(parkId);
    }

    @GetMapping("/wechat/{communityId}")
    public AjaxResult wechatConf(@PathVariable("communityId") Long communityId){
        log.info("小区id===>{}",communityId);
        List<CommunityWechat> wechatList = communityService.selectWechatByCommunityId(communityId);
        return AjaxResult.success(wechatList);
    }

    @GetMapping("/ali/{communityId}")
    public AjaxResult aliConf(@PathVariable("communityId") Long communityId){
        log.info("小区id===>{}",communityId);
        List<CommunityAli> wechatList = communityService.selectAliByCommunityId(communityId);
        return AjaxResult.success(wechatList);
    }

}
