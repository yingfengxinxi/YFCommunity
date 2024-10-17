package com.wr.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:24
 */
@Data
@TableName("xccj_community.bus_home_menu")
public class HomeMenu extends BaseEntity {

    //	主键
    private Long homeMenuId;
    //	物业id，表xccj_estate_sys.bus_estate
    private Long estateId;
    //类型0=物业移动端1=物业移动端
    private String type;
    //菜单名称
    private String name;
    //菜单类型0=顶部菜单1=物业服务/生活服务
    private String menuType;
    //图片地址
    private String menuUrl;
    //跳转地址
    private String pageUrl;
    //物业移动端菜单code
    private String menuCode;
    //排序
    private int sort;
    //删除标记：0=未删除，1=已删除
    private String delFlag;

}
