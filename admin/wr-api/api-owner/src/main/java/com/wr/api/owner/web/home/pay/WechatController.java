package com.wr.api.owner.web.home.pay;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.owner.service.estateapi.pay.PayService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.dto.OpenDTO;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.remote.domain.CommunityWechat;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/2/25 14:11
 */
@RestController
@RequestMapping("/estateapi/wechat")
@Api(tags = "业主端-获取openid")
public class WechatController extends BaseController {

    @Autowired
    private PayService payService;

    @PostMapping("getOpenId")
    public AjaxResult getOpenId(@RequestBody OpenDTO openDTO) {
        List<CommunityWechat> communityWechatList = payService.selectWechatById(openDTO.getCommunityId(), null);
        if (CollectionUtils.isEmpty(communityWechatList)) {

            return AjaxResult.error("当前小区未配置支付账号,获取openid失败");
        }
        CommunityWechat communityWechat = communityWechatList.get(0);
        Map<String, String> map = WechatPayUtils.getOpenId(communityWechat.getOwnerAppId(), communityWechat.getOwnerAppSecret(), openDTO.getCode());
        AjaxResult ajaxResult = new AjaxResult();
        if (StringUtils.equals(map.get("code"), "200")) {
            ajaxResult.put("code", 200);
            ajaxResult.put("openId", map.get("openId"));
            return ajaxResult;
        } else {
            return AjaxResult.error("获取openid失败");
        }

    }
}
