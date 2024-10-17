package com.wr.estate.service.info;

import com.wr.remote.estate.info.Banner;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:50:55
 * @Desc:
 */
public interface BannerService {
  /*
   * 查询列表
   * */
  List<Banner> selectList(Banner banner);

  /**
   * 根据主键获取对象
   * @param bannerId 主键
   * @return
   */
  Banner selectBannerById(Long bannerId);

  /**
   * 校验序号唯一性
   *  banner
   * @param
   * @return
   */
  List<Banner> checkOrderNumUnique(Banner banner);

  /*
   * 修改时检查序号唯一性
   * */
  List<Banner> checkOrderNumUnique2(Banner banner);

  /**
   * 新增社区头条
   * @param banner
   * @return
   */
  Integer insertBanner(Banner banner);

  /**
   * 修改头条
   * @param banner 车辆信息
   * @return
   */
  Integer updateBanner(Banner banner);

  /**
   * 删除头条
   * @param bannerIds 主键
   * @return
   */
  Integer deleteBannerByIds(Long[] bannerIds);
}
