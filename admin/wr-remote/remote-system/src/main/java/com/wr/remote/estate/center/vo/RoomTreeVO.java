package com.wr.remote.estate.center.vo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 16:30:14
 * @Desc:
 */
public class RoomTreeVO {

    /**
     * 小区id
     */
    private Long id;

    /**
     * 小区名称
     */
    private String name;

    /**
     * 层级，1级小区，2级楼栋，3级单元
     */
    private Integer level;

    /**
     * 唯一key
     */
    private String key;

    /**
     * child
     */
    private List<RoomTreeVO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<RoomTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<RoomTreeVO> children) {
        this.children = children;
    }

    public String getKey() {
        return level + "-" + id;
    }
    @Override
    public String toString() {
        return "RoomTreeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", key='" + key + '\'' +
                ", children=" + children +
                '}';
    }
}
