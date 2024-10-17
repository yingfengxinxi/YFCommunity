package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.api.estate.service.application.serve.RescueService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:46:15
 * @Desc: 社会救助控制层【物业移动端】
 */
@RestController
@RequestMapping("/v2/rescue")
public class RescueController extends BaseController {

    @Autowired
    private RescueService rescueService;

    /**
     * 获取社会救助列表
     * @param rescueVO 查询对象
     * @return
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo selectRescueList(RescueVO rescueVO) {
        startPage();
        List<RescueVO> rescueVOList = rescueService.selectRescueList(rescueVO);
        return getDataTable(rescueVOList);
    }

    /**
     * 获取社会救助详情
     * @param rescueId 查询id
     * @return
     */
    @AuthRequired
    @GetMapping("/{rescueId}")
    public AjaxResult getRescueDetail(@PathVariable Long rescueId) {
        RescueVO rescueVO = rescueService.getRescueDetail(rescueId);
        return AjaxResult.success(rescueVO);
    }
}
