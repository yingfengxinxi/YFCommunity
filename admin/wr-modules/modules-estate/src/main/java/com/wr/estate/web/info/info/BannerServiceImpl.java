package com.wr.estate.web.info.info;

import com.wr.estate.mapper.info.BannerMapper;
import com.wr.estate.service.info.BannerService;
import com.wr.remote.estate.info.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:51:39
 * @Desc:
 */
@Service
public class BannerServiceImpl implements BannerService {

  @Autowired
  private BannerMapper bannerMapper;

  @Override
  public List<Banner> selectList(Banner banner) {
    return bannerMapper.selectList(banner);
  }

  @Override
  public Banner selectBannerById(Long bannerId) {
    return bannerMapper.selectBannerById(bannerId);
  }

  @Override
  public List<Banner> checkOrderNumUnique(Banner banner) {
    return bannerMapper.checkOrderNumUnique(banner);
  }

  @Override
  public List<Banner> checkOrderNumUnique2(Banner banner) {
    return bannerMapper.checkOrderNumUnique2(banner);
  }

  @Override
  public Integer insertBanner(Banner banner) {
    return bannerMapper.insertBanner(banner);
  }

  @Override
  public Integer updateBanner(Banner banner) {
    return bannerMapper.updateBanner(banner);
  }

  @Override
  public Integer deleteBannerByIds(Long[] bannerIds) {
    return bannerMapper.deleteBannerByIds(bannerIds);
  }
}
