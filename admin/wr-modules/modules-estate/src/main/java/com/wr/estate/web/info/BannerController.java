package com.wr.estate.web.info;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.info.BannerService;
import com.wr.remote.estate.info.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.Socket;
import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 10:53:54
 * @Desc:
 */
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController {

  @Autowired
  private BannerService bannerService;

  /**
   * 轮播图列表
   */
  @RequiresPermissions("estate:banner:list")
  @GetMapping("/list")
  public TableDataInfo list(Banner banner) {
    startPage();
    banner.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());

    List<Banner> list = bannerService .selectList(banner);
    return getDataTable(list);
  }

  /**
   * 根据编号获取详细信息
   */
  @RequiresPermissions("estate:banner:query")
  @GetMapping(value = "/{bannerId}")
  public AjaxResult getInfo(@PathVariable Long bannerId) {
//      headlineService.updateReadNum(headlineId);
    Banner banner = bannerService.selectBannerById(bannerId);
    return AjaxResult.success(banner);
  }

  /**
   * 添加轮播图
   */
  @RequiresPermissions("estate:banner:add")
  @Log(title = "添加轮播图", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@Validated @RequestBody Banner banner) {
    banner.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    // 校验序号唯一性
    List<Banner> banners = bannerService.checkOrderNumUnique(banner);
    if (banners.size() > 0) {
      return AjaxResult.error("添加轮播图设置失败,序号重复");
    }
    if (null == banner.getBannerUrl() || "".equals(banner.getBannerUrl())) {
      return AjaxResult.error("请上传图片");
    }
    banner.setCreateBy(SecurityUtils.getUsername());
    int row = bannerService.insertBanner(banner);
    return toAjax(row);
  }

  /**
   * 修改轮播图
   */
  @RequiresPermissions("estate:banner:edit")
  @Log(title = "修改轮播图", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@Validated @RequestBody Banner banner) {
//    // 校验序号唯一性
//    List<Banner> banners = bannerService.checkOrderNumUnique(banner);
//    if (banners.size() > 0) {
//      return AjaxResult.error("修改轮播图设置失败,序号不存在");
//    }
    banner.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    banner.setUpdateBy(SecurityUtils.getUsername()) ;
    int row = bannerService.updateBanner(banner);
    return toAjax(row);
  }

  /**
   * 删除轮播图
   *
   * @param bannerIds 主键
   * @return
   */
  @RequiresPermissions("estate:banner:remove")
  @Log(title = "删除轮播图", businessType = BusinessType.DELETE)
  @DeleteMapping("/{bannerIds}")
  public AjaxResult remove(@PathVariable Long[] bannerIds) {
    int row = bannerService.deleteBannerByIds(bannerIds);
    return toAjax(row);
  }

}
