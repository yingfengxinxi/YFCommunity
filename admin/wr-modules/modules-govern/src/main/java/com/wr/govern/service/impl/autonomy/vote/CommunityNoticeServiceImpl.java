package com.wr.govern.service.impl.autonomy.vote;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.vote.CommunityNoticeMapper;
import com.wr.govern.service.autonomy.vote.CommunityNoticeService;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.NoticeCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
    notice.setEstateId(estateId);
    List<CommunityNotice> list = noticeMapper.selectList(notice);
    for (CommunityNotice communityNotice : list) {
      communityNotice.setEstateId(estateId);
      if ("1".equals(communityNotice.getNoticeStatus())) {
        communityNotice.setUpdateTime(null);
      }
    }
    return list;
  }

  @Override
  public CommunityNotice selectNoticeById(Long noticeId) {
    CommunityNotice communityNotice = noticeMapper.selectEstateNameById(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    CommunityNotice notice = noticeMapper.selectNoticeById(noticeId);
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
    notice.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    int row = noticeMapper.insertNotice(notice);
    // 新增社区与头条管理
    insertNoticeCommunity(notice);
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
  public List<CommunityNotice> checkTitleUnique(CommunityNotice notice) {
    List<CommunityNotice> list = noticeMapper.checkTitleUnique(notice);
    return list;
  }

  @Override
  public List<CommunityNotice> checkTitleUnique2(CommunityNotice notice) {
    List<CommunityNotice> list = noticeMapper.checkTitleUnique2(notice);
    return list;
  }

  @Override
  public Integer updateNotice(CommunityNotice notice) {
    int row = noticeMapper.updateNotice(notice);
    //删除社区与公示关联
    noticeMapper.deleteCommunityByNoticeId(notice.getNoticeId());
    // 修改社区与头条管理
    insertNoticeCommunity(notice);
    return row;
  }

  @Override
  public Integer deleteNoticeByIds(Long[] noticeIds) {
    return noticeMapper.deleteNoticeByIds(noticeIds);
  }

  private void insertNoticeCommunity(CommunityNotice notice) {
    this.insertNoticeCommunity(notice.getNoticeId(), notice.getCommunityIds());
  }

  /**
   * 新增社区与头条管理
   *
   * @param noticeId 公示Id
   * @param communityIds 社区Id
   */
  public void insertNoticeCommunity(Long noticeId, Long[] communityIds)
  {
    if (StringUtils.isNotEmpty(communityIds))
    {
      // 新增用户与角色管理
      List<NoticeCommunity> list = new ArrayList<NoticeCommunity>();
      for (Long communityId : communityIds)
      {
        NoticeCommunity hc = new NoticeCommunity();
        hc.setNoticeId(noticeId);
        hc.setCommunityId(communityId);
        list.add(hc);
      }
      noticeMapper.batchCommunityNotice(list);
    }
  }
}
