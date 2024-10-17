package com.wr.remote.estate.info;

import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 乐进
 * @Date: 2022-10-13 09:40:33
 * @Desc:
 */
@Data
@ApiModel("信息公示")
public class Invest extends BaseEntity {

    /*
     * 招商公示主键
     * */
    private Long investId;

    /*
     * 物业名称
     * */
    private String estateName;

    /**
     * 物业id，表bus_estate
     */
    private Long estateId;
    //小区id
    private Long communityId;

    @NotNull(message = "简介不能为空")
    private String briefIntroduction;
    /**
     * 标题
     */
    @NotNull(message = "标题不能为空")
    private String investTitle;

    @NotNull(message = "类型不能为空")
    //公示类型0=物业招商公示1=物业通报公示
    private String investType;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    private String content;

    /**
     * 阅读数
     */
    private Long readNum;

    /**
     * 状态：0=已发布，1=已撤回
     */
    private String investStatus;

}
