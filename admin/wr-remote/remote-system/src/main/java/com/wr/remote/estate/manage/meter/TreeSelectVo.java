package com.wr.remote.estate.manage.meter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wr.remote.domain.TreeSelect;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-26 13:24:28
 * @Desc:
 */
public class TreeSelectVo implements Serializable {

    private static final long serialVersionUID = 6944277393253171656L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVo> children;

    private String flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelectVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelectVo> children) {
        this.children = children;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
