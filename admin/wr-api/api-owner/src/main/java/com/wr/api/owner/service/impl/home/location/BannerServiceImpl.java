package com.wr.api.owner.service.impl.home.location;

import com.wr.api.owner.entity.home.location.Banner;
import com.wr.api.owner.entity.home.location.BannerExample;
import com.wr.api.owner.mapper.home.location.BannerMapper;
import com.wr.api.owner.service.home.location.BannerService;
import com.wr.common.core.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 17:44:21
 * @Desc: 轮播图实现类
 */
@Service
public class BannerServiceImpl implements BannerService {


    @Resource
    private BannerMapper bannerMapper;


    /**
     * 查询轮播图集合
     * @param estateId  物业Id
     * @return
     */
    @Override
    @Transactional
    public List<Banner> selectListBanner(Long estateId, String type) {
        BannerExample bannerExample=new BannerExample();
        BannerExample.Criteria criteria = bannerExample.createCriteria();
        criteria.andDelFlagEqualTo("0");
        if (null != estateId){
            criteria.andEstateIdEqualTo(estateId);
        }
        if (StringUtils.isNotBlank(type)){
            criteria.andTypeEqualTo(type);
        }
        List<Banner> banners = bannerMapper.selectByExample(bannerExample);
        return banners;
    }


}
