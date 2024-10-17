package com.wr.api.owner.service.estateapi.featured.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.featured.FeaturedLikeMapper;
import com.wr.api.owner.service.estateapi.featured.FeaturedLikeService;
import com.wr.remote.govern.autonomy.featured.FeaturedLike;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
@Service
public class FeaturedLikeServiceImpl extends ServiceImpl<FeaturedLikeMapper,FeaturedLike> implements FeaturedLikeService {
}
