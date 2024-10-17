package com.wr.estate.web.center;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.CarportNoService;
import com.wr.remote.domain.CarportNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/6 15:47
 */
@Api(tags = "物管平台-车位编号管理")
@RestController
@RequestMapping("carportNo")
public class CarportNoController extends BaseController {

    @Autowired
    private CarportNoService carportNoService;


    @ApiOperation("车位管理分页")
    @GetMapping("/list")
    public TableDataInfo list(CarportNo carportNo) {
        startPage();
        return getDataTable(carportNoService.list(carportNo));
    }


    @ApiOperation("车位编号详情")
    @GetMapping("/{carportNoId}")
    public AjaxResult get(@PathVariable("carportNoId") Long carportNoId) {

        return AjaxResult.success(carportNoService.get(carportNoId));
    }

    @ApiOperation("车位编号下拉")
    @GetMapping("/getAllCarportNo")
    public AjaxResult getAllCarportNo(@RequestParam("communityId") Long communityId) {

        return AjaxResult.success(carportNoService.getAllCarportNo(communityId));
    }

    @ApiOperation("车位编号添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody CarportNo carportNo) {
        Integer addCheckCount = carportNoService.addCheckCount(carportNo);
        if (addCheckCount >= 1) {
            return AjaxResult.error("车位编号已存在，请勿重复添加");
        }
        carportNo.setCreateBy(SecurityUtils.getUsername());
        carportNoService.insert(carportNo);
        return AjaxResult.success();
    }

    @ApiOperation("车位编号修改")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody CarportNo carportNo) {
        Integer updateCheckCount = carportNoService.updateCheckCount(carportNo);
        if (updateCheckCount >= 1) {
            return AjaxResult.error("车位编号已存在，请勿重复添加");
        }
        carportNo.setUpdateBy(SecurityUtils.getUsername());
        carportNoService.update(carportNo);
        return AjaxResult.success();
    }

    /**
     * @param carportNoIds
     * @return
     */
    @ApiOperation("车位编号批量删除")
    @PostMapping("deleteById")
    public AjaxResult deleteById(@RequestParam("carportNoIds") Long[] carportNoIds) {

        for (Long carportNoId : carportNoIds) {
            carportNoService.delete(carportNoId);
        }
        return AjaxResult.success();
    }

    @ApiOperation("导出模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<CarportNo> util = new ExcelUtil<CarportNo>(CarportNo.class);
        util.importTemplateExcel(response, "车辆编号信息");
    }

    @ApiOperation("批量导入车位编号信息")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<CarportNo> util = new ExcelUtil<CarportNo>(CarportNo.class);
        List<CarportNo> dataList = util.importExcel(file.getInputStream());
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (CarportNo carportNo : dataList) {
                carportNo.setCreateBy(SecurityUtils.getUsername());
                carportNo.setCommunityId(communityId);
                Integer i = carportNoService.addCheckCount(carportNo);
                if (i <= 0) {
                    carportNoService.insert(carportNo);
                }

            }
            return AjaxResult.success("成功导入" + dataList.size() + "条车位编号");
        } else {
            return AjaxResult.error("请勿导入空文件");
        }

    }

}
