package com.wr.system.business.domain.screen;

/**
 * @Author: XingDaoRong
 * @Date: 2022/1/17
 * 查询部门信息vo
 */
public class DeptVo {
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
