package com.wr.estate.web.index;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.OrderNumVO;
import com.wr.estate.service.impl.index.IndexService;
import com.wr.remote.domain.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author DIANWEI
 * 2022-12-01
 * 首页 controller
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private IndexService indexService;

    /**
     * 审核数量 房产审核 车辆审核 业主审核 租客审核
     *
     * @return  审核数量 房产审核 车辆审核 业主审核 租客审核
     */
    @GetMapping("/getAuditNum")
    public AjaxResult getAuditNum() {

        Map<String, Object> map = indexService.getAuditNum(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return AjaxResult.success(map);
    }

    /**
     * 小区列表
     */
    @GetMapping("/getCommunityIdList")
    public AjaxResult getCommunityIdList() {
        List<Community> communityList = indexService.getCommunityIdList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return AjaxResult.success(communityList);
    }

    /**
     * 每日人行/车行
     */
    @GetMapping("/getLogList")
    public AjaxResult getLogList(Long communityId) {
        Map<String, List<BigDecimal>> map = indexService.getLogList(communityId);
        return AjaxResult.success(map);
    }

    /**
     * 查询能耗数据
     * @return 查询能耗数据
     */
    @GetMapping("/getEnergyData")
    public AjaxResult getEnergyData(Long energyType, Long communityId) {
        Map<String, List<BigDecimal>> map = indexService.getEnergyData(energyType, communityId);
        return AjaxResult.success(map);
    }

    /**
     *  查询房屋状态
     * @param communityId 社区id
     * @return 查询房屋状态
     */
    @GetMapping("/getRoomStatusData")
    public AjaxResult getRoomStatusData(Long communityId){
        Map<String, Object> map = indexService.getRoomStatusData(communityId);
        return AjaxResult.success(map);
    }
    /**
     *  获取工单数量
     * @return 获取工单数量
     */
    @GetMapping("/getOrderList")
    public AjaxResult getOrderList(){
        List<OrderNumVO> map = indexService.getOrderList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return AjaxResult.success(map);
    }
    /**
     *  查询社区公告
     * @return 查询社区公告
     */
    @GetMapping("/getNoticeList")
    public AjaxResult getNoticeList(){
        List<Map<String, Object>> map = indexService.getNoticeList(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return AjaxResult.success(map);
    }

    /**
     *  查询基金投票金额
     * @return 查询基金投票金额
     */
    @GetMapping("/getFoundData")
    public AjaxResult getFoundData(Long communityId){

        return indexService.getFoundData(communityId);
    }
}
