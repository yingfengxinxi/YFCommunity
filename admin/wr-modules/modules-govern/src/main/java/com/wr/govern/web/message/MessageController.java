package com.wr.govern.web.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.common.core.constant.ScheduleConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.sign.Base64;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.message.MessageService;
import com.wr.govern.service.party.PartyService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.Message;
import com.wr.remote.govern.party.vo.MessageVO;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-10-28 14:23:18
 * @Desc: 党建资讯 控制层
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private PartyService partyService;

    /**
     * 获取列表
     *
     * @param messageVO
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:message:list")
    @GetMapping("/list")
    public TableDataInfo list(MessageVO messageVO) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        messageVO.setEstateId(estateId);
        startPage();
        List<MessageVO> memberAll = messageService.getMessageAll(messageVO);
        return getDataTable(memberAll);
    }

    /**
     * 获取详细信息
     */
    @RequiresPermissions("govern:message:query")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable Long messageId) {
        MessageVO messageById = messageService.getMessageById(messageId);
        return AjaxResult.success(messageById);
    }

    /**
     * 添加
     *
     * @param message
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:message:add")
    @Log(title = "党建资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody MessageVO message) {
        message.setContent( new String( Base64.decode( message.getContent() )) );
        // 获取小区名称
        String communityName = partyService.getCommunityName(message.getCommunityId());
        if (UserConstants.NOT_UNIQUE.equals(messageService.checkNameUnique(message))) {
            return AjaxResult.error("添加标题'" + message.getMsgTitle() + "'失败，该项目：" + communityName + "下已建立过该标题");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        message.setCreateBy(userName);
        message.setUpdateBy(userName);
        Integer num = messageService.addMessage(message);
        return toAjax(num);
    }

    /**
     * 删除
     *
     * @param messageId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:message:remove")
    @Log(title = "党建资讯", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{messageId}")
    public AjaxResult remove(@PathVariable Long[] messageId) {
        Integer num = messageService.delMessage(messageId);
        return toAjax(num);
    }

    /**
     * 党建资讯置顶
     *
     * @param message
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @Log(title = "党建资讯置顶", businessType = BusinessType.OTHER)
    @PostMapping("/topMessage")
    @Transactional
    public AjaxResult topMessage(@RequestBody Message message) {
        if(message == null || message.getMessageId() == null){
            throw new ServiceException("获取党建信息ID失败，请稍后重试！");
        }
        //更新所有的数据为0
        LambdaUpdateWrapper<Message> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq( Message::getDelFlag, ScheduleConstants.Status.NORMAL.getValue() )
                .set( Message::getTop,ScheduleConstants.Status.NORMAL.getValue() );
        messageService.update(wrapper);
        //需要置顶数据更新为1
        LambdaUpdateWrapper<Message> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq( Message::getDelFlag, ScheduleConstants.Status.NORMAL.getValue() )
                .eq( Message::getMessageId,message.getMessageId() )
                .set( Message::getTop,ScheduleConstants.Status.PAUSE.getValue() );
        boolean update = messageService.update( updateWrapper );
        return toAjax(update);
    }
}