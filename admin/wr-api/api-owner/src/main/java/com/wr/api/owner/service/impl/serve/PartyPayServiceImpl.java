package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.ApiMemberVo;
import com.wr.api.owner.entity.vo.serve.ApiPartyPayVo;
import com.wr.api.owner.mapper.serve.PartyPayMapper;
import com.wr.api.owner.service.serve.PartyPayService;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 党费缴费记录
 * @Author: lvzy
 * @Date: 2022-11-17 13:11:59
 * @Desc:党费缴费记录
 */
@Service
@AllArgsConstructor
public class PartyPayServiceImpl implements PartyPayService {

    private final PartyPayMapper partyPayMapper;

    @Override
    public List<ApiPartyPayVo> listPage(Long memberId) {
        return partyPayMapper.listPage(memberId);
    }

    @Override
    public AjaxResult oldDetail(Long logId) {
        return AjaxResult.success(partyPayMapper.oldDetail(logId));
    }

    @Override
    public AjaxResult newList(ApiMemberVo member) {
        Integer integer = partyPayMapper.selectDateByCount(member);
        if (integer == 0) {
            return AjaxResult.success(partyPayMapper.newList(member));
        }
        return AjaxResult.success("当前月份已缴费");
    }

    @Override
    public AjaxResult newDetail(Long memberId) {
        return AjaxResult.success(partyPayMapper.newDetail(memberId));
    }
}
