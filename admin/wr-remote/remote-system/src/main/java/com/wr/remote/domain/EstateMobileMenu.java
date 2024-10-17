package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/7 13:44
 * 物业移动端菜单表
 */
@Data
public class EstateMobileMenu extends BaseEntity {

    //	菜单id
    private Long estateMenuId;
    //	菜单名称
    private String estateMenuName;
    //菜单编号
    private String estateMenuCode;


}
