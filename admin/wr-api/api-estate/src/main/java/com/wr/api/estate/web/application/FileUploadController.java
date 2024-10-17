package com.wr.api.estate.web.application;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.domain.R;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lvzy
 * @Date 2024/1/17 17:21
 */
@RestController
@RequestMapping("/v2/estate/upload")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileUploadController extends BaseController {

    private final RemoteFileService remoteFileService;

    /**
     * 文件上传
     */
    @PostMapping
    public AjaxResult imageUpload(@RequestParam(name = "file") MultipartFile file) {
        logger.info("文件上传======>");
        if (file.isEmpty()) {
            logger.error("请上传文件");
            return AjaxResult.error("请上传文件");
        }
        R<SysFile> fileResult = remoteFileService.upload(file);
        Assert.notNull(fileResult, "文件上传失败");
        Assert.notNull(fileResult.getData(), "文件上传失败");
        String url = fileResult.getData().getUrl();
        JSONObject data = new JSONObject();
        data.put("filePath", url);
        return AjaxResult.success(data);
    }
}
