package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author lvzy
 * @Date 2024/1/25 13:44
 * 社区资讯
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("社区资讯")
@TableName(value = "xccj_community.bus_community_information")
public class CommunityInformation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    //活动封面
    private String activePicture;

    /**
     * 咨询标题
     */
    private String msgTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 阅读数
     */
    private Long lookNumber;

    //是否置顶0=否1=是
    private String top;
    /**
     * 排序序号
     */
    private Long sortNumber;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    @TableLogic
    private String delFlag;

}
