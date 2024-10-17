package com.wr.api.estate.web.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.estate.entity.vo.application.serve.CameraVO;
import com.wr.api.estate.service.application.serve.CameraService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.ServletUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import com.wr.common.security.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 20:13:53
 * @Desc:
 */
@RestController
@RequestMapping("v2/camera")
public class CameraController extends BaseController {

    @Resource
    private CameraService cameraService;

    @ApiOperation("监控点列表")
    @PostMapping("search")
    public TableDataInfo search(@RequestBody CameraVO cameraVO) {
        PageUtils.startPage(cameraVO.getPageNum(),cameraVO.getPageSize());
        return getDataTable(cameraService.searchByCommunityId(cameraVO.getCommunityId(), cameraVO.getCameraName()));
    }

    /**
     * 查询海康视频流
     *
     * @param indexCode
     * @param communityId
     * @return
     */
    @ApiOperation("查询海康视频流")
    @GetMapping("cameraUrl")
    public AjaxResult selectCameraUrl(@RequestParam("indexCode") String indexCode, @RequestParam("communityId") Long communityId) {

        return cameraService.selectCameraUrl(indexCode, communityId);
    }
}
