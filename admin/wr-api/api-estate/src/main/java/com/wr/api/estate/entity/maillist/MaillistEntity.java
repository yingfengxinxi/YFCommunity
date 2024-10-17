package com.wr.api.estate.entity.maillist;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: maillistEntity
 * @History:
 */
@Data
public class MaillistEntity implements Serializable {

    private static final long serialVersionUID = -8844962151315923810L;

    private String nickName;
    private String phonenumber;
    private Long estateId;
    private String communityName;
    private Long communityId;
    private Long deptId;
    private Long parentId;
    private String ancestors;
    private String deptName;
    private String estateName;
    private Long deptType;
    List<UserEntity> userList;
    List<OwnerEntity> ownerList;

    private String searchValue;
    private Long userId;
    private Long collectStaffId;
    private Long collectId;
}
