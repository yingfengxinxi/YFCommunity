package com.wr.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.system.domain.Banner;
import com.wr.system.mapper.BannerMapper;
import com.wr.system.service.BannerService;
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
  public List<Banner> selectList(Long estateId, String type) {
    return bannerMapper.selectListByestateId(estateId, type);
  }
}
