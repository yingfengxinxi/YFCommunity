package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.ApiMemberVo;
import com.wr.api.owner.entity.vo.serve.ApiPartyPayVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 党费缴费记录
 * @Author: lvzy
 * @Date: 2022-11-17 13:11:41
 * @Desc:党费缴费记录
 */
public interface PartyPayService {

    /**
     * 分页查询
     * @param memberId
     * @return
     */
    List<ApiPartyPayVo> listPage(Long memberId);

    /**
     * 最新缴费
     * @param member 党员ID
     * @return
     */
    AjaxResult newList(ApiMemberVo member);

    /**
     * 历史缴费记录
     * @param logId
     * @return
     */
    AjaxResult oldDetail(Long logId);

    /**
     * 最新缴费详情
     * @param memberId
     * @return
     */
    AjaxResult newDetail(Long memberId);
}
