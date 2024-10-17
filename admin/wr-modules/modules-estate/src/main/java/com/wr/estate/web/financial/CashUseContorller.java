package com.wr.estate.web.financial;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.financial.CashUseService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.financial.EstateCash;
import com.wr.remote.system.RemoteFileService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:46:07
 * @Desc:
 */

@RestController
@RequestMapping("/cashUse")
public class CashUseContorller extends BaseController {

    @Autowired
    private CashUseService cashUseService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 列表
     * @param
     * @return
     */
    @GetMapping("/list")
//    @RequiresPermissions("business:activitetype:list")
    public TableDataInfo list(EstateCash estateCash){
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.isEmpty(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        Long estateId = sysUser.getEstateId();
        estateCash.setEstateId(estateId);
        startPage();
        List<EstateCash> list = cashUseService.selectList(estateCash);
        return getDataTable(list);
    }
    /**
     * 查询投票详情
     */
    @GetMapping(value = "/infoByVoteId/{voteId}")
    public AjaxResult infoByVoteId(@PathVariable("voteId") Long voteId) {
        return AjaxResult.success(cashUseService.infoByVoteId(voteId));
    }

    /**
     * 修改数据
     *
     * @param estateCash 实体
     * @return 修改结果
     */
    @Log(title = "申请修改数据", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("business:borrow:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody EstateCash estateCash) {
        return AjaxResult.success(cashUseService.updateGrant(estateCash));
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @Log(title = "服务合同图片上传", businessType = BusinessType.INSERT)
    @RequiresPermissions("manage:serve:upload")
    @PostMapping("/uploadPicture")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            //MultipartFile temp = ThumbnailsUtils.compressMultipart(filePath,file,1280,1080);
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if(R.FAIL == fileResult.getCode()){
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgPath", url);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }


}
