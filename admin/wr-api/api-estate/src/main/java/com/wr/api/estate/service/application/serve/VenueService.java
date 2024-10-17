package com.wr.api.estate.service.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.estate.entity.application.serve.VenueAppoint;
import com.wr.api.estate.entity.vo.application.serve.VenueAppointApiVo;
import com.wr.api.estate.entity.vo.application.serve.VenueVO;
import com.wr.common.core.web.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:25:15
 * @Desc:
 */
public interface VenueService {

    /**
     * 查询 场馆
     *
     * @param communityId
     * @param state
     * @return
     */
    List<VenueVO> searchByCommunityId(Long communityId, String state);

    /**
     * 详情
     *
     * @param venueId
     * @return
     */
    VenueVO detailById(Long venueId);

    /**
     * 活动类型
     *
     * @param communityId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchActByCommunityId(Long communityId);

    /**
     * 场馆使用规则
     *
     * @param communityId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchRuleByCommunityId(Long communityId);

    /**
     * 新增
     *
     * @param venueAppoint
     * @return
     */
    AjaxResult save(VenueAppoint venueAppoint);

    /**
     * 查询场馆的预约人
     *
     * @param venueId
     * @return
     */
    List<LinkedHashMap<String, Object>> searchAppoint(Long venueId, String time);

    /**
     * 支付宝 支付
     *
     * @param
     * @return
     */
    boolean updateAliPay(Map<String, String> params, JSONObject attach);

    /**
     * 微信 支付
     *
     * @param
     * @return
     */
    boolean updateWechatPay(HttpServletRequest request, JSONObject attach);

    /**
     * 查询场馆工单
     *
     * @param communityId
     * @return
     */
    List<VenueAppointApiVo> searchVenueAppoint(Long communityId);

    /**
     * 查看场馆工单详情
     *
     * @param appointId
     * @return
     */
    VenueAppointApiVo detailVenueAppoint(Long appointId);

    /**
     * 场馆工单终止
     *
     * @param appointId
     * @return
     */
    int terVenueAppoint(Long appointId);
}
