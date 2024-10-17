package com.wr.api.estate.mapper.maillist;

import com.wr.api.estate.entity.maillist.MaillistEntity;
import com.wr.api.estate.entity.maillist.UserEntity;
import com.wr.api.estate.entity.maillist.ContactEntity;
import com.wr.api.estate.entity.maillist.OwnerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: maillistMapper
 * @History:
 */
@Repository
public interface MaillistMapper {

    /**
     * 根据部门id查询部门列表
     *
     * @param estateId
     * @return
     */
    List<MaillistEntity> getEstate(Long estateId);

    List<UserEntity> getUserInfo(UserEntity userEntity);

    List<MaillistEntity> getcid(Long estateId);

    List<ContactEntity> contact(ContactEntity contactEntity);

    List<OwnerEntity> getowner(OwnerEntity owner);

    /**
     * 如果当前登录账号是”物业端员工账号“，需重新查询一次
     *
     * @param staffId
     * @return
     */
    List<Long> getownerEntityList(Long staffId);

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
     * @param maillistEntity
     * @return
     */
    int collectDelete(MaillistEntity maillistEntity);

    /**
     * 根据当前登录用户查询收藏夹
     *
     * @param userId
     * @return
     */
    List<UserEntity> getCollectList(Long userId);
}
