package com.wr.api.estate.service.maillist;

import com.wr.api.estate.entity.maillist.MaillistEntity;
import com.wr.api.estate.entity.maillist.OwnerEntity;
import com.wr.api.estate.entity.maillist.UserEntity;
import com.wr.api.estate.entity.maillist.ContactEntity;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: maillistService
 * @History:
 */
public interface MaillistService {

    /**
     * 企业通讯录（物业端）
     *
     * @param userEntity
     * @return
     */
    List<UserEntity> getEnterprise(UserEntity userEntity);

    List<ContactEntity> contact(ContactEntity contactEntity);

    /**
     * 业主电话（物业端）
     *
     * @param owner
     * @return
     */
    List<MaillistEntity> getowner(OwnerEntity owner);

    /**
     * 查看收藏的联系方式
     *
     * @param maillistEntity
     * @return
     */
    List<MaillistEntity> getCollect(MaillistEntity maillistEntity);

    /**
     * 添加收藏
     *
     * @param maillistEntity
     * @return
     */
    int collectAdd(MaillistEntity maillistEntity);

    /**
     * 取消收藏
     *
     * @param collectId
     * @return
     */
    int collectDelete(MaillistEntity maillistEntity);
}
