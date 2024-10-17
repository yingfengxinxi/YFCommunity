package com.wr.api.owner.web.home.pay;

import com.wr.api.owner.entity.dto.LiveDTO;
import com.wr.api.owner.service.home.pay.LiveService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 09:36:14
 * @Desc: 物业费
 */
@RestController
@RequestMapping("/v2/live")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LiveController extends BaseController {

    private final LiveService liveService;

    /**
     * 待缴费用查询
     *
     * @param liveDTO 人员信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/fee")
    public AjaxResult getFee(LiveDTO liveDTO) {

        return liveService.getLiveFee( liveDTO );
    }

    /**
     * 物业费查询
     *
     * @param liveDTO 人员信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/property")
    public AjaxResult payLog(@RequestBody LiveDTO liveDTO) {

        return liveService.getPropertyLog( liveDTO );
    }

    /**
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Author SUNk
     * @Description 查询物业费用详情
     * @Date 14:41 2024/3/30
     * @Param [liveDTO]
     **/
    @GetMapping("/findDetilProperty")
    public AjaxResult findDetilProperty(@RequestParam Long logId) {

        return liveService.findDetilProperty( logId );
    }

    /**
     * 物业费列表查询
     *
     * @param liveDTO 人员信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/findListPropertyLog")
    public TableDataInfo findListPropertyLog(@RequestBody LiveDTO liveDTO) {
        PageUtils.startPage( liveDTO.getPageNum() == null ? 1 : liveDTO.getPageNum(), liveDTO.getPageSize()==null?10:liveDTO.getPageSize() );
        return getDataTable( liveService.findListPropertyLog( liveDTO ) );
    }
}
