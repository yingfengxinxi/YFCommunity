package com.wr.remote.estate.customer.decorate;

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
 * @Date 2024/1/12 17:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("装修违规记录")
@TableName(value = "xccj_community.bus_decorate_violation")
public class DecorateViolation extends BaseEntity {

    private String id;
    //装修表id
    private String decorateId;
    //违规记录内容
    private String content;
    //图片
    private String image;
    //装修进度
    private String renovationAuditStatus;
    @TableField(exist = false)
    private List<String> images;
    //0-正常1-删除
    @TableLogic
    private String delFlag;
}
