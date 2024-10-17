package com.wr.govern.web.party;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.party.PartyService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.party.Party;
import com.wr.remote.govern.party.vo.PartyVO;
import com.wr.remote.system.RemoteFileService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-10-25 11:29:21
 * @Desc: 党组织管理 控制层
 */
@RestController
@RequestMapping("/party")
public class PartyController extends BaseController {
    @Autowired
    private PartyService partyService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 获取党组织列表
     * @param Party
     * @return: com.wr.common.core.web.page.TableDataInfo
     * @author: SJiang
     * @date: 2022/10/25 11:33
     */
    @RequiresPermissions("govern:party:list")
    @GetMapping("/list")
    public TableDataInfo list(Party Party) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        Party.setEstateId(estateId);
        startPage();
        List<PartyVO> partyList = partyService.getPartyList(Party);
        return getDataTable(partyList);
    }

    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{partyId}")
    public AjaxResult getInfo(@PathVariable Long partyId) {
        PartyVO partyById = partyService.getPartyById(partyId);
        return AjaxResult.success(partyById);
    }

    @RequiresPermissions("govern:party:add")
    @Log(title = "党组织管理", businessType = BusinessType.INSERT)
    @PostMapping ("/add")
    public AjaxResult add(@RequestBody Party party) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            AjaxResult.error("添加党组织名称'" + party.getPartyName() + "'失败，请联系管理员");
        }
        party.setEstateId(sysUser.getEstateId());
        if (UserConstants.NOT_UNIQUE.equals(partyService.checkNameUnique(party))) {
            return AjaxResult.error("添加党组织名称'" + party.getPartyName() + "'失败，该组织已建立");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        party.setCreateBy(userName);
        party.setUpdateBy(userName);
        Integer num = partyService.addParty(party);
        return toAjax(num);
    }

    @RequiresPermissions("govern:party:edit")
    @Log(title = "党组织管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody Party party) {
        // 获取小区名称
        String communityName = partyService.getCommunityName(party.getCommunityId());
        if (UserConstants.NOT_UNIQUE.equals(partyService.checkNameUnique(party))) {
            return AjaxResult.error("修改党组织名称'" + party.getPartyName() + "'失败，该组织已建立");
        }
        // 获取登录名称
        String userName = SecurityUtils.getUsername();
        party.setUpdateBy(userName);
        Integer num = partyService.upParty(party);
        return toAjax(num);
    }

    @RequiresPermissions("govern:party:remove")
    @Log(title = "党组织管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{partyId}")
    public AjaxResult remove(@PathVariable Long partyId) {
        return partyService.delParty(partyId);
    }

    @Log(title = "图片上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData()))
            {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if(R.FAIL == fileResult.getCode()){
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.put("imgUrl",url);
            return ajaxResult;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 查询顶层党组织
     * @return
     */
    @GetMapping("/topList")
    public AjaxResult listPartyOrganizationForTopOrganization(){
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return null;
        }
        Long estateId = sysUser.getEstateId();
        List<PartyVO> topPartyAll = partyService.getTopPartyAll(estateId);
        return AjaxResult.success(topPartyAll);
    }

    /**
     * 查询上级党组织
     * @return
     */
    @GetMapping("/partyList")
    public AjaxResult listParentPartyAll(){
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return null;
        }
        Long estateId = sysUser.getEstateId();
        List<PartyVO> topPartyAll = partyService.getParentPartyAll(estateId);
        return AjaxResult.success(topPartyAll);
    }

    /**
     * 获取所有党员（审核通过）
     * @return
     */
    @GetMapping("/memberAll")
    public AjaxResult listMember(){
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return null;
        }
        Long estateId = sysUser.getEstateId();
        List<PartyVO> memberAll = partyService.getMemberAll(estateId);
        return AjaxResult.success(memberAll);
    }
}
