package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业员工移动端角色中间表
 */
@Data
public class EstateStaffRole extends BaseEntity {

    //物业岗位角色表id
    private Long estateRoleId;
    //物业人员用户表id
    private Long staffId;


}
