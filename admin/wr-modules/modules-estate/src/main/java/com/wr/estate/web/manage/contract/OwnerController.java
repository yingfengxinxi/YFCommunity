package com.wr.estate.web.manage.contract;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.manage.contract.OwnerService;
import com.wr.remote.estate.manage.vo.contract.OwnerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 业主信息
 */
@RestController
@RequestMapping("/ownerLeaseInfo")
public class OwnerController extends BaseController {

    @Autowired
    private OwnerService ownerService;

    /**
     * 获取owner列表
     * @param ownerVo
     * @return
     * @throws ParseException
     */
    @GetMapping("/ownerInfo")
    public TableDataInfo list(OwnerVo ownerVo) throws ParseException {
        // startPage();
        //调用接口获取内容
       List<OwnerVo> OwnerList= ownerService.getOwnerVo(ownerVo);
        return getDataTable(OwnerList);
    }


}
