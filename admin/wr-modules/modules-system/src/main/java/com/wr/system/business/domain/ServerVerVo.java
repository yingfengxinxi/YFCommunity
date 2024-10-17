package com.wr.system.business.domain;

import com.wr.remote.domain.ServerVer;
import lombok.Data;

/**
 * 服务版本实体类 Vo
 *
 * @author DIANWEI
 * @date 2022/9/21 17:03
 */
@Data
public class ServerVerVo extends ServerVer {
    /**
     * 菜单组
     */
    private Long[] menuIds;
}
