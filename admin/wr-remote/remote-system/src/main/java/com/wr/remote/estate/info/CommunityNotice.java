package com.wr.remote.estate.info;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 15:54:17
 * @Desc:
 */
@Data
@ApiModel("通知公告")
public class CommunityNotice extends BaseEntity {

    /*
     * 社区公示表主键
     * */
    private Long noticeId;

    /**
     * 社区组
     */
    private Long[] communityIds;

    /*
     * 社区表主键
     * */
    private Long communityId;

    /*
     * 物业名称
     * */
    private String estateName;

    /*
     * 社区名称
     * */
    private String communityName;

    /**
     * 物业id，表bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String noticeTitle;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    private String content;
    //简介
    private String briefIntroduction;

    /**
     * 阅读数
     */
    private Long readNum;

    /**
     * 状态：0=已发布，1=已撤回
     */
    private String noticeStatus;

    @TableField(exist = false)
    private String type;

    //发布范围0=全部1=物业2=业主
    private String range;

}
