package com.wr.estate.web.info.info;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.info.HeadlineMapper;
import com.wr.estate.service.info.HeadlineService;
import com.wr.remote.estate.info.Headline;
import com.wr.remote.estate.info.HeadlineCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-11 11:55:42
 * @Desc:
 */
@Service
public class HeadlineServiceImpl implements HeadlineService {

  @Autowired
  private HeadlineMapper headlineMapper;

  @Override
  public List<Headline> selectList(Headline headline) {
    headline.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    List<Headline> list = headlineMapper.selectList(headline);
    for (Headline headline2 : list) {
      headline2.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    }
    return list;
  }

  @Override
  public List<Headline> checkTitleUnique(Headline headline) {
    List<Headline> list = headlineMapper.checkTitleUnique(headline);
    return list;
  }

  @Override
  public List<Headline> checkTitleUnique2(Headline headline) {
    List<Headline> list = headlineMapper.checkTitleUnique2(headline);
    return list;
  }

  /*@Override
  public Integer updateReadNum(Long headLineId) {
    Headline headline = headlineMapper.selectHeadlineById(headLineId);
    headline.setReadNum(headline.getReadNum() + 1);
    return headlineMapper.updateReadNum(headline);
  }*/

  @Override
  public Headline selectHeadlineById(Long headlineId) {
    Headline headline = headlineMapper.selectHeadlineById(headlineId);
//    HeadlineCommunity headlineCommunity = headlineMapper.selectCommunity(headlineId);
//    headline.setCommunityName(headlineCommunity.getCommunityName());
    return headline;
  }

  @Override
  public Integer insertHeadline(Headline headline) {
    headline.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    int row =  headlineMapper.insertHeadline(headline);
    // 新增社区与头条管理
    insertHeadlineCommunity(headline);
    return row;
  }

  private void insertHeadlineCommunity(Headline headline) {
    this.insertHeadlineCommunity(headline.getHeadlineId(), headline.getCommunityIds());
  }

  /**
   * 新增社区与头条管理
   *
   * @param headlineId 头条Id
   * @param communityIds 社区Id
   */
  public void insertHeadlineCommunity(Long headlineId, Long[] communityIds)
  {
    if (StringUtils.isNotEmpty(communityIds))
    {
      // 新增用户与角色管理
      List<HeadlineCommunity> list = new ArrayList<HeadlineCommunity>();
      for (Long communityId : communityIds)
      {
        HeadlineCommunity hc = new HeadlineCommunity();
        hc.setHeadlineId(headlineId);
        hc.setCommunityId(communityId);
        list.add(hc);
      }
      headlineMapper.batchHeadlineCommunity(list);
    }
  }

  @Override
  public Integer updateHeadline(Headline headline) {
    //删除社区与头条关联
    headlineMapper.deleteCommunityByHeadlineId(headline.getHeadlineId());
    // 新增社区与头条管理
    insertHeadlineCommunity(headline);
    return headlineMapper.updateHeadline(headline);
  }

  @Override
  public Integer deleteHeadlineByIds(Long[] headlineIds) {
    return headlineMapper.deleteHeadlineByIds(headlineIds);
  }
}
