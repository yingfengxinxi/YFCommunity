package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.Rescue;
import com.wr.api.owner.entity.vo.serve.RescueVO;
import com.wr.api.owner.service.serve.RescueService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:01:30
 * @Desc: 服务-社区救助 控制层【业主端】
 */
@RestController
@RequestMapping("/v2/rescue")
public class RescueController extends BaseController {
    @Autowired
    private RescueService rescueService;

    /**
     * 获取社区救助列表
     *
     * @param communityId 小区id
     * @param releaseId 业主id
     * @param releaseType 发布人类型
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Long communityId, String releaseType, Long releaseId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return new TableDataInfo();
        }
        startPage();
        List<RescueVO> rescueAll = rescueService.getRescueAll(communityId, releaseType, releaseId);
        return getDataTable(rescueAll);
    }

    /**
     * 获取社区救助详情
     *
     * @param rescueId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/info")
    public AjaxResult getInfo(Long rescueId) {
        if (ObjectUtils.isEmpty(rescueId)) {
            return AjaxResult.error("参数不能为空");
        }
        RescueVO rescueById = rescueService.getRescueById(rescueId);
        return AjaxResult.success(rescueById);
    }

    /**
     * 添加救助
     *
     * @param rescue
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @ApiLog(title = "社区救助", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Rescue rescue) {
        Integer num = rescueService.addRescue(rescue);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
