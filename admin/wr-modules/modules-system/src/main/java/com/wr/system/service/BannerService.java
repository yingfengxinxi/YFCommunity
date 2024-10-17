package com.wr.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.system.domain.Banner;


import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:50:55
 * @Desc:
 */
public interface BannerService extends IService<Banner> {
  /*
   * 查询列表
   * */
  List<Banner> selectList(Long estateId, String type);
}
