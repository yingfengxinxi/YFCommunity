package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.MajordomoVO;
import com.wr.api.owner.service.serve.MajordomoService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:51:19
 * @Desc: 服务-专属管家 控制层
 */
@RestController
@RequestMapping("/v2/majordomo")
public class MajordomoController extends BaseController {
    @Autowired
    private MajordomoService majordomoService;

    /**
     * 获取楼栋专属管家
     * @param communityId 小区id
     * @param buildingId 楼栋id
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/info")
    public AjaxResult getInfo(Long communityId, Long buildingId) {
        if (ObjectUtils.isEmpty(communityId) || ObjectUtils.isEmpty(buildingId)){
            return AjaxResult.error("参数不能为空");
        }
        MajordomoVO majordomo = majordomoService.getMajordomo(communityId, buildingId);
        return AjaxResult.success(majordomo);
    }
}
