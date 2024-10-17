package com.wr.govern.service.message;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.govern.party.Message;
import com.wr.remote.govern.party.vo.MessageVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-28 13:50:33
 * @Desc: 党建资讯 业务
 */
public interface MessageService extends IService<Message> {

    /**
     * 获取党建资讯列表
     * @param message
     * @return: java.util.List<com.wr.govern.entity.Message>
     */
    List<MessageVO> getMessageAll(MessageVO message);

    /**
     * 获取详情
     * @param messageId
     * @return: java.util.List<com.wr.govern.entity.Message>
     */
    MessageVO getMessageById(Long messageId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    String checkNameUnique(MessageVO member);

    /**
     * 新增
     * @param message
     * @return: java.lang.Integer
     */
    Integer addMessage(MessageVO message);

    /**
     * 删除
     * @param messageIds
     * @return: java.lang.Integer
     */
    Integer delMessage(Long [] messageIds);
}
