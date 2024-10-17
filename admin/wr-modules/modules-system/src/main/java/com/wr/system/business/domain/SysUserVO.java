package com.wr.system.business.domain;

import com.wr.remote.domain.SysUser;
import lombok.Data;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-11 10:09:38
 * @Desc:
 */
@Data
public class SysUserVO extends SysUser {

    /**
     * 物业id
     */
    private Long estateId;

    /**
     * 物业名称
     */
    private String estateName;
}
