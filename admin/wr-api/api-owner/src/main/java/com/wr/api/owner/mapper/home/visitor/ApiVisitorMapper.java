package com.wr.api.owner.mapper.home.visitor;


import com.wr.api.owner.entity.home.visitor.ApiAccount;
import com.wr.api.owner.entity.home.visitor.ApiVisitor;
import com.wr.api.owner.entity.home.visitor.vo.ApiLiaisonVo;
import com.wr.api.owner.entity.home.visitor.vo.OwnerHouseVo;

import java.util.List;

/**
 * 
 * @Author: lusu
 * @Date: 2022-11-16 20:27:09
 * @Desc: 访客记录
 */
public interface ApiVisitorMapper {



    List<ApiLiaisonVo> getList(ApiLiaisonVo apiLiaisonVo);


    ApiLiaisonVo getVisitorById(Long visitorId);

    List<OwnerHouseVo> ownerInfo(Long ownerId);



    void addLiaison(ApiLiaisonVo apiLiaisonVo);

    void addVisitor(ApiVisitor apiVisitor);

    ApiAccount visitorInfo(String loginName);

    int updateStatus(ApiLiaisonVo apiLiaisonVo);
}