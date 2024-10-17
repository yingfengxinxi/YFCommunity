package com.wr.api.estate.entity.maillist;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: ownerEntity
 * @History:
 */
@Data
public class OwnerEntity extends BaseEntity {

    private Long estateId;
    private Long ownerId;
    private Long communityId;
    private Long accountId;
    private String ownerName;
    private String ownerPhone;
    private String gender;
    private Long staffId;
    private Long[] ownerArray;
}
