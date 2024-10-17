package com.wr.govern.service.impl.autonomy.vote;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.vote.VoteCommunityMapper;
import com.wr.govern.mapper.autonomy.vote.VoteItemMapper;
import com.wr.govern.mapper.autonomy.vote.VoteMapper;
import com.wr.govern.service.autonomy.vote.CommunityNoticeService;
import com.wr.govern.service.autonomy.vote.VoteService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.govern.autonomy.vote.Vote;
import com.wr.remote.govern.autonomy.vote.VoteCommunity;
import com.wr.remote.govern.autonomy.vote.VoteItem;
import com.wr.remote.govern.autonomy.vote.VoteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 投票管理 服务实现
 *
 * @author liubei
 * @createDate 2022-10-24
 */
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private VoteCommunityMapper voteCommunityMapper;
    @Autowired
    private VoteItemMapper voteItemMapper;

    @Autowired
    private CommunityNoticeService communityNoticeService;

    @Override
    public List<Vote> selectBusEstateList(Vote vote) {
        vote.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        List<Vote> list = voteMapper.selectBusEstateList(vote);
        for (Vote ques : list) {
            // ques.setEstateName(investigationMapper.selectCommunityById(ques.getCommunityId()).getCommunityName());
            List<VoteItem> item = voteItemMapper.selectBusVoteItem(ques.getVoteId());
            for (VoteItem busVoteItem : item) {
                if (busVoteItem.getItemName().equals("")) {
                    //类型3代表是文本答题
                    busVoteItem.setType("3");
                }
            }
            ques.setItemList(item);
        }
        return list;
    }

    @Override
    public Vote selectBusEstateById(Long voteId) {

        return voteMapper.selectBusEstateById(voteId);
    }

    @Override
    public List<VoteRecord> getVoteRecord(VoteRecord voteRecord) {
        List<VoteRecord> voteRecordList = voteItemMapper.getVoteRecord(voteRecord);
        return voteRecordList;
    }

    @Override
    public int insertBusEstate(Vote busVote) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date first = sdf.parse(busVote.getStartEndTime()[0]);
        Date lastTime = sdf.parse(busVote.getStartEndTime()[1]);
        busVote.setStartTime(first);
        busVote.setEndTime(lastTime);
        busVote.setCreateBy(SecurityUtils.getUsername());
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        busVote.setEstateId(sysUser.getEstateId());
        Integer num = voteMapper.insertBusEstate(busVote);
        //添加问卷调查发布范围基本信息
        for (int i = 0; i < busVote.getCommunityIds().size(); i++) {
            VoteCommunity busVoteCommunity = new VoteCommunity();
            busVoteCommunity.setVoteId(busVote.getVoteId());
            busVoteCommunity.setCommunityId(busVote.getCommunityIds().get(i));
            num = voteCommunityMapper.insertBusVoteCommunity(busVoteCommunity);

        }

        if (!CollectionUtils.isEmpty(busVote.getUnitIds())){
            // 新增社区投票单元范围关联
            voteCommunityMapper.insertVoteUnitBatch(busVote);
        }

        if (!CollectionUtils.isEmpty(busVote.getBuildingIds())){
            // 新增社区投票楼栋范围关联
            voteCommunityMapper.insertVoteBuildingBatch(busVote);
        }

        VoteItem item1 = new VoteItem();
        item1.setVoteId(busVote.getVoteId());
        item1.setItemName("同意");
        voteItemMapper.insertBusVoteItem(item1);

        VoteItem item2 = new VoteItem();
        item2.setVoteId(busVote.getVoteId());
        item2.setItemName("不同意");
        voteItemMapper.insertBusVoteItem(item2);
        //添加问卷调查单选题题目
/*
        for (int i = 0; i < busVote.getSingle().size(); i++) {
            if (StringUtils.isNotEmpty(busVote.getSingle().get(i).getTitle())) {
                //添加题目对应选项
                for (int j = 0; j < busVote.getSingle().get(i).getItemValue().size(); j++) {
                    VoteItem item = new VoteItem();
                    item.setVoteId(busVote.getVoteId());
                    item.setItemName(busVote.getSingle().get(i).getItemValue().get(j).getValue());
                    num = voteItemMapper.insertBusVoteItem(item);
                }
            }
        }
*/

/*        for (int i = 0; i < busVote.getMultiple().size(); i++) {
            if (StringUtils.isNotEmpty(busVote.getMultiple().get(i).getTitle())) {
                //添加题目对应选项
                for (int j = 0; j < busVote.getMultiple().get(i).getItemValue().size(); j++) {
                    VoteItem item = new VoteItem();
                    item.setVoteId(busVote.getVoteId());
                    item.setItemName(busVote.getMultiple().get(i).getItemValue().get(j).getValue());
                    num = voteItemMapper.insertBusVoteItem(item);
                }
            }
        }
        for (int i = 0; i < busVote.getText().size(); i++) {
            //添加题目对应选项
            if (StringUtils.isNotEmpty(busVote.getText().get(i).getTitle())) {
                for (int j = 0; j < busVote.getText().get(i).getItemValue().size(); j++) {
                    VoteItem item = new VoteItem();
                    item.setVoteId(busVote.getVoteId());
                    item.setItemName(busVote.getText().get(i).getItemValue().get(j).getValue());
                    num = voteItemMapper.insertBusVoteItem(item);
                }
            }
        }*/
        return num;

    }


    @Override
    public int deleteBusEstateById(Long[] voteId) {
        return voteMapper.deleteBusEstateById(voteId);

    }

    /**
     * 发布公告
     *
     * @param voteId 投票id
     */
    @Override
    public void bulletin(Long voteId) {
        String noticeTemplate = "同意人数：%s人\n不同意人数：%s\n同意率：%s%%";
        // 查询投票详情
        Vote vote = voteMapper.selectBusEstateById(voteId);
        CommunityNotice notice = new CommunityNotice();
        notice.setCreateBy(SecurityUtils.getUsername());
        notice.setNoticeTitle(vote.getVoteName());
        // 查询投票情况
        int consentCount = voteMapper.consentCount(voteId);
        int unConsentCount = voteMapper.unConsentCount(voteId);
        List<Long> communityIds = voteMapper.selectCommunityIds(voteId);
        String rate;
        if (consentCount == 0 || unConsentCount + consentCount == 0) {
            rate = "0.00%";
        } else {
            rate = String.format("%.2f", (consentCount / (float) (unConsentCount + consentCount)) * 100);
        }
        notice.setContent(String.format(noticeTemplate, consentCount, unConsentCount, rate));
        Long[] communityIdArray = new Long[communityIds.size()];
        notice.setCommunityIds(communityIds.toArray(communityIdArray));
        communityNoticeService.insertNotice(notice);
        voteMapper.updateHasNotice(voteId);
    }

    public static void main(String[] args) {
        String rate = String.format("%.2f", (float) (0 / (0 + 10)) * 100);
        System.out.println(rate);
    }
}
