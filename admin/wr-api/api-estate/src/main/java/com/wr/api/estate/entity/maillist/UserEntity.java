package com.wr.api.estate.entity.maillist;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: userEntity
 * @History:
 */
@Data
public class UserEntity extends BaseEntity {

    private Long estateId;

    private Long communityId;

    private Long deptId;

    private Long userId;

    private String nickName;

    private Long userType;

    private String email;

    private String phonenumber;

    //是否收藏0=否1=是
    private String flag;

    private Long collectId;
}
