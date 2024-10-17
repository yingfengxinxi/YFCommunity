package com.wr.api.owner.web.serve;

import com.wr.api.owner.service.serve.SocialService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.domain.ApiSocialRang;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 社交表
 *
 * @Author: lvzy
 * @Date: 2022-11-16 16:07:05
 * @Desc:社交表
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/social")
public class SocialController extends BaseController {

    private final SocialService socialService;

    @PostMapping("/add")
//    @AuthRequired
//    @EncryptResponse
    @ApiLog(title = "新增我要发布", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody ApiSocial apiSocial) {
        return socialService.add( apiSocial );
    }

    @PostMapping("/like")
    @AuthRequired
//    @EncryptResponse
    @ApiLog(title = "社交点赞", businessType = BusinessType.INSERT)
    public AjaxResult like(@RequestBody ApiSocialLikeVo apiSocialLike) {
        return socialService.like( apiSocialLike );
    }

    @PostMapping("/comment")
    @AuthRequired
//    @EncryptResponse
    @ApiLog(title = "社交评论", businessType = BusinessType.INSERT)
    public AjaxResult comment(@RequestBody ApiSocialComment apiSocialComment) {
        return socialService.comment( apiSocialComment );
    }

    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiSocialVo apiSocialVo) {
        return getDataTable( socialService.listPage( apiSocialVo ) );
    }

    @GetMapping("/detail")
//    @AuthRequired
//    @EncryptResponse
    public ApiSocialVo detail(ApiSocialRang apiSocialRang) {
        return socialService.detail( apiSocialRang.getSocialId() );
    }
}
