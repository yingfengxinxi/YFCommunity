package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.MemberActVo;
import com.wr.api.owner.mapper.serve.ApiOwnerMemberActMapper;
import com.wr.api.owner.service.serve.ApiMemberActService;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党员活动
 * @Author: lvzy
 * @Date: 2022-11-15 15:15:16
 * @Desc:党员活动
 */
@Service
@AllArgsConstructor
public class ApiOwnerMemberActServiceImpl implements ApiMemberActService {

    private final ApiOwnerMemberActMapper apiOwnerMemberActMapper;


    @Override
    public List<MemberActVo> listPage(Long communityId) {
        return apiOwnerMemberActMapper.listPage(communityId);
    }

    @Override
    public AjaxResult detail(MemberActVo memberAct) {
        return AjaxResult.success(apiOwnerMemberActMapper.detail(memberAct));
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult signUp(MemberActVo memberAct) {
        return AjaxResult.success(apiOwnerMemberActMapper.signUp(memberAct));
    }
}
