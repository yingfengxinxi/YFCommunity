package com.wr.api.estate.web.application.manage;

import com.wr.api.estate.entity.dto.BorrowDTO;
import com.wr.api.estate.entity.vo.application.manage.BorrowVo;
import com.wr.api.estate.entity.vo.application.manage.GoodsVo;
import com.wr.api.estate.service.application.manage.GoodsService;
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
 * @Author: lvzy
 * @Date: 2022-11-14 19:40:21
 * @Desc:
 */
@RestController
@RequestMapping("/v2/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    @GetMapping("/goodsList")
    public TableDataInfo list(GoodsVo goodsVo) {
        startPage();
        List<GoodsVo> list = goodsService.getList(goodsVo);
        return getDataTable(list);
    }

    /**
     * 物品详情
     * @param goodsId
     * @return
     */
    @GetMapping("/goodsDetail/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(goodsService.getById(goodsId));
    }
/*List<BorrowVo> getBorrowList(Long goodsId);*/
    /**
     * 借用历史列表
     * @param goodsId
     * @return
     */
    @GetMapping("/borrowList/{goodsId}")
    public TableDataInfo borrowList(@PathVariable("goodsId") Long goodsId) {
        startPage();
        List<BorrowVo> list = goodsService.getBorrowList(goodsId);
        return getDataTable(list);
    }
/*List<BorrowVo> getApplyList(Long communityId);*/
    /**
     * 借用申请列表
     * @param communityId
     * @return
     */
    @GetMapping("/applyList/{communityId}")
    public TableDataInfo applyList(@PathVariable("communityId") Long communityId) {
        startPage();
        List<BorrowVo> list = goodsService.getApplyList(communityId);
        return getDataTable(list);
    }

    /*BorrowVo getApplyDetail(BorrowVo borrowVo);*/
    /**
     * 借用申请详情
     * @param borrowVo
     * @return
     */
    @GetMapping("/applyDetail")
    public AjaxResult applyInfo(BorrowVo borrowVo) {
        return AjaxResult.success(goodsService.getApplyDetail(borrowVo));
    }

    /**
     * 同意借用申请
     * @param borrowVo
     * @return
     */
    @PostMapping("/agree")
    @ApiLog(title="物品借用-同意", businessType = BusinessType.UPDATE)
    public AjaxResult updateBorrowAgree(@RequestBody BorrowVo borrowVo) {
        return AjaxResult.success(goodsService.updateBorrowAgree(borrowVo));
    }

    /**
     * 驳回借用申请
     * @param borrowVo
     * @return
     */
    @PostMapping("/refuse")
    @ApiLog(title="物品借用-驳回", businessType = BusinessType.UPDATE)
    public AjaxResult updateBorrowRefuse(@RequestBody BorrowVo borrowVo) {
        return AjaxResult.success(goodsService.updateBorrowRefuse(borrowVo));
    }

    /*int updateGoodsVoFree(GoodsVo goodsVo);*/
    /**
     * 确认归还
     * @param borrowDTO
     * @return
     */
    @PostMapping("/goodsFree")
    @ApiLog(title="物品借用-归还", businessType = BusinessType.UPDATE)
    public AjaxResult updateGoodsFree(@RequestBody BorrowDTO borrowDTO) {
        return AjaxResult.success(goodsService.updateGoodsVoFree(borrowDTO));
    }

    /**
     * 物品总数
     * @param communityId
     * @return
     */

    @GetMapping("/count/{communityId}")
    public AjaxResult getCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(goodsService.getCount(communityId));
    }

    /**
     * 归还物品详情
     * @param goodsId
     * @return
     */
    @GetMapping("/returnDetail/{goodsId}")
    public AjaxResult returnInfo(@PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(goodsService.getDetailById(goodsId));
    }

    /**
     * 物品列表
     * @param estateId
     * @return
     */
    @GetMapping("/nameList/{estateId}")
    public AjaxResult list(@PathVariable("estateId") Long estateId) {
        return AjaxResult.success(goodsService.getClassifyName(estateId));
    }
}
