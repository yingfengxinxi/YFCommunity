package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.BannerMapper;
import com.wr.api.estate.service.application.serve.BannerService;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

  @Autowired
  private BannerMapper bannerMapper;

  @Override
  public List<Banner> selectList() {
    return bannerMapper.selectListByestateId();
  }
}
