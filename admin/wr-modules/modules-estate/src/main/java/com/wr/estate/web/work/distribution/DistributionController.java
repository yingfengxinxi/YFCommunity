package com.wr.estate.web.work.distribution;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.work.distribution.Distribution;
import com.wr.estate.service.work.distribution.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/25 10:19
 * @Version 1.5
 */
@RestController
@RequestMapping("/work/distribution")
public class DistributionController extends BaseController {
    @Autowired
    private DistributionService distributionService;


    @GetMapping("/list")
    public List<Distribution> selectList(Distribution distribution){
        return distributionService.selectList(distribution);
    }


    @Log(title = "分配新增", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('bussiness:praise:add')")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Distribution distribution){
        String operName = SecurityUtils.getUsername();
        distribution.setCreateBy(operName);
        distribution.setUpdateBy(operName);
        int row = distributionService.insertAllot(distribution);
        if (row>0){
            return AjaxResult.success("新增成功");
        } else if (row==-1) {
            return AjaxResult.error("任务已存在");
        }
        return AjaxResult.error("系统错误，请联系管理员。");
    }

    /**
     * 修改
     */
    @Log(title = "分配修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Distribution distribution)
    {
        String operName = SecurityUtils.getUsername();
        distribution.setUpdateBy(operName);
        return toAjax(distributionService.updateAllot(distribution));
    }

    @PostMapping("/allot")
    public int selectAllot(@RequestBody Distribution distribution){
        return distributionService.selectAllot(distribution);
    }



    @GetMapping("/{allotId}")
    public AjaxResult selectAll(@PathVariable("allotId") Long allotId){
        Distribution distribution = distributionService.selectAllotById(allotId);
        return AjaxResult.success(distribution);
    }


}
