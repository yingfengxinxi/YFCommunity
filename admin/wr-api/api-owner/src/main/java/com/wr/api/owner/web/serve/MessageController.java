package com.wr.api.owner.web.serve;

import com.wr.remote.domain.ApiMessage;
import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.remote.domain.vo.ApiMessageVo;
import com.wr.api.owner.service.serve.MessageService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 党建资讯
 * @Author: lvzy
 * @Date: 2022-11-15 16:41:39
 * @Desc:党建资讯
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/message")
public class MessageController extends BaseController {

    private final MessageService messageService;

    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiMessageVo apiMessage) {
        new ExtractCode().pageListAntiSql(apiMessage.getPageNum(), apiMessage.getPageSize());
        return getDataTable(messageService.listPage(apiMessage.getCommunityId()));
    }

    @GetMapping("/detail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult detail(ApiMessage apiMessage) {
        return messageService.detail(apiMessage.getMessageId());
    }
}
