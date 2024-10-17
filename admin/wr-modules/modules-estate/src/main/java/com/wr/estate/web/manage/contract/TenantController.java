package com.wr.estate.web.manage.contract;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 租客信息controller
 */
@RestController
@RequestMapping("/tenant")
public class TenantController extends BaseController {
    @Autowired
    private RemoteFileService remoteFileService;


    /**
     * 上传图片
     * @param file
     * @return
     */
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
