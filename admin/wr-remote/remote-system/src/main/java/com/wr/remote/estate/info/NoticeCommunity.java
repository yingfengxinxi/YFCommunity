package com.wr.remote.estate.info;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 16:29:31
 * @Desc:
 */
public class NoticeCommunity {

  /*
   * 头条Id
   * */
  private Long noticeId;

  /*
   * 社区Id
   * */
  private Long communityId;

  /*
   * 社区名称
   * */
  private String communityName;

  public Long getNoticeId() {
    return noticeId;
  }

  public void setNoticeId(Long noticeId) {
    this.noticeId = noticeId;
  }

  public Long getCommunityId() {
    return communityId;
  }

  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
  }

  public String getCommunityName() {
    return communityName;
  }

  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }
}
