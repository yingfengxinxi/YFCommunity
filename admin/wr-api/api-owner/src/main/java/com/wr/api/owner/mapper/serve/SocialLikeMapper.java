package com.wr.api.owner.mapper.serve;

import com.wr.remote.domain.ApiSocialLike;
import com.wr.remote.domain.vo.ApiSocialLikeVo;

/**
 * 社交点赞记录表
 * @Author: lvzy
 * @Date: 2022-11-16 15:59:06
 * @Desc:社交点赞记录表
 */
public interface SocialLikeMapper {

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ApiSocialLike record);

    /**
     * 取消点赞
     * @param apiSocialLike
     * @return
     */
    Integer remove(ApiSocialLikeVo apiSocialLike);

    /**
     * 检查是否点赞
     * @param apiSocialLike
     * @return
     */
    Integer checkLike(ApiSocialLikeVo apiSocialLike);
}