package com.wr.api.owner.web;

import com.wr.api.owner.service.CommunityService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 11:49:34
 * @Desc: 小区支付配置查询
 */
@RestController
@RequestMapping("/v2/community")
public class CommunityController extends BaseController {
    @Autowired
    private CommunityService communityService;

    @GetMapping("/wechat/{communityId}")
    public AjaxResult wechatConf(@PathVariable("communityId") Long communityId){
        List<CommunityWechat> wechatList = communityService.selectWechatByCommunityId(communityId);
        return AjaxResult.success(wechatList);
    }

    @GetMapping("/ali/{communityId}")
    public AjaxResult aliConf(@PathVariable("communityId") Long communityId){
        List<CommunityAli> aliList = communityService.selectAliByCommunityId(communityId);
        return AjaxResult.success(aliList);
    }

    /**
     * 获取所有小区
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult list(){
        List<Community> communityAll = communityService.getCommunityAll();
        return AjaxResult.success(communityAll);
    }
}
