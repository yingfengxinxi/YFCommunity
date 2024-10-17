package com.wr.api.owner.service.home.pay;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.LiveDTO;
import com.wr.api.owner.entity.vo.pay.PropertyLogPageVO;
import com.wr.common.core.web.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:46:53
 * @Desc: 生活缴费业务层
 */
public interface LiveService {
    /**
     * 查询应缴金额
     * @param liveDTO 人员信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult getLiveFee(LiveDTO liveDTO);

    /**
     * 查询物业费缴费记录
     * @param liveDTO 登录信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult getPropertyLog(LiveDTO liveDTO);


    /**
     * 微信支付--添加物业缴费记录
     * @param request 请求数据
     * @param wxAttach 微信冗余参数
     * @return boolean
     */
    Boolean insertPropertyLog(HttpServletRequest request, JSONObject wxAttach);

    /**
     * 支付宝支付--添加物业缴费记录
     * @param params 支付宝参数
     * @param aliAttach 冗余参数
     * @return boolean
     */
    Boolean insertAliPayLog(Map<String, String> params, JSONObject aliAttach);

    /**
    * @Author SUNk
    * @Description 查询物业费详情
    * @Date 14:43 2024/3/30
    * @Param [logId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult findDetilProperty(Long logId);

    /**
    * @Author SUNk
    * @Description 查询物业费列表详情
    * @Date 11:05 2024/4/1
    * @Param [liveDTO]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    List<PropertyLogPageVO> findListPropertyLog(LiveDTO liveDTO);
}
