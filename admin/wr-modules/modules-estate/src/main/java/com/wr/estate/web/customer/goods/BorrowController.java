package com.wr.estate.web.customer.goods;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.goods.BorrowService;
import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-12 16:27:27
 * @Desc: 借用申请表控制层
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController extends BaseController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 分页查询
     *
     * @param borrowVo 筛选条件
     * @return 查询结果
     */
    @RequiresPermissions("business:borrow:list")
    @GetMapping("/list")
    public TableDataInfo list(BorrowVo borrowVo){
        startPage();
        List<BorrowVo> list = borrowService.selectBorrowList(borrowVo);
        for (BorrowVo borrow : list){
            Long dayCount = DateUtils.getDayCount(borrow.getApplyTime(), borrow.getReturnTime());
            borrow.setBorrowDays(dayCount.intValue());
        }
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{borrowId}")
    public AjaxResult getInfo(@PathVariable Long borrowId) {
        return AjaxResult.success(borrowService.getBorrowById(borrowId));
    }


    /**
     * 修改数据
     *
     * @param borrow 实体
     * @return 修改结果
     */
    @Log(title = "借用申请修改数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:borrow:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BorrowVo borrow) {
        return AjaxResult.success(borrowService.updateBorrow(borrow));
    }

}

