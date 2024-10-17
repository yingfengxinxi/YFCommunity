package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 *  服务版本实体类
 * @author DIANWEI
 * @date 2022/9/21 14:16
 */
@Data
public class ServerVer extends BaseEntity {
    /**
     * 主键
     */
    private Long versionId;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 删除标记：0=未删除；1=已删除
     */
    private String delFlag;

}
