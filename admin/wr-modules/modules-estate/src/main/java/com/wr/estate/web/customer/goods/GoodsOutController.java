package com.wr.estate.web.customer.goods;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.goods.GoodsOutService;
import com.wr.remote.estate.customer.goods.vo.GoodsOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 14:04:38
 * @Desc: 物品出门控制层
 */
@RestController
@RequestMapping("/goodsOut")
public class GoodsOutController extends BaseController {

    @Autowired
    private GoodsOutService goodsOutService;

    /**
     * 分页查询
     *
     * @param goodsOutVo 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    @RequiresPermissions("business:goodsOut:list")
    public TableDataInfo list(GoodsOutVo goodsOutVo){
        startPage();
        List<GoodsOutVo> list = goodsOutService.selectGoodsOutList(goodsOutVo);
        return getDataTable(list);
    }


    /**
     * 查询详情
     *
     */
    @GetMapping(value = "/getInfoById/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(goodsOutService.getInfo(id));
    }


    /**
     * 出门
     *
     * @param goodsOutVo 实体
     * @return
     */
    @Log(title = "物品出门", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:goodsOut:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody GoodsOutVo goodsOutVo) {
        return AjaxResult.success(goodsOutService.updateOutStatus(goodsOutVo));
    }
}
