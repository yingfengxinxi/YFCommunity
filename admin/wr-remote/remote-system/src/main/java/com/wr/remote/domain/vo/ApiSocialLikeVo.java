package com.wr.remote.domain.vo;

import com.wr.remote.domain.ApiSocialLike;
import lombok.Data;

import java.io.Serializable;

/**
 * 社交点赞记录扩展类
 * @Author: lvzy
 * @Date: 2022-11-25 10:56:21
 * @Desc:社交点赞记录扩展类
 */
@Data
public class ApiSocialLikeVo extends ApiSocialLike implements Serializable {

    private static final long serialVersionUID = -5576140911797830992L;

    /**
     * 点赞/取消 0:点赞, 1: 取消点赞
     */
    private String praise;

}
