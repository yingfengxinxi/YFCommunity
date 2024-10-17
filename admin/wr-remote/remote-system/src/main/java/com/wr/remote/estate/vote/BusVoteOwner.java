package com.wr.remote.estate.vote;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("投票表决业主表")
@TableName(value = "xccj_community.bus_vote_owner")
public class BusVoteOwner extends BaseEntity {
    //id主键
    private String id;
    //投票范围表id
    private String voteScopeId;
    //业主账号id
    private Long accountId;
    //投票状态0=未投票1=支持2=反对3=弃权
    private String ownerStatus;
    //设置原因
    private String setReason;
    //删除标记：0=未删除，1=已删除
    private String delFlag;

    @TableField(exist = false)
    private String ownerName;
    @TableField(exist = false)
    private String cardType;
    @TableField(exist = false)
    private String cardNo;
    @TableField(exist = false)
    private String ownerPhone;

}
