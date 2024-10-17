package com.wr.remote.estate.info;

/**
 * @Author: 乐进
 * @Date: 2022-10-11 19:17:52
 * @Desc:
 */
public class HeadlineCommunity {
  /*
  * 头条Id
  * */
  private Long headlineId;

  /*
  * 社区Id
  * */
  private Long communityId;

  /*
  * 社区名称
  * */
  private String communityName;

  public String getCommunityName() {
    return communityName;
  }

  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }

  public Long getHeadlineId() {
    return headlineId;
  }

  public void setHeadlineId(Long headlineId) {
    this.headlineId = headlineId;
  }

  public Long getCommunityId() {
    return communityId;
  }

  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
  }
}
