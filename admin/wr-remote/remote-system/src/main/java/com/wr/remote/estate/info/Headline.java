package com.wr.remote.estate.info;

import com.wr.common.core.web.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * @Author: 乐进
 * @Date: 2022-10-11 10:43:15
 * @Desc:
 */
public class Headline extends BaseEntity {

  /*
  * 主键
  * */
  private Long headlineId;

  /** 社区组 */
  private Long[] communityIds;

  /*
  * 社区Id
  * */
  private String communityId;

  /*
  * 社区名称
  * */
  private String communityName;

  /**
   * 物业id，表bus_estate
   */
  private Long estateId;

  /**
   * 头条标题
   */
  @NotNull(message = "头条标题不能为空")
  private String headlineTitle;

  /**
   * 开始时间
   */
  @NotNull(message = "开始时间不能为空")
  private String startTime;

  /**
   * 结束时间
   */
  @NotNull(message = "结束时间不能为空")
  private String endTime;

  /**
   * 图片
   */
  @NotNull(message = "图片不能为空")
  private String headlineUrl;

  /**
   * 内容
   */
  @NotNull(message = "内容不能为空")
  private String communityContent;

  /**
   * 阅读数
   */
  private Long readNum;

  public String getCommunityId() {
    return communityId;
  }

  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }

  public Long[] getCommunityIds() {
    return communityIds;
  }

  public void setCommunityIds(Long[] communityIds) {
    this.communityIds = communityIds;
  }

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

  public Long getEstateId() {
    return estateId;
  }

  public void setEstateId(Long estateId) {
    this.estateId = estateId;
  }

  public String getHeadlineTitle() {
    return headlineTitle;
  }

  public void setHeadlineTitle(String headlineTitle) {
    this.headlineTitle = headlineTitle;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getHeadlineUrl() {
    return headlineUrl;
  }

  public void setHeadlineUrl(String headlineUrl) {
    this.headlineUrl = headlineUrl;
  }

  public String getCommunityContent() {
    return communityContent;
  }

  public void setCommunityContent(String communityContent) {
    this.communityContent = communityContent;
  }

  public Long getReadNum() {
    return readNum;
  }

  public void setReadNum(Long readNum) {
    this.readNum = readNum;
  }
}
