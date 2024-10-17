package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.DonationVO;
import com.wr.api.owner.service.serve.DonationService;
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
 * @Date: 2022-11-14 21:24:33
 * @Desc: 服务-捐赠公告 控制层
 */
@RestController
@RequestMapping("/v2/donation")
public class DonationController extends BaseController {
    @Autowired
    private DonationService donationService;

    /**
     * 获取捐赠公告列表
     * @param communityId
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Long communityId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return new TableDataInfo();
        }
        startPage();
        List<DonationVO> donationAll = donationService.getDonationAll(communityId);
        return getDataTable(donationAll);
    }

    @AuthRequired
    @GetMapping("/info")
    public AjaxResult info(Long donationId) {
        if (ObjectUtils.isEmpty(donationId)) {
            return AjaxResult.error("参数不能为空");
        }
        DonationVO donationById = donationService.getDonationById(donationId);
        return AjaxResult.success(donationById);
    }
}
