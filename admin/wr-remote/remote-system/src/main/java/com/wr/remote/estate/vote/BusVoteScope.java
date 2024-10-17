package com.wr.remote.estate.vote;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.vo.BusVoteOwnerVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("投票表决范围表")
@TableName(value = "xccj_community.bus_vote_scope")
public class BusVoteScope extends BaseEntity {

    //	主键
    private String id;
    ////	物业id，表xccj_estate_sys.bus_estate
    private Long estateId;
    //小区id
    private Long communityId;
    //	楼栋id
    private Long buildingId;
    //单元楼id
    private Long unitId;
    //	房间id
    private Long roomId;
    @TableField(exist = false)
    private Long ownerId;
    //投票表id
    private String voteId;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;


    @TableField(exist = false)
    private String ownerPhone;
    @TableField(exist = false)
    private String ownerRelation;
    @TableField(exist = false)
    private String ownerName;
    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private String unitName;
    @TableField(exist = false)
    private String buildingName;
    @TableField(exist = false)
    private String houseAddress;
    @TableField(exist = false)
    private List<BusVoteOwnerVo> voteOwnerList;

    @TableField(exist = false)
    private String il;
}
