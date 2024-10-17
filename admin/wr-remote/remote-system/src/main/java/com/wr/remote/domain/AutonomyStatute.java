package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/25 13:49
 * 自治规约
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("自治规约")
@TableName(value = "xccj_community.bus_autonomy_statute")
public class AutonomyStatute extends BaseEntity {


    private String id;
    //物业id
    private Long estateId;
    //小区id
    private Long communityId;
    //活动名称
    private String name;

    //活动图片【封面】
    private String picture;
    //活动列表中显示
    private String listContent;
    //活动内容
    private String content;


    //顺序
    private int sort;

    @TableLogic
    private String delFlag;
}
