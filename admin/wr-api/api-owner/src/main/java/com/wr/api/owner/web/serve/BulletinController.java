package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.Clue;
import com.wr.api.owner.entity.vo.serve.BulletinVO;
import com.wr.api.owner.service.serve.BulletinService;
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
 * @Date: 2022-11-15 15:15:12
 * @Desc: 服务-警民互动 控制层
 */
@RestController
@RequestMapping("/v2/bulletin")
public class BulletinController extends BaseController {
    @Autowired
    private BulletinService bulletinService;

    /**
     * 获取人员通报数据
     *
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<BulletinVO> bulletinAll = bulletinService.getBulletinAll();
        return getDataTable(bulletinAll);
    }

    /**
     * 获取详情
     *
     * @param bulletinId 主键id
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/info")
    public AjaxResult info(Long bulletinId) {
        if (ObjectUtils.isEmpty(bulletinId)) {
            return AjaxResult.error("参数不能为空");
        }
        BulletinVO bulletinById = bulletinService.getBulletinById(bulletinId);
        return AjaxResult.success(bulletinById);
    }

    @AuthRequired
    @ApiLog(title = "警民互动", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Clue clue) {
        AjaxResult ajaxResult = bulletinService.addClue(clue);
        return ajaxResult;
    }
}
