package com.wr.remote.estate.vote;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Date 2024/2/23 15:21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("投票表决")
@TableName(value = "xccj_community.bus_vote")
public class BusVote extends BaseEntity {
    //	主键
    private String id;
    //	物业id，表xccj_estate_sys.bus_estate
    private Long estateId;
    @TableField(exist = false)
    private Long accountId;
    @TableField(exist = false)
    private String ownerId;
    //小区id
    private Long communityId;
    //	楼栋id
    private Long buildingId;
    //单元楼id
    private Long unitId;
    //	房间id
    private Long roomId;
    //投票单号
    private String voteNo;
    //投票名称
    private String voteName;
    //内容
    private String content;
    //投票开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //投票结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //是否延期0=否1=是
    private String isYq;
    //旧投票结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date oldEndTime;
    //议题名称
    private String issuesName;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

    @TableField(exist = false)
    private String state;
    @TableField(exist = false)
    private String countnum;
    /**
     * 展示状态
     **/
    @TableField(exist = false)
    private String showStatus;
}
