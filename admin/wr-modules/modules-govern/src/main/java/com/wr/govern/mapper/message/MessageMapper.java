package com.wr.govern.mapper.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.govern.party.Message;
import com.wr.remote.govern.party.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-28 11:18:07
 * @Desc: 党建资讯 mapper
 */
public interface MessageMapper extends BaseMapper<Message> {

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
    MessageVO getMessageById(@Param("messageId")Long messageId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    Message checkNameUnique(MessageVO member);

    /**
     * 新增
     * @param message
     * @return: java.lang.Integer
     */
    Integer addMessage(MessageVO message);

    /**
     * 新增党建资讯-党组织关联表
     * @param messageId
     * @param partyIds
     * @return: java.lang.Integer
     */
    Integer addMsgParty(@Param("messageId") Long messageId, @Param("partyIds") Long [] partyIds);

    /**
     * 删除
     * @param messageIds
     * @return: java.lang.Integer
     */
    Integer delMessage(@Param("messageIds") Long [] messageIds);
}
