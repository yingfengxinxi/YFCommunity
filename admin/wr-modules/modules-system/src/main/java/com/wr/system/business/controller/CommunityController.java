package com.wr.system.business.controller;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;

import com.wr.remote.domain.*;
import com.wr.remote.system.RemoteFileService;
import com.wr.system.business.domain.CommunityVo;
import com.wr.system.business.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 社区信息管理 Controller
 *
 * @Date 2022/9/22 15:35
 * @Author DIANWEI
 **/
@RestController
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @Autowired
    private CommunityService communityVoService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 查询小区管理列表
     */
    @RequiresPermissions("system:community:list")
    @GetMapping("/list")
    public TableDataInfo list(CommunityVo communityVo) {
        startPage();
        List<CommunityVo> list = communityVoService.selectCommunityVoList(communityVo);
        return getDataTable(list);
    }

    /**
     * 获取小区管理详细信息
     */
    @RequiresPermissions("system:community:query")
    @GetMapping(value = "/{communityId}")
    public AjaxResult getInfo(@PathVariable("communityId") Long communityId) {

        return AjaxResult.success(communityVoService.selectCommunityVoById(communityId));
    }

    /**
     * 根据表名和小区id查询 通用查询
     */
    @GetMapping(value = "/table")
    public AjaxResult communityTableColumnsById(String communityTable, Long communityId) {
        Integer num = communityVoService.communityTableColumnsById(communityTable, communityId);
        return AjaxResult.success(num);
    }

    /**
     * 新增小区管理
     */
    @RequiresPermissions("system:community:add")
    @Log(title = "小区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityVo communityVo) {
        communityVo.setCreateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.insertCommunityVo(communityVo));
    }

    /**
     * 新增海康配置管理
     */
    @RequiresPermissions("system:community:addHik")
    @Log(title = "海康配置管理", businessType = BusinessType.INSERT)
    @PostMapping("/hik")
    public AjaxResult addHik(@RequestBody CommunityHik communityHik) {
        communityHik.setCreateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.insertCommunityHikVo(communityHik));
    }

    /**
     * 新增微信配置管理
     */
    @RequiresPermissions("system:community:addWechat")
    @Log(title = "微信配置管理", businessType = BusinessType.INSERT)
    @PostMapping("/wechat")
    public AjaxResult addWechat(@RequestBody CommunityWechat communityWechat) {
        communityWechat.setCreateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.insertCommunityWechatVo(communityWechat));
    }

    /**
     * 修改小区管理
     */
    @RequiresPermissions("system:community:edit")
    @Log(title = "小区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityVo communityVo) {
        communityVo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.updateCommunityVo(communityVo));
    }

    /**
     * 修改海康配置管理
     */
    @RequiresPermissions("system:community:editHik")
    @Log(title = "海康配置管理", businessType = BusinessType.UPDATE)
    @PutMapping("/hik")
    public AjaxResult editHik(@RequestBody CommunityHik communityHik) {
        communityHik.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.updateCommunityHikVo(communityHik));
    }

    /**
     * 修改微信配置管理
     */
    @RequiresPermissions("system:community:editWechat")
    @Log(title = "微信配置管理", businessType = BusinessType.UPDATE)
    @PutMapping("/wechat")
    public AjaxResult editWechat(@RequestBody CommunityWechat communityWechat) {
        communityWechat.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(communityVoService.updateCommunityWechatVo(communityWechat));
    }

    /**
     * 删除小区管理
     */
    @RequiresPermissions("system:community:remove")
    @Log(title = "小区管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{communityId}")
    public AjaxResult remove(@PathVariable Long communityId) {

        return toAjax(communityVoService.deleteCommunityVoById(communityId));
    }

    /**
     * 图片上传
     */
    @Log(title = "商户私钥上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            R<SysFile> fileResult = remoteFileService.uploadPem(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("imgUrl", url);
            ajaxResult.put("imgUrlName", url.split("/")[url.split("/").length - 1]);
            return ajaxResult;
        }
        return AjaxResult.error("私钥上传异常，请联系管理员");
    }

}
