package com.wr.estate.web.customer.goods;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.goods.CompensateService;
import com.wr.remote.estate.customer.goods.vo.CompensateVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 11:47:45
 * @Desc: 赔偿记录控制层
 */
@RestController
@RequestMapping("/compensate")
public class CompensateController extends BaseController {

    @Autowired
    private CompensateService compensateService;

    /**
     * 分页查询
     *
     * @param compensateVo 筛选条件
     * @return 查询结果
     */
    @RequiresPermissions("business:compensate:list")
    @GetMapping("/list")
    public TableDataInfo list(CompensateVo compensateVo){
        startPage();
        List<CompensateVo> list = compensateService.selectCompensateList(compensateVo);
        return getDataTable(list);
    }

    /**
     * 新增数据
     *
     * @param goodsVo 实体
     * @return 新增结果
     */
    @Log(title = "新增赔偿记录", businessType = BusinessType.INSERT)
    @RequiresPermissions("business:compensate:insert")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody GoodsVo goodsVo) {
        return AjaxResult.success(compensateService.insertCompensate(goodsVo));
    }
}
