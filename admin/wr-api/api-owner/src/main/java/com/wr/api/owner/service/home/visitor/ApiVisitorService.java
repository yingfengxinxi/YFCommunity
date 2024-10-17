package com.wr.api.owner.service.home.visitor;


import com.wr.api.owner.entity.home.visitor.vo.ApiLiaisonVo;
import com.wr.api.owner.entity.home.visitor.vo.OwnerHouseVo;
import com.wr.api.owner.entity.home.visitor.vo.RegisterVisitorVO;
import com.wr.common.core.web.domain.AjaxResult;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/15 11:46
 * @Desc: 我的访客
 */
public interface ApiVisitorService {


    /**
     * 获取社区救助列表
     *
     * @param apiLiaisonVo
     * @return
     * @throws ParseException
     */
    List<ApiLiaisonVo> getList(ApiLiaisonVo apiLiaisonVo);


    ApiLiaisonVo getVisitorById(Long visitorId);

    List<OwnerHouseVo> ownerInfo(Long ownerId);

    Integer add(ApiLiaisonVo apiLiaisonVo);

    int updateStatus(ApiLiaisonVo apiLiaisonVo);

    /**
     * 注册访客
     *
     * @param registerVisitor 注册参数
     * @author yx
     * @date 2022/12/1 10:23
     */
    AjaxResult register(RegisterVisitorVO registerVisitor);
}
