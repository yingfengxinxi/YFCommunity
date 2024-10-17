package com.wr.system.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wr.remote.domain.SysDept;
import com.wr.remote.domain.SysMenu;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author RainCity
 */
@Data
public class TreeSelectVo
{
    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;


    private String type;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVo> children;

    public TreeSelectVo()
    {

    }

    public TreeSelectVo(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }

    public TreeSelectVo(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }

    public TreeSelectVo(AreaVo area) {
        this.id = area.getAreaId();
        this.label = area.getAreaName();
        this.type = area.getNum();
        this.children = area.getChildren().stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }

    public TreeSelectVo(EstateVo estateVo) {
        this.id = estateVo.getEstateId();
        this.label = estateVo.getEstateName();
        this.children = estateVo.getChildren().stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }

    public TreeSelectVo(CommunityVo community) {
        this.id = community.getCommunityId();
        this.label = community.getCommunityName();
        this.type = community.getCommunityType();
        this.children = community.getChildren().stream().map(TreeSelectVo::new).collect(Collectors.toList());
    }
}
