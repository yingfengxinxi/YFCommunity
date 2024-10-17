package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.entity.vo.serve.ApiMemberVo;
import com.wr.api.owner.entity.vo.serve.ApiPartyPayVo;
import com.wr.api.owner.service.serve.PartyPayService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.govern.party.Member;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 党费缴费记录
 * @Author: lvzy
 * @Date: 2022-11-17 13:05:06
 * @Desc:党费缴费记录
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/party")
public class PartyPayController extends BaseController {

    private final PartyPayService partyPayService;

    /**
     * 历史缴费分页
     * @param member
     * @return
     */
    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiMemberVo member) {
        new ExtractCode().pageListAntiSql(member.getPageNum(), member.getPageSize());
        return getDataTable(partyPayService.listPage(member.getMemberId()));
    }

    /**
     * 历史缴费详情
     * @param partyPayVo
     * @return
     */
    @GetMapping("/oldDetail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult oldDetail(ApiPartyPayVo partyPayVo) {
        return partyPayService.oldDetail(partyPayVo.getLogId());
    }

    /**
     * 最新缴费列表
     * @param member
     * @return
     */
    @GetMapping("/newList")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult newList(ApiMemberVo member) {
        return partyPayService.newList(member);
    }

    /**
     * 最新缴费详情
     * @param member
     * @return
     */
    @GetMapping("/newDetail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult newDetail(Member member) {
        return partyPayService.newDetail(member.getMemberId());
    }

}
