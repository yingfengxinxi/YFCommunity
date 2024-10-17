package com.wr.api.owner.service.impl.home.contact;

import com.wr.api.owner.entity.home.contact.Contact;
import com.wr.api.owner.mapper.home.contact.ContactMapper;
import com.wr.api.owner.service.home.contact.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DIANWEI
 *  常用电话 service实现类
 * @date 2022/11/17 10:17
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public List<Contact> selectContactList(Contact contact) {

        return contactMapper.selectContactList(contact);
    }
}
