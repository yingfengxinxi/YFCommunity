package com.wr.common.customize.util;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 海康配置表实体类
 *
 * @author DIANWEI
 * @date 2022/9/22 10:21
 */
@Data
public class CommunityHik extends BaseEntity {
    private static final long serialVersionUID = -8523987789625891130L;
    /**
     * 社区id，表xccj_community.bus_community
     */
    private Long communityId;
    private String communityName;
    /**
     * 海康服务器ip
     */
    private String hikIp;
    /**
     * 海康服务port
     */
    private String hikPort;
    /**
     * 海康服务ak
     */
    private String hikAk;
    /**
     * 海康服务sk
     */
    private String hikSk;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
}
