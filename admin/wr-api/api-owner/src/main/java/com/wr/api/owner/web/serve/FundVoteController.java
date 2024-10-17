package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.FundVote;
import com.wr.api.owner.entity.vo.serve.FundVoteResultVO;
import com.wr.api.owner.entity.vo.serve.FundVoteVO;
import com.wr.api.owner.entity.vo.serve.VoteStatisticsVO;
import com.wr.api.owner.service.serve.FundVoteService;
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
@RequestMapping("/v2/fundVote")
public class FundVoteController extends BaseController {
    @Autowired
    private FundVoteService fundVoteService;

    /**
     * 获取投票表决
     *
     * @param fundVote 查询条件
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public AjaxResult list(FundVote fundVote) {
        List<FundVoteResultVO> list = fundVoteService.list(fundVote);
        return AjaxResult.success(list);
    }

    /**
     * 投票表决
     *
     * @param vote 投票数据
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/vote")
    @ApiLog(title = "基金投票-投票", businessType = BusinessType.UPDATE)
    public AjaxResult update(@RequestBody FundVoteVO vote) {
        fundVoteService.vote(vote);
        return AjaxResult.success();
    }

    /**
     * 添加投票
     *
     * @param vote
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/add")
    @ApiLog(title = "基金投票-添加", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody FundVoteVO vote) {
        fundVoteService.insertFundVote(vote);
        return AjaxResult.success();
    }

    @AuthRequired
    @GetMapping("/statistics")
    public AjaxResult voteStatistics(Long voteId) {
        if (ObjectUtils.isEmpty(voteId)) {
            return AjaxResult.error("参数不能为空");
        }
        VoteStatisticsVO voteStatisticsVO = fundVoteService.voteResultSum(voteId);
        return AjaxResult.success(voteStatisticsVO);
    }

}
