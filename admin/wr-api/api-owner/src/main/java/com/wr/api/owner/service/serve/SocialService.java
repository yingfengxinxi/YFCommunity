package com.wr.api.owner.service.serve;

import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 16:07:23
 * @Desc:社交表
 */
public interface SocialService {

    /**
     * 分页查询
     * @param apiSocialVo
     * @return
     */
    List<ApiSocialVo> listPage(ApiSocialVo apiSocialVo);

    /**
     * 新增-我要发布
     * @param apiSocial
     * @return
     */
    AjaxResult add(ApiSocial apiSocial);

    /**
     * 点赞
     * @param apiSocialLike
     * @return
     */
    AjaxResult like(ApiSocialLikeVo apiSocialLike);

    /**
     * 评论
     * @param apiSocialComment
     * @return
     */
    AjaxResult comment(ApiSocialComment apiSocialComment);

    /**
     * 详情
     * @param socialId
     * @return
     */
    ApiSocialVo detail(Long socialId);
}
