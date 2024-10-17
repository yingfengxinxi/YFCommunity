package com.wr.api.owner.mapper.home.contact;

import com.wr.api.owner.entity.home.contact.Contact;

import java.util.List;

/**
 * @author DIANWEI
 * 常用电话 mapper
 * @date 2022/11/17 9:42
 */
public interface ContactMapper {

    /**
     * @return com.wr.api.owner.entity.home.contact.Contact
     * @Description 常用电话列表查询
     * @Param [contact]
     * @Date 2022/11/17 10:15
     * @Author DIANWEI
     **/
    public List<Contact> selectContactList(Contact contact);
}
