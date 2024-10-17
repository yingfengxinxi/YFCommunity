package com.wr.govern.service.message.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.UserConstants;
import com.wr.govern.mapper.message.MessageMapper;
import com.wr.govern.service.message.MessageService;
import com.wr.remote.govern.party.Message;
import com.wr.remote.govern.party.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-28 14:00:34
 * @Desc: 党建咨询 业务实现
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper,Message> implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public List<MessageVO> getMessageAll(MessageVO message) {
        return messageMapper.getMessageAll(message);
    }

    @Override
    public MessageVO getMessageById(Long messageId) {
        return messageMapper.getMessageById(messageId);
    }

    @Override
    public String checkNameUnique(MessageVO member) {
        //查看是否已存在
        Message nameUnique = messageMapper.checkNameUnique(member);
        if (ObjectUtils.isNotEmpty(nameUnique)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer addMessage(MessageVO message) {
        Integer num = messageMapper.addMessage(message);
        messageMapper.addMsgParty(message.getMessageId(), message.getPartyIds());
        return num;
    }

    @Override
    public Integer delMessage(Long [] messageIds) {
        return messageMapper.delMessage(messageIds);
    }
}
