package com.wr.govern.web.autonomy.decide;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.autonomy.decide.DecideService;
import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决controller
 */
@RestController
@RequestMapping("/decide")
public class DecideController extends BaseController {


    @Autowired
    private DecideService decideService;

    /**
     * 查询自治表决列表
     * @param decide
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
   // @RequiresPermissions("autonomy:decide:list")
    public TableDataInfo list(Decide decide) throws ParseException {
        startPage();
        //调用接口获取内容
        List<Decide> list = decideService.getList(decide);
        return getDataTable(list);
    }


    /**
     * 新增
     * @param decideVo
     * @return
     */
    @PostMapping
    @RequiresPermissions("autonomy:decide:add")
    @Log(title = "新增表决", businessType = BusinessType.INSERT)
    public AjaxResult addDecide(@Validated @RequestBody decideVo decideVo) {
        //通过vo类接收新增信息， 统一使用lease的接口， 随后在lease实现类中讲vo类中的字段一一放置到具体的实现类中
        Integer inte = decideService.addDecide(decideVo);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }


    /**
     * 修改
     * @param decideVo
     * @return
     */
    @RequiresPermissions("autonomy:decide:edit")
    @Log(title = "修改表决", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateDecide(@Validated @RequestBody decideVo decideVo){
        int i= decideService.updateDecide(decideVo);
        return toAjax(i);
    }


    /**
     *根据id获取当前详情
     * @param decideId
     * @return
     */
    //@RequiresPermissions("system:user:query")
    @GetMapping(value = { "/", "/{decideId}" })
    public AjaxResult getInfo(@PathVariable(value = "decideId", required = false) Long decideId) {
        AjaxResult ajax = AjaxResult.success();

        //不是空时，发送ajax
        if (StringUtils.isNotNull(decideId)){
            //1.表决表信息  Decide
          Decide decide = decideService.queryDecideByIdOne(decideId);
            //2.自联表中的表决发布至不同小区  List<decideRang>
            List<Long> drList = decideService.queryDecideRangById(decideId);
            //3.选项表信息  DecideItem
            List<DecideItem> decideItemList = decideService.queryDecideItemById(decideId);

            //讲请求sql得到的信息通过异步发送到vue页面进行显示
            ajax.put(AjaxResult.DATA_TAG, decide);
            ajax.put("commIds",drList );
            if(decideItemList.size()==2){
                String itemTitleOne=decideItemList.get(0).getItemTitle();
                String itemTitleTwo=decideItemList.get(1).getItemTitle();
                ajax.put("itemTitleOne",itemTitleOne);
                ajax.put("itemTitleTwo",itemTitleTwo);
            }
        }
        return ajax;
    }




    /**
     * 删除当前制约
     * @param decideId
     * @return
     */
    @RequiresPermissions("autonomy:decide:delete")
    @Log(title = "删除治约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{decideId}")
    public AjaxResult delDecide(@PathVariable Long[] decideId){
        return toAjax(decideService.delDecide(decideId));
    }



    /**
     * 根据治决表id去查询对应的选项
     * @param statuteId
     */
    @GetMapping("/getDecideItem/{statuteId}")
    public AjaxResult getDecideItemById(@PathVariable("statuteId") Long statuteId) {
        return AjaxResult.success(decideService.getDecideItemById(statuteId));
    }



    /**
     * 修改选项表中的信息  投票用的。
     * @param itemId
     * @return
     */
    @RequiresPermissions("autonomy:decide:edit")
    @Log(title = "修改选项表", businessType = BusinessType.UPDATE)
    @PutMapping("/updateDecideItem")
    public AjaxResult updateDecideItemById(@Validated @RequestBody Long itemId){
        int i= decideService.updateDecideItemById(itemId);
        return i > 0 ? AjaxResult.success("投票成功") : AjaxResult.error("投票失败，你已投票！");
    }




















}
