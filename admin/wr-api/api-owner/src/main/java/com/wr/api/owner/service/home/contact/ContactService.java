package com.wr.api.owner.service.home.contact;

import com.wr.api.owner.entity.home.contact.Contact;

import java.util.List;

/**
 * @author DIANWEI
 * 常用电话 service
 * @date 2022/11/17 10:17
 */
public interface ContactService {
    /**
     * @Description  常用电话列表查询
     * @Param [contact]
     * @return com.wr.api.owner.entity.home.contact.Contact
     * @Date 2022/11/17 10:15
     * @Author DIANWEI
     **/
    public List<Contact> selectContactList(Contact contact);
}
