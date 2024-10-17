package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.dto.PeoLostDTO;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;
import com.wr.api.owner.service.serve.PeoLostService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:02:20
 * @Desc: 寻人启示
 */
@RestController
@RequestMapping("/v2/people")
public class PeoLostController extends BaseController {
    @Autowired
    private PeoLostService peoLostService;



    /**
     * 寻人启示
     * @param peoLostVo
     * @return
     */
    @GetMapping("/peopleList")
    @AuthRequired
    public TableDataInfo getList(PeoLostVo peoLostVo){
        if (ObjectUtils.isEmpty(peoLostVo)) {
            return new TableDataInfo();
        }
        startPage();
        List<PeoLostDTO> list = peoLostService.getPeopleLostList(peoLostVo);
        return getDataTable(list);
    }

    /**
     * 寻人启示详情
     * @param lostId
     * @return
     */
    @GetMapping("peo/{lostId}")
    @AuthRequired
    public AjaxResult getListInfo(@PathVariable Long lostId){
        List<PeoLostVo> list =peoLostService.getListInfo(lostId);
        return AjaxResult.success(list);
    }

    /**
     * 修改寻人发布的状态
     * @param peoLostVo
     * @return
     */
    @PostMapping("/update")
    @AuthRequired
    public AjaxResult updateByLost(@RequestBody PeoLostVo peoLostVo)
    {
        return AjaxResult.success(peoLostService.updateByLost(peoLostVo));
    }

    /**
     * 发布寻人启示
     * @param peoLostVo
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    public AjaxResult insertProLost(@RequestBody PeoLostVo peoLostVo){
        return  AjaxResult.success(peoLostService.insertProLost(peoLostVo)) ;
    }

    /**
     * 我得寻人启示
     * @param accountId
     * @return
     */
    @GetMapping("/peopleMyList/{accountId}")
    @AuthRequired
    public TableDataInfo getMyList(@PathVariable Long accountId){

        startPage();
        List<PeoLostVo> list = peoLostService.getPeopleMyLostList(accountId);
        return getDataTable(list);
    }
}
