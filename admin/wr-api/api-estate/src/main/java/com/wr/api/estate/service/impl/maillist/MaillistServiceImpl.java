package com.wr.api.estate.service.impl.maillist;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wr.api.estate.entity.maillist.MaillistEntity;
import com.wr.api.estate.entity.maillist.UserEntity;
import com.wr.api.estate.entity.maillist.ContactEntity;
import com.wr.api.estate.entity.maillist.OwnerEntity;
import com.wr.api.estate.mapper.maillist.MaillistMapper;
import com.wr.api.estate.service.maillist.MaillistService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: bajie
 * @create: 2022/11/15
 * @Description:
 * @FileName: maillistServiceImpl
 * @History:
 */
@Service
public class MaillistServiceImpl implements MaillistService {

    @Autowired
    private MaillistMapper maillistMapper;


    /**
     * 企业通讯录（物业端）
     *
     * @param userEntity
     * @return
     */
    @Override
    public List<UserEntity> getEnterprise(UserEntity userEntity) {
        //根据当前登录用户查询收藏夹
        MaillistEntity maillist = new MaillistEntity();
        maillist.setCollectStaffId(userEntity.getUserId());
        List<MaillistEntity> entityList = this.getCollect(maillist);
        List<UserEntity> getuser = maillistMapper.getUserInfo(userEntity);

        if (CollectionUtils.isNotEmpty(getuser)) {
            getuser.stream().forEach(ue -> {
                ue.setFlag("0");
                ue.setCollectId(null);
                String phonenumber = ue.getPhonenumber();
                if (CollectionUtils.isNotEmpty(entityList)) {
                    entityList.stream().forEach(maillistEntity -> {
                        if (maillistEntity.getPhonenumber().equals(phonenumber)) {
                            ue.setFlag("1");
                            ue.setCollectId(maillistEntity.getCollectId());
                        }
                    });
                }

            });
        }

        return getuser;
    }

    @Override
    public List<ContactEntity> contact(ContactEntity contact) {

        //查询当业务下小区id
        List<MaillistEntity> cidList = maillistMapper.getcid(contact.getEstateId());
        List<ContactEntity> contactEntitieList = new ArrayList<>();
        for (int i = 0; i < cidList.size(); i++) {
            if (cidList.get(i).getCommunityId() != null) {
                ContactEntity Tempcontact = new ContactEntity();
                Tempcontact.setCommunityId(cidList.get(i).getCommunityId());
                if (contact.getCompanyName() != null) {
                    Tempcontact.setCompanyName(contact.getCompanyName());
                }
                if (contact.getSearchValue() != null) {
                    Tempcontact.setSearchValue(contact.getSearchValue());
                }
                List<ContactEntity> con = maillistMapper.contact(Tempcontact);
                contactEntitieList.addAll(con);
            }
        }
        return contactEntitieList;
    }

    /**
     * 业主电话（物业端）
     *
     * @param owner
     * @return
     */
    @Override
    public List<MaillistEntity> getowner(OwnerEntity owner) {
        List<MaillistEntity> cidList = maillistMapper.getcid(owner.getEstateId());
        for (int i = 0; i < cidList.size(); i++) {
            if (cidList.get(i).getCommunityId() != null) {
                OwnerEntity ownerEntity = new OwnerEntity();
                ownerEntity.setCommunityId(cidList.get(i).getCommunityId());
                if (owner.getSearchValue() != null) {
                    ownerEntity.setSearchValue(owner.getSearchValue());
                }
                List<OwnerEntity> con = maillistMapper.getowner(ownerEntity);
                cidList.get(i).setOwnerList(con);
            }
        }
        return cidList;

    }

    /**
     * 查看收藏的联系方式
     *
     * @param maillistEntity
     * @return
     */
    @Override
    public List<MaillistEntity> getCollect(MaillistEntity maillistEntity) {
        return maillistMapper.getCollect(maillistEntity);
    }

    /**
     * 添加收藏
     *
     * @param maillistEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int collectAdd(MaillistEntity maillistEntity) {
        return maillistMapper.collectAdd(maillistEntity);
    }

    /**
     * 取消收藏
     *
     * @param maillistEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int collectDelete(MaillistEntity maillistEntity) {
        return maillistMapper.collectDelete(maillistEntity);
    }
}
