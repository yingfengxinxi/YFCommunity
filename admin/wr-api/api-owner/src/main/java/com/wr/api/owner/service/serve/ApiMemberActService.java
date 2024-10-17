package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.MemberActVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 党员活动
 * @Author: lvzy
 * @Date: 2022-11-15 15:15:03
 * @Desc:党员活动
 */
public interface ApiMemberActService {

    /**
     * 分页
     * @param communityId
     * @return
     */
    List<MemberActVo> listPage(Long communityId);

    /**
     * 详情
     * @param memberAct
     * @return
     */
    AjaxResult detail(MemberActVo memberAct);

    /**
     * 报名
     * @param memberId   党员ID
     * @param activityId 活动ID
     * @return
     */
    AjaxResult signUp(MemberActVo memberAct);
}
