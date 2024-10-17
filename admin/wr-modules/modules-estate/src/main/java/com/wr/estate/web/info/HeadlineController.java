package com.wr.estate.web.info;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.info.HeadlineService;
import com.wr.remote.estate.info.Headline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @Author: 乐进
 * @Date: 2022-10-11 11:58:48
 * @Desc:
 */
@RestController
@RequestMapping("/headline")
public class HeadlineController extends BaseController {
  @Autowired
  private HeadlineService headlineService;

  /**
   * 社区头条列表
   */
  @RequiresPermissions("estate:headline:list")
  @GetMapping("/list")
  public TableDataInfo list(Headline headline) {
    startPage();
    headline.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
    List<Headline> list = headlineService.selectList(headline);
    return getDataTable(list);
  }

  /**
   * 根据编号获取详细信息
   */
  @RequiresPermissions("estate:headline:query")
  @GetMapping(value = "/{headlineId}")
  public AjaxResult getInfo(@PathVariable Long headlineId) {
//      headlineService.updateReadNum(headlineId);
    Headline headline = headlineService.selectHeadlineById(headlineId);
    return AjaxResult.success(headline);
  }

  /**
   * 添加社区头条
   */
  @RequiresPermissions("estate:headline:add")
  @Log(title = "添加社区头条", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@Validated @RequestBody Headline headline) {
    List<Headline> headlines = headlineService.checkTitleUnique(headline);
    if (headlines.size() > 0) {
      return AjaxResult.error("添加头条失败,标题重复");
    }
    headline.setCreateBy(SecurityUtils.getUsername());
    int row = headlineService.insertHeadline(headline);
    return toAjax(row);
  }

  /**
   * 修改社区头条
   */
  @RequiresPermissions("estate:headline:edit")
  @Log(title = "修改社区头条", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@Validated @RequestBody Headline headline) {
    List<Headline> headlines = headlineService.checkTitleUnique2(headline);
    if (headlines.size() > 0) {
      return AjaxResult.error("修改头条失败,标题重复");
    }
    headline.setUpdateBy(SecurityUtils.getUsername()) ;
    int row = headlineService.updateHeadline(headline);
    return toAjax(row);
  }

  /**
   * 删除社区头条
   *
   * @param headlineIds 主键
   * @return
   */
  @RequiresPermissions("estate:headline:remove")
  @Log(title = "删除社区头条", businessType = BusinessType.DELETE)
  @DeleteMapping("/{headlineIds}")
  public AjaxResult remove(@PathVariable Long[] headlineIds) {
    int row = headlineService.deleteHeadlineByIds(headlineIds);
    return toAjax(row);
  }

}
