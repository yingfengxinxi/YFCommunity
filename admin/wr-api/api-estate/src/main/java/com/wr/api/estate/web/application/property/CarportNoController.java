package com.wr.api.estate.web.application.property;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.api.estate.service.application.serve.CarportNoService;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.CarportNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/6 15:47
 */
@Api(tags = "物业移动端-车位编号管理")
@RestController
@RequestMapping("v2/carportNo")
public class CarportNoController extends BaseController {

    @Autowired
    private CarportNoService carportNoService;

    @ApiOperation("车位编号下拉")
    @GetMapping("/getAllCarportNo")
    public AjaxResult getAllCarportNo(@RequestParam("communityId") Long communityId) {

        return AjaxResult.success(carportNoService.getAllCarportNo(communityId));
    }
}
