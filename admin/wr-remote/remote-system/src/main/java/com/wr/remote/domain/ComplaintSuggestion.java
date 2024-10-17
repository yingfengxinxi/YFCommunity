package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/25 16:11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("业主投诉建议")
@TableName(value = "xccj_community.bus_complaint_suggestion")
public class ComplaintSuggestion extends BaseEntity {

    //	主键
    private String id;
    //所属小区id，表xccj_community.bus_community
    private Long communityId;
    //物业id
    private Long estateId;
    //	投诉建议类型,数据字典：complaint_suggestion_type
    private String complaintSuggestionType;
    //问题描述
    private String problemDescription;
    //	问题图片多张图片使用逗号(,)隔开
    private String problemImage;
    @TableField(exist = false)
    private List<String> problemImageLists;
    //答复内容
    private String propertyResponse;
    //回复状态0=未回复1=已回复
    @TableField(exist = false)
    private String status;
    // 删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
