package com.wr.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:27:46
 * @Desc:
 */
@Data
@TableName("xccj_community.bus_banner")
@ApiModel(value = "Banner对象", description = "轮播图")
public class Banner extends BaseEntity {

  /*
  * 轮播图主键
  * */
  @ApiModelProperty(value = "轮播图主键")
  private Long bannerId;

  /**
   * 物业id，表bus_estate
   */
  @ApiModelProperty(value = "物业id")
  private Long estateId;

  /**
   * 序号
   */
  @NotNull(message = "序号不能为空")
  @ApiModelProperty(value = "序号")
  private Integer orderNum;

  /**
   * 图片路径
   */
  @NotNull(message = "图片不能为空")
  @ApiModelProperty(value = "图片路径")
  private String bannerUrl;
  @ApiModelProperty(value = "跳转链接")
  private String pageUrl;
  @ApiModelProperty(value = "跳转链接类型（0.内部 2.H5 3.图片）")
  private String pageUrlType;

  @ApiModelProperty(value = "类型 0=业主移动端1=物业移动端")
  private String type;

  /**
   * 图片路径
   */
  @ApiModelProperty(value = "删除标记（0.未删除 1.已删除）")
  private String delFlag;
}
