package com.wr.remote.govern.autonomy.owner;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 业主委员会
 */
@Data
public class OwnerCommittee extends BaseEntity {
    /**
     * 主键
     */
    private Long committeeId;
    /**
     * 社区ID
     */
    private Long communityId;
    /**
     * 社区别名
     */
    private String committeeName;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
    /**
     * 社区集合
     */
    private Long[] communityIds;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 手机号
     */
    private String ownerPhone;
    /**
     * 业主ID
     */
    private Long ownerId;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 单元ID
     */
    private String unitId;
    /**
     * 楼栋ID
     */
    private String buildingId;
    /**
     * 业主集合id
     */
    private Long[] ownerIds;
    /**
     * 是否为空标识
     */
    private String are;


}
