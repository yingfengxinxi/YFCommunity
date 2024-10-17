package com.wr.estate.web.message;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.message.MessageService;
import com.wr.remote.domain.ApiMessage;
import com.wr.remote.domain.vo.ApiMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 党建资讯
 *
 * @Author: lvzy
 * @Date: 2022-11-15 16:41:39
 * @Desc:党建资讯
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommunityService communityService;

    @GetMapping("list")
    public TableDataInfo list(ApiMessage apiMessage) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        Long communityId = communityService.getByEstateId(estateId);
        apiMessage.setCommunityId(communityId);
        startPage();
        return getDataTable(messageService.listPage(apiMessage));
    }

    /**
     * 新增
     *
     * @param apiMessage
     * @return
     */
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody ApiMessage apiMessage) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        Long communityId = communityService.getByEstateId(estateId);
        apiMessage.setCommunityId(communityId);
        apiMessage.setTop("0");

        //判断是否存在置顶数据
        ApiMessage message = new ApiMessage();
        message.setCommunityId(communityId);
        message.setTop("0");
        List<ApiMessage> apiMessageList = messageService.listPage(message);
        if (CollectionUtils.isEmpty(apiMessageList)) {
            apiMessage.setTop("1");
        }

        messageService.insertSelective(apiMessage);
        return AjaxResult.success();
    }

    /**
     * 修改
     *
     * @param apiMessage
     * @return
     */
    @PutMapping("update")
    public AjaxResult update(@RequestBody ApiMessage apiMessage) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        Long communityId = communityService.getByEstateId(estateId);
        apiMessage.setCommunityId(communityId);
        messageService.updateByPrimaryKeySelective(apiMessage);
        return AjaxResult.success();
    }

    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam("messageId") Long messageId) {
        return AjaxResult.success(messageService.detail(messageId));
    }

    /**
     * @param messageId
     * @return
     */
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("messageId") Long messageId, @RequestParam("top") String top) {
        ApiMessageVo detail = messageService.detail(messageId);
        if (top.equals("1")) {
            ApiMessage message = new ApiMessage();
            message.setMessageId(detail.getMessageId());
            message.setCommunityId(detail.getCommunityId());
            List<ApiMessage> apiMessages = messageService.listPage(message);
            if (CollectionUtils.isNotEmpty(apiMessages)) {
                ApiMessage message1 = apiMessages.get(0);
                message1.setTop("1");
                messageService.updateByPrimaryKeySelective(message1);
            }
        }
        messageService.deleteByPrimaryKey(messageId);
        return AjaxResult.success();
    }

    /**
     * @param messageId
     * @return
     */
    @GetMapping("updateTop")
    public AjaxResult updateTop(@RequestParam("messageId") Long messageId) {

        ApiMessageVo detail = messageService.detail(messageId);
        Long communityId = detail.getCommunityId();

        ApiMessage message = new ApiMessage();
        message.setCommunityId(communityId);
        message.setTop("1");
        List<ApiMessage> apiMessageList = messageService.listPage(message);
        if (CollectionUtils.isNotEmpty(apiMessageList)) {
            ApiMessage apiMessage = apiMessageList.get(0);
            //旧置顶数据
            apiMessage.setTop("0");
            messageService.updateByPrimaryKeySelective(apiMessage);
        }


        //新置顶数据
        detail.setTop("1");
        messageService.updateByPrimaryKeySelective(detail);
        return AjaxResult.success();
    }
}
