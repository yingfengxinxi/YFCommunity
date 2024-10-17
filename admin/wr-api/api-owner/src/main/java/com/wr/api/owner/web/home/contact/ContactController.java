package com.wr.api.owner.web.home.contact;

import com.wr.api.owner.entity.home.contact.Contact;
import com.wr.api.owner.service.home.contact.ContactService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DIANWEI
 * 常用电话 controller
 * @date 2022/11/17 10:16
 */
@RequestMapping("/v2/contact")
@RestController
public class ContactController extends BaseController {

    @Autowired
    private ContactService contactService;

    /**
     * @return com.wr.common.core.web.page.TableDataInfo
     * @Description常用电话列表
     * @Param [contact]
     * @Date 2022/11/17 10:26
     * @Author DIANWEI
     **/
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo selectContactList(Contact contact) {
        startPage();
        List<Contact> contactList = contactService.selectContactList(contact);
        return getDataTable(contactList);
    }
}
