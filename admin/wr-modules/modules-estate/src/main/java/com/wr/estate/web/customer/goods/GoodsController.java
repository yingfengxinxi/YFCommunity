package com.wr.estate.web.customer.goods;

import com.wr.common.core.domain.R;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.goods.BorrowService;
import com.wr.estate.service.customer.goods.GoodsService;
import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 19:43:53
 * @Desc: 物品管理控制层
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 分页查询
     *
     * @param goodsVo 筛选条件
     * @return 查询结果
     */
    @RequiresPermissions("business:goods:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsVo goodsVo){
        startPage();
        List<GoodsVo> list = goodsService.selectGoodsList(goodsVo);
        for (GoodsVo goods : list){
            // 借用天数
            BorrowVo borrowDay = borrowService.getBorrowDay(goods.getGoodsId());
            if (null != borrowDay){
                Long dayCount = DateUtils.getDayCount(borrowDay.getApplyTime(), borrowDay.getReturnTime());
                logger.info("借用天数====>>>{}",dayCount);
                goods.setBorrowDays(dayCount.intValue());
            }

            // 历史借用次数
            int borrowCount = borrowService.getBorrowCount(goods.getGoodsId());
            logger.info("历史借用次数====>>>{}",borrowCount);
            goods.setBorrowCount(borrowCount);
        }
        return getDataTable(list);
    }

    /**
     * 查询详情
     *
     */
    @GetMapping(value = "/getInfoById/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(goodsService.getGoodsInfo(id));
    }


    /**
     * 修改数据
     *
     * @param goodsVo 实体
     * @return 修改结果
     */
    @Log(title = "物品修改", businessType = BusinessType.UPDATE)
    @RequiresPermissions("business:goods:update")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody GoodsVo goodsVo) {
        return AjaxResult.success(goodsService.updateGoods(goodsVo));
    }

    /**
     * 新增数据
     *
     * @param goodsVo 实体
     * @return 新增结果
     */
    @Log(title = "新增物品", businessType = BusinessType.INSERT)
    @RequiresPermissions("business:goods:insert")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody GoodsVo goodsVo) {
        return AjaxResult.success(goodsService.insertClassify(goodsVo));
    }

    /**
     * 提醒
     *
     * @param goodsVo 实体
     * @return 新增结果
     */
    @Log(title = "新增提醒", businessType = BusinessType.INSERT)
    @RequiresPermissions("business:goods:remind")
    @PostMapping("/remind")
    public AjaxResult remind(@RequestBody GoodsVo goodsVo) {
        return AjaxResult.success(goodsService.insertRemind(goodsVo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 是否删除成功
     */
    @Log(title = "物品删除", businessType = BusinessType.DELETE)
    @RequiresPermissions("business:goods:delete")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long id){
        return AjaxResult.success(goodsService.deleteById(id));
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @Log(title = "文件上传", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult uploadPicture(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error("文件服务异常，请联系管理员");
            }
            if (R.FAIL == fileResult.getCode()) {
                return AjaxResult.error(fileResult.getMsg());
            }
            String url = fileResult.getData().getUrl();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgPath", url);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 导出
     */
    @Log(title = "物品导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("business:goods:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsVo goodsVo) throws IOException {
        List<GoodsVo> list = goodsService.export(goodsVo);
        ExcelUtil<GoodsVo> util = new ExcelUtil<GoodsVo>(GoodsVo.class);
        util.exportExcel(response, list, "数据");
    }
}
