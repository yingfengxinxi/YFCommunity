package com.wr.api.owner.service.estateapi.impl;

import cn.hutool.core.map.MapUtil;
import com.wr.api.owner.mapper.estateapi.CommunityNoticeMapper;
import com.wr.api.owner.service.estateapi.CommunityNoticeService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.NoticeCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 16:23:46
 * @Desc:
 */
@Service
public class CommunityNoticeServiceImpl implements CommunityNoticeService {

    @Autowired
    private CommunityNoticeMapper noticeMapper;

    @Override
    public List<CommunityNotice> selectList(CommunityNotice notice) {

        if ("0".equals(notice.getRange())) {
            notice.setRange("");
        }
        List<CommunityNotice> list = noticeMapper.selectList(notice);
        return list;
    }

    @Override
    public CommunityNotice selectNoticeById(Map<String,Object> map) {
        CommunityNotice communityNotice = noticeMapper.selectEstateNameById( MapUtil.getLong( map,"estateId" ) );
        CommunityNotice notice = noticeMapper.selectNoticeById(MapUtil.getLong( map,"noticeId" ));
        notice.setEstateName(communityNotice.getEstateName());
        if ("1".equals(notice.getNoticeStatus())) {
            notice.setUpdateTime(null);
        }
        return notice;
    }

    @Override
    public int batchCommunityNotice(List<NoticeCommunity> communityList) {
        return noticeMapper.batchCommunityNotice(communityList);
    }

    @Override
    public Integer insertNotice(CommunityNotice notice) {

        int row = noticeMapper.insertNotice(notice);
        return row;
    }

    @Override
    public Integer updateStatus(Long noticeId) {
        int row = noticeMapper.updateStatus(noticeId);
        return row;
    }

    @Override
    public Integer updateStatus2(Long noticeId) {
        int row = noticeMapper.updateStatus2(noticeId);
        return row;
    }

    @Override
    public Integer checkTitleUnique(Long estateId, Long noticeId, String noticeTitle,Long communityId) {

        return  noticeMapper.checkTitleUnique(estateId, noticeId, noticeTitle,communityId);
    }



    @Override
    public Integer updateNotice(CommunityNotice notice) {
        int row = noticeMapper.updateNotice(notice);
        return row;
    }

    @Override
    public Integer deleteNoticeByIds(Long[] noticeIds) {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
