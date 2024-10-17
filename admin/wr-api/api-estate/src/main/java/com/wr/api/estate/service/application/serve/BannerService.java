package com.wr.api.estate.service.application.serve;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.estate.info.Banner;

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
  List<Banner> selectList();
}
