package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.service.application.serve.BulletinService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 13:10:54
 * @Desc:
 */
@RestController
@RequestMapping("v2/bulletin")
public class BulletinController extends BaseController {

    @Resource
    private BulletinService bulletinService;

    @GetMapping("search")
    @AuthRequired
    @EncryptResponse
    public TableDataInfo search(Long communityId){
        startPage();
        //String ciphertext
//        Long communityId = DecryptUtil.decryptAes(ciphertext, Long.class);
//        if(null == communityId){
//            return AjaxResult.error("系统错误，请重试！！");
//        }
        return getDataTable(bulletinService.searchByCommunityId(communityId));
    }
    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    public AjaxResult detail(Long id){
        //String ciphertext
//        Long communityId = DecryptUtil.decryptAes(ciphertext, Long.class);
//        if(null == communityId){
//            return AjaxResult.error("系统错误，请重试！！");
//        }
        return AjaxResult.success(bulletinService.detailByBulletinId(id));
    }
}
