package com.wr.estate.web.financial;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.service.financial.AccountManagementService;
import com.wr.remote.domain.CommunityAli;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/1
 * @Description:
 * @FileName: AccountManagementController
 * @History:
 */

@RestController
@RequestMapping("/actm")
public class AccountManagementController extends BaseController {

    @Autowired
    private AccountManagementService accountManagementService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 查询支付宝账号列表
     *
     * @param communityAli 支付宝账号实体
     * @return {@link TableDataInfo}
     * @Author RainCity
     * @Date 2023-04-27 11:48:20
     * @ver v1.0.0
     */
    @GetMapping("/ali_list")
    @PreAuthorize("@ss.hasPermi('system:actm:list')")
    public TableDataInfo aliList(CommunityAli communityAli) {
        startPage();
        List<CommunityAli> list = accountManagementService.selectAliList(communityAli);
        return getDataTable(list);
    }

    /**
     * 添加支付宝账号
     *
     * @param communityAli 支付宝账号信息
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:05:31
     * @ver v1.0.0
     */
    @PostMapping("/add_alipay")
    @PreAuthorize("@ss.hasPermi('system:actm:addalipay')")
    @Log(title = "账号管理新增支付宝账号", businessType = BusinessType.INSERT)
    public AjaxResult addAlipay(@RequestBody CommunityAli communityAli) {

        return toAjax(accountManagementService.insertAli(communityAli));
    }

    /**
     * 修改支付宝账号
     *
     * @param communityAli 支付宝账号信息
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:06:01
     * @ver v1.0.0
     */
    @PutMapping("/update_alipay")
    @PreAuthorize("@ss.hasPermi('system:actm:updatealipay')")
    @Log(title = "账号管理修改支付宝账号", businessType = BusinessType.UPDATE)
    public AjaxResult updateAlipay(@RequestBody CommunityAli communityAli) {

        return toAjax(accountManagementService.updateAli(communityAli));
    }

    /**
     * 删除支付宝账号
     *
     * @param alipayId id
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:07:22
     * @ver v1.0.0
     */
    @DeleteMapping("del_alipay/{alipayId}")
    @PreAuthorize("@ss.hasPermi('system:actm:delalipay')")
    @Log(title = "账号管理删除支付宝账号", businessType = BusinessType.DELETE)
    public AjaxResult delAlipay(@PathVariable("alipayId") Long alipayId) {

        return toAjax(accountManagementService.removeAli(alipayId));
    }

    /**
     * 查询微信账号列表
     *
     * @param communityWechat 支付宝账号实体
     * @return {@link TableDataInfo}
     * @Author RainCity
     * @Date 2023-04-27 11:48:20
     * @ver v1.0.0
     */
    @GetMapping("/wechat_list")
    @PreAuthorize("@ss.hasPermi('system:actm:list')")
    public TableDataInfo wechatList(CommunityWechat communityWechat) {
        startPage();
        List<CommunityWechat> list = accountManagementService.selectWechatList(communityWechat);
        return getDataTable(list);
    }

    /**
     * 添加微信账号
     *
     * @param communityWechat 微信账号信息
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:05:31
     * @ver v1.0.0
     */
    @PostMapping("/add_wechat")
    @PreAuthorize("@ss.hasPermi('system:actm:addwechat')")
    @Log(title = "账号管理新增微信账号", businessType = BusinessType.INSERT)
    public AjaxResult addWechat(@RequestBody CommunityWechat communityWechat) {

        CommunityWechat cw = new CommunityWechat();
        cw.setCommunityId(communityWechat.getCommunityId());
        List<CommunityWechat> communityWechats = accountManagementService.selectWechatList(cw);
        if (communityWechats.size() >= 1) {
            return AjaxResult.error("当前小区已存在账号");
        }
        return toAjax(accountManagementService.insertWechat(communityWechat));
    }

    /**
     * 修改微信账号
     *
     * @param communityWechat 微信账号信息
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:06:01
     * @ver v1.0.0
     */
    @PutMapping("/update_wechat")
    @PreAuthorize("@ss.hasPermi('system:actm:updatewechat')")
    @Log(title = "账号管理修改微信账号", businessType = BusinessType.UPDATE)
    public AjaxResult updateWechat(@RequestBody CommunityWechat communityWechat) {

        return toAjax(accountManagementService.updateWechat(communityWechat));
    }

    /**
     * 删除微信账号
     *
     * @param wechatId id
     * @return {@link AjaxResult}
     * @Author RainCity
     * @Date 2023-04-27 13:07:22
     * @ver v1.0.0
     */
    @DeleteMapping("del_wechat/{wechatId}")
    @PreAuthorize("@ss.hasPermi('system:actm:delwechat')")
    @Log(title = "账号管理删除微信账号", businessType = BusinessType.DELETE)
    public AjaxResult delWechat(@PathVariable("wechatId") Long wechatId) {

        return toAjax(accountManagementService.removeWechat(wechatId));
    }

    /**
     * 商户私钥上传
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
            ajaxResult.put("fileUrl", url);
            ajaxResult.put("fileName", url.split("/")[url.split("/").length - 1]);
            return ajaxResult;
        }
        return AjaxResult.error("私钥上传异常，请联系管理员");
    }

}
