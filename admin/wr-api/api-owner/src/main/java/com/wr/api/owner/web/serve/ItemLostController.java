package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.ItemLostDTO;
import com.wr.api.owner.entity.dto.MyProItemLostDTO;
import com.wr.api.owner.entity.vo.serve.ItemLostVo;
import com.wr.api.owner.service.serve.ItemLostService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:42:43
 * @Desc: 寻物启示
 */
@RestController
@RequestMapping("/v2/Item")
public class ItemLostController extends BaseController {

    @Autowired
    private ItemLostService service;

    /**
     * 寻物品启示
     * @param itemLostVo
     * @return
     */
    @GetMapping("/itemList")
    @AuthRequired
    public TableDataInfo getList(ItemLostVo itemLostVo) {
        startPage();
        List<ItemLostDTO> list = service.getItemLostList(itemLostVo);
        return getDataTable(list);
    }

    /**
     * 寻物启示详情
     * @param lostId
     * @return
     */
    @GetMapping("item/{lostId}")
    @AuthRequired
    public AjaxResult getListInfo(@PathVariable Long lostId){
        List<ItemLostVo> list =service.getListInfo(lostId);
        return AjaxResult.success(list);
    }

    /**
     * 修改寻物发布的状态
     * @param itemLostVo
     * @return
     */
    @PostMapping("/update")
    @AuthRequired
    @ApiLog(title = "修改寻物发布的状态",businessType = BusinessType.UPDATE)
    public AjaxResult updateByLost(@RequestBody ItemLostVo itemLostVo)
    {
        return AjaxResult.success(service.updateByLost(itemLostVo));
    }

    /**
     * 发布寻物启示
     * @param itemLostVo
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    @ApiLog(title = "新增寻物发布",businessType = BusinessType.INSERT)
    public AjaxResult insertProLost(@RequestBody ItemLostVo itemLostVo){
        return  AjaxResult.success(service.insertItemLost(itemLostVo));
    }

    /**
     * 我得寻物启示
     * @param accountId
     * @return
     */
    @GetMapping("/{accountId}")
    @AuthRequired
    public TableDataInfo getMyList(@PathVariable Long accountId){

        startPage();
        List<MyProItemLostDTO> list = service.getItemMyLostList(accountId);
        return getDataTable(list);
    }
}
