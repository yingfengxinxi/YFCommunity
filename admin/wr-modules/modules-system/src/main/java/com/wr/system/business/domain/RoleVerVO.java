package com.wr.system.business.domain;

import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysRole;
import lombok.Data;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 11:29:25
 * @Desc:
 */
@Data
public class RoleVerVO extends SysRole {

    /**
     * 主键id
     */
    private Long versionId;

    /**
     * 版本名称
     */
    private String versionName;
}
