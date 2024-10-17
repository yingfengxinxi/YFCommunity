package com.wr.remote.govern.autonomy.featured;

import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-28 14:09:58
 * @Desc:  特色板块点赞记录表
 */
@Data
public class FeaturedLikeVO extends FeaturedLike {

    /**
     * 点赞/取消 0:点赞, 1: 取消点赞
     */
    private String praise;

}
