package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.mapper.serve.SocialCommentMapper;
import com.wr.api.owner.mapper.serve.SocialLikeMapper;
import com.wr.api.owner.mapper.serve.SocialMapper;
import com.wr.api.owner.service.serve.SocialService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.domain.Sensitive;
import com.wr.remote.domain.vo.ApiSocialCommentVo;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 16:07:34
 * @Desc:社交表
 */
@Service
@AllArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final SocialMapper socialMapper;

    private final SocialLikeMapper socialLikeMapper;

    private final SocialCommentMapper socialCommentMapper;

    @Autowired
    private RedisService redisService;
    @Override
    public List<ApiSocialVo> listPage(ApiSocialVo apiSocialVo) {
        new ExtractCode().pageListAntiSql(apiSocialVo.getPageNum(), apiSocialVo.getPageSize());
        List<ApiSocialVo> apiSocialVos = socialMapper.listPage(apiSocialVo);

        if(CollUtil.isNotEmpty( apiSocialVos )){
            for (ApiSocialVo socialVo : apiSocialVos) {

                socialVo.setComentount( socialMapper.selectCountComment( socialVo.getSocialId() ) );

//                List<ApiSocialCommentVo> apiSocialCommentVos = socialMapper.selectCommentList( socialVo.getSocialId() );
//                if(CollUtil.isNotEmpty( apiSocialCommentVos )){
//                    for (ApiSocialCommentVo apiSocialCommentVo : apiSocialCommentVos) {
//                        List<ApiSocialCommentVo> apiSocialCommentVos1 = socialMapper.selectNextCommentList( apiSocialCommentVo.getCommentId() );
//                        if(CollUtil.isNotEmpty( apiSocialCommentVos1 )){
//                            apiSocialCommentVo.setSocialCommentVos( apiSocialCommentVos1 );
//                        }
//                    }
//                }
//                socialVo.setCommentList( apiSocialCommentVos );
            }
        }
        return apiSocialVos;
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult add(ApiSocial apiSocial) {
        List<Sensitive> sensitiveList = redisService.getCacheObject( CacheConstants.SENSITIVE);
        if (CollUtil.isNotEmpty( sensitiveList )) {
            List<String> phrasesList = sensitiveList.stream().map(Sensitive::getPhrases).collect( Collectors.toList());
            ApiSocial apiSocial1 = SensitiveUtil.sensitiveFilterOwn( apiSocial, phrasesList );
            if (apiSocial1.getContent().contains("**")) {
                throw new ServiceException("输入内容存在敏感词信息,请重新输入");
            }
        }

        int save = socialMapper.insertSelective( apiSocial );
        save = socialMapper.insertRang( apiSocial );
        return AjaxResult.success(save>0?"新增成功":"新增失败，请稍后重试！");
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult like(ApiSocialLikeVo apiSocialLike) {
        if ("0".equals(apiSocialLike.getPraise())) {
            Integer result = socialLikeMapper.checkLike(apiSocialLike);
            if(result==null || result>0){
                return AjaxResult.error("不可重复点赞");
            }
            return AjaxResult.success(socialLikeMapper.insertSelective(apiSocialLike));
        } else {
            return AjaxResult.success(socialLikeMapper.remove(apiSocialLike));
        }
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult comment(ApiSocialComment apiSocialComment) {
        List<Sensitive> sensitiveList = redisService.getCacheObject( CacheConstants.SENSITIVE);
        if (CollUtil.isNotEmpty( sensitiveList )) {
            List<String> phrasesList = sensitiveList.stream().map(Sensitive::getPhrases).collect( Collectors.toList());
            ApiSocialComment apiSocial1 = SensitiveUtil.sensitiveFilterOwn( apiSocialComment, phrasesList );
            if (apiSocial1.getContent().contains("**")) {
                throw new ServiceException("输入内容存在敏感词信息,请重新输入");
            }
        }

        socialCommentMapper.insertSelective(apiSocialComment);
        ApiSocialComment socialComment = socialCommentMapper.selectByPrimaryKey(apiSocialComment.getCommentId());
        return AjaxResult.success(socialComment);
    }

    @Override
    public ApiSocialVo detail(Long socialId) {
        ApiSocialVo detail = socialMapper.detilSocial( socialId );
        detail.setComentount( socialMapper.selectCountComment( detail.getSocialId() ) );
        List<ApiSocialCommentVo> apiSocialCommentVos = socialMapper.selectCommentList( socialId );
        if(CollUtil.isNotEmpty( apiSocialCommentVos )){
            for (ApiSocialCommentVo apiSocialCommentVo : apiSocialCommentVos) {
                List<ApiSocialCommentVo> apiSocialCommentVos1 = socialMapper.selectNextCommentList( apiSocialCommentVo.getCommentId() );
                if(CollUtil.isNotEmpty( apiSocialCommentVos1 )){
                    apiSocialCommentVo.setSocialCommentVos( apiSocialCommentVos1 );
                }
            }
        }
        detail.setCommentList( apiSocialCommentVos );
        return detail;
    }
}
