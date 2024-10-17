package com.wr.estate.web.customer.goods;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.goods.ClassifyService;
import com.wr.remote.estate.customer.goods.Classify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 13:38:14
 * @Desc: 物品分类控制层
 */
@RestController
@RequestMapping("/classify")
public class ClassifyController extends BaseController {

    @Autowired
    private ClassifyService classifyService;

    /**
     * 分页查询
     *
     * @param classify 筛选条件
     * @return 查询结果
     */
    @RequiresPermissions("business:classify:list")
    @GetMapping("/list")
    public TableDataInfo list(Classify classify){
        Long estateId = classifyService.getLoginUserEstateId();
        classify.setEstateId(estateId);
        startPage();
        List<Classify> list = classifyService.selectClassifyList(classify);
        return getDataTable(list);
    }

    /**
     * 查询全部分类
     * @return
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(){
        Classify classify = new Classify();
        Long estateId = classifyService.getLoginUserEstateId();
        classify.setEstateId(estateId);
        classify.setClassifyStatus("0");
        List<Classify> list = classifyService.selectClassifyList(classify);
        return AjaxResult.success(list);
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(classifyService.getClassifyById(id));
    }

    /**
     * 新增数据
     *
     * @param classify 实体
     * @return 新增结果
     */
    @Log(title = "分类管理新增", businessType = BusinessType.INSERT)
    @RequiresPermissions("business:classify:insert")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody Classify classify) {
        return AjaxResult.success(classifyService.insertClassify(classify));
    }

    /**
     * 修改数据
     *
     * @param classify 实体
     * @return 修改结果
     */
    @Log(title = "分类管理修改", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:classify:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody Classify classify) {
        return AjaxResult.success(classifyService.updateClassify(classify));
    }

    /**
     * 修改状态
     *
     * @param classify 实体
     * @return 修改结果
     */
    @Log(title = "分类管理修改状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateClassifyStatus")
    public AjaxResult updateClassifyStatus(@RequestBody Classify classify) {
        return AjaxResult.success(classifyService.updateClassify(classify));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 是否删除成功
     */
    @Log(title = "分类管理删除", businessType = BusinessType.DELETE)
    @RequiresPermissions("business:classify:delete")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long[] id){
        return AjaxResult.success(classifyService.deleteClassifyByIds(id));
    }
}

