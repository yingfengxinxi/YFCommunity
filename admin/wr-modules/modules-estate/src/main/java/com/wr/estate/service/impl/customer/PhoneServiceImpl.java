package com.wr.estate.service.impl;

import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.estate.mapper.PhoneMapper;
import com.wr.estate.service.PhoneService;
import com.wr.remote.estate.phone.BusContact;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private PhoneMapper phoneMapper;


    @Override
    public List<BusContact> pageList(BusContact busContact) {
        return phoneMapper.pageList(busContact);
    }

    @Override
    @Transactional
    public int deleteByIds(Long[] contactId) {
        return phoneMapper.deleteByIds(contactId);
    }

    @Override
    public String importUser(List<BusContact> phoneList, Long communityId,String operName) {
        if (StringUtils.isNull(phoneList) || phoneList.size() == 0)
        {
            throw new ServiceException("导入常用电话数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BusContact phone : phoneList)
        {
            try
            {
                if (null != phone)
                {
                    phone.setCommunityId(communityId);
                    phone.setCreateBy(operName);
                    phone.setUpdateBy(operName);
                    this.insert(phone);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、联系人 " + phone.getContactName() + " 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、联系人 " + phone.getContactName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    @Transactional
    public int insert(BusContact busContact) {
        return phoneMapper.add(busContact);
    }

    @Override
    @Transactional
    public int add(BusContact busContact) {
        return phoneMapper.add(busContact);
    }

    @Override
    public BusContact queryDeatil(Long contactId) {
        return phoneMapper.queryDeatil(contactId);
    }

    @Override
    @Transactional
    public int updatePhone(BusContact busContact) {
        return phoneMapper.updatePhone(busContact);
    }
}
