package com.wr.govern.service.autonomy.vote;

import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.NoticeCommunity;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 16:22:11
 * @Desc:
 */
public interface CommunityNoticeService {
  /*
   * 查询列表
   * */
  List<CommunityNotice> selectList(CommunityNotice notice);


  /**
   * 根据主键获取对象
   * @param noticeId 主键
   * @return
   */
  CommunityNotice selectNoticeById(Long noticeId);

  /**
   * 批量新增用户角色信息
   *
   * @param communityList 用户角色列表
   * @return 结果
   */
  public int batchCommunityNotice(List<NoticeCommunity> communityList);

  /**
   * 新增社区公示
   * @param notice
   * @return
   */
  Integer insertNotice(CommunityNotice notice);

  /**
   * 修改公示状态
   * @param noticeId 主键
   * @return
   */
  Integer updateStatus(Long noticeId);

  /**
   * 修改公示状态
   * @param noticeId 主键
   * @return
   */
  Integer updateStatus2(Long noticeId);

  /**
   * 校验标题唯一性
   *  banner
   * @param
   * @return
   */
  List<CommunityNotice> checkTitleUnique(CommunityNotice notice);

  /*
   * 修改时检查标题唯一性
   * */
  List<CommunityNotice> checkTitleUnique2(CommunityNotice notice);


  /**
   * 修改公示
   * @param notice 公示信息
   * @return
   */
  Integer updateNotice(CommunityNotice notice);

  /**
   * 删除公示
   * @param noticeIds 主键
   * @return
   */
  Integer deleteNoticeByIds(Long[] noticeIds);
}
