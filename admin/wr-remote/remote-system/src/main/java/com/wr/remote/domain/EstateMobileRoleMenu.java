package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端角色菜单中间表
 */
@Data
public class EstateMobileRoleMenu extends BaseEntity {

    //物业移动端菜单表id
    private Long estateMenuId;
    //物业员工移动端角色表id
    private Long estateRoleId;


}
