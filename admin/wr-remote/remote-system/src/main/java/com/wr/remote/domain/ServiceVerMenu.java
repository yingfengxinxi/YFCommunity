package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 *  服务版本菜单关联表 实体类
 * @author DIANWEI
 * @date 2022/9/21 14:17
 */
@Data
public class ServiceVerMenu extends BaseEntity {
    /**
     * 版本id，表xccj_community.bus_server_ver
     */
    private Long versionId;
    /**
     * 菜单id，表xccj_estate_sys.sys_menu
     */
    private Long menuId;
}
