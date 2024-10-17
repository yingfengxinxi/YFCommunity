package com.wr.api.owner.web.home.visitor;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.owner.entity.home.BusInterview;
import com.wr.api.owner.service.home.visitor.BusInterviewService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/businterview")
@Api(tags = "新访客档案表")
public class BusInterviewController extends BaseController {

    @Resource
    private BusInterviewService busInterviewService;

    /**
     * 新增访客档案
     *
     * @param busInterview 访客档案
     * @return
     */
    @PostMapping("/saveBusInterview")
    public AjaxResult saveBusInterview(@RequestBody BusInterview busInterview) {
        boolean save = busInterviewService.save( busInterview );
        return AjaxResult.success( save );
    }

    /**
     * 访客分页查询
     *
     * @param busInterview 访客档案
     * @return
     */
    @GetMapping("/findListBusInterview")
    public TableDataInfo findListBusInterview(BusInterview busInterview) {
        if(busInterview == null || busInterview.getAccountId() == null){
            throw new ServiceException("获取业主ID失败请稍后重试！");
        }
        startPage();
        LambdaUpdateWrapper<BusInterview> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq( BusInterview::getAccountId,busInterview.getAccountId())
                .eq( busInterview.getCommunityId() != null,BusInterview::getCommunityId,busInterview.getCommunityId() )
                .orderByDesc( BusInterview::getCreateTime );
        List<BusInterview> list = busInterviewService.list( wrapper );

        return getDataTable(list);
    }


}
