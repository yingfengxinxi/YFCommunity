package com.wr.estate.service.message.impl;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.message.MessageMapper;
import com.wr.estate.service.message.MessageService;
import com.wr.remote.domain.ApiMessage;
import com.wr.remote.domain.vo.ApiMessageVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 党建资讯
 *
 * @Author: lvzy
 * @Date: 2022-11-15 16:42:12
 * @Desc:党建资讯
 */
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;


    /**
     * 分页
     *
     * @param communityId
     * @return
     */
    @Override
    public List<ApiMessage> listPage(ApiMessage apiMessage) {
        return messageMapper.listPage(apiMessage);
    }

    /**
     * 添加
     *
     * @param apiMessage
     */
    @Override
    public void insertSelective(ApiMessage apiMessage) {
        apiMessage.setCreateBy(SecurityUtils.getUsername());
        apiMessage.setCreateTime(new Date());
        messageMapper.insertSelective(apiMessage);
    }

    /**
     * 更新
     *
     * @param apiMessage the updated record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(ApiMessage apiMessage) {
        apiMessage.setUpdateBy(SecurityUtils.getUsername());
        apiMessage.setUpdateTime(new Date());
        return messageMapper.updateByPrimaryKeySelective(apiMessage);
    }

    @Override
    public int deleteByPrimaryKey(Long messageId) {
        return messageMapper.deleteByPrimaryKey(messageId);
    }


    /**
     * 详情
     *
     * @param messageId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ApiMessageVo detail(Long messageId) {
        ApiMessageVo detail = messageMapper.detail(messageId);
        return detail;
    }
}
