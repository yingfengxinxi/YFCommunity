package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.Vote;
import com.wr.api.owner.entity.vo.serve.VoidVO;
import com.wr.api.owner.entity.vo.serve.VoteItemVO;
import com.wr.api.owner.entity.vo.serve.VoteRecordVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import com.wr.api.owner.service.serve.VoteService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 21:01:19
 * @Desc: 服务-委员会投票 控制层
 */
@RestController
@RequestMapping("/v2/vote")
public class VoteController extends BaseController {
    @Autowired
    private VoteService voteService;

    /**
     * 获取投票表决
     *
     * @param communityId
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public AjaxResult list(Long communityId, Long buildingId, Long unitId, Long ownerId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return AjaxResult.error("参数不能为空");
        }
        List<VoidVO> voidAll = voteService.getVoidAll(communityId, buildingId, unitId, ownerId);
        return AjaxResult.success(voidAll);
    }

    /**
     * 获取投票表决选项
     *
     * @param voteId
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/voteItemList")
    public AjaxResult voteItemList(Long voteId) {
        if (ObjectUtils.isEmpty(voteId)) {
            return AjaxResult.error("参数不能为空");
        }
        List<VoteItemVO> voteItemAll = voteService.getVoteItemAll(voteId);
        return AjaxResult.success(voteItemAll);
    }

    /**
     * 统计
     *
     * @param voteId 投票id
     * @return AjaxResult
     */
    @AuthRequired
    @GetMapping("/statistics")
    public AjaxResult voteStatistics(Long voteId) {
        if (ObjectUtils.isEmpty(voteId)) {
            return AjaxResult.error("参数不能为空");
        }
        VoteStatisticsVO voteStatisticsVO = voteService.voteStatistics(voteId);
        return AjaxResult.success(voteStatisticsVO);
    }

    /**
     * 投票表决
     *
     * @param itemId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/update")
    @ApiLog(title = "业主委员会-投票", businessType = BusinessType.UPDATE)
    public AjaxResult update(@RequestBody VoteRecordVO vote) {
        Integer num = voteService.upVoteItem(vote);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 添加投票
     *
     * @param vote
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/add")
    @ApiLog(title = "业主委员会-添加", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody Vote vote) {
        AjaxResult ajaxResult = voteService.addVote(vote);
        return ajaxResult;
    }
}
