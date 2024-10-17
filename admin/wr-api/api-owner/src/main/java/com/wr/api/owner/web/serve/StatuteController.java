package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.StatuteVO;
import com.wr.api.owner.service.serve.StatuteService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 13:30:09
 * @Desc: 服务-自治规约 控制层
 */
@RestController
@RequestMapping("/v2/statute")
public class StatuteController extends BaseController {
    @Autowired
    private StatuteService statuteService;

    /**
     * 获取本小区自治规约数据
     * @param communityId 小区id
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Long communityId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return new TableDataInfo();
        }
        startPage();
        List<StatuteVO> statuteAll = statuteService.getStatuteAll(communityId);
        return getDataTable(statuteAll);
    }

    /**
     * 获取详情
     * @param statuteId 主键id
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/info")
    public AjaxResult info(Long statuteId) {
        if (ObjectUtils.isEmpty(statuteId)) {
            return AjaxResult.error("参数不能为空");
        }
        StatuteVO statuteById = statuteService.getStatuteById(statuteId);
        return AjaxResult.success(statuteById);
    }
}
