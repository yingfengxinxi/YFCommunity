package com.wr.api.owner.service.home.memberAct;


import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.memberAct.ApiMemberAct;
import com.wr.api.owner.entity.home.memberAct.ApiMemberJoin;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 党建活动
 */
public interface ApiMemberActService {


    /**
     * 查列表
     * @param apiMemberAct
     * @return
     */
    List<ApiMemberAct> getList(ApiMemberAct apiMemberAct);

    /**
     * 查党建活动 详情
     * @param apiMemberAct
     * @return
     */
    ApiMemberAct getInfo(ApiMemberAct apiMemberAct);


    /**
     * 新增
     * @param apiActivityOwner
     * @return
     */
    Integer add(ApiMemberJoin apiActivityOwner);




    ApiMemberJoin getOwnerStatus(ApiMemberJoin apiActivityOwner);
}
