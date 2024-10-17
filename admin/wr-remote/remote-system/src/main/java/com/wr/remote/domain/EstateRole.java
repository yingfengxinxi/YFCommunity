package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色表
 */
@Data
public class EstateRole extends BaseEntity {

    //id
    private Long estateRoleId;
    //	角色名称
    private String estateRoleName;

    //物业id
    private Long estateId;


}
