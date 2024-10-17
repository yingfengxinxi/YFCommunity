package com.wr.api.owner.entity.vo.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:31:33
 * @Desc: 管家管理 VO
 */
@Data
public class MajordomoVO implements Serializable {
    private static final long serialVersionUID = 4740683196979102562L;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 员工名称
     */
    private String staffName;
    /**
     * 员工手机号
     */
    private String staffPhone;
    /**
     * 楼栋名称
     */
    private String buildingName;

    @Override
    public String toString() {
        return "MajordomoVO{" +
                "avatar='" + avatar + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }
}
