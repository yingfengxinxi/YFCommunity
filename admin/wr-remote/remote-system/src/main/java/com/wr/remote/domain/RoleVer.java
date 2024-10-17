package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 *  物业角色-服务版本关联表
 * @author DIANWEI
 * @date 2022/9/21 14:48
 */
@Data
public class RoleVer extends BaseEntity {
    /**
     * 角色id，表：xccj_estate_sys.sys_role，role_type=10
     */
    private Long roleId;
    /**
     * 版本id，表xccj_community.bus_server_ver
     */
    private Long versionId;
}
