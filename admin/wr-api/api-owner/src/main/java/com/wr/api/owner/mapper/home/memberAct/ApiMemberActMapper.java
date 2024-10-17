package com.wr.api.owner.mapper.home.memberAct;

import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.memberAct.ApMember;
import com.wr.api.owner.entity.home.memberAct.ApiMemberAct;
import com.wr.api.owner.entity.home.memberAct.ApiMemberJoin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 党建
 */
@Mapper
public interface ApiMemberActMapper {

    /**
     * 查列表
     * @param apiMemberAct
     * @return
     */
    List<ApiMemberAct> getList(ApiMemberAct apiMemberAct);

    /**
     * 查列表
     * @param apiMemberAct
     * @return
     */
    ApiMemberAct getInfo(ApiMemberAct apiMemberAct);

    /**
     * 新增
     * @param apiMemberJoin
     * @return
     */
    Integer add(ApiMemberJoin apiMemberJoin);



    /**
     * 查询当前用户是否已经报名
     * @param apiActivityOwner
     * @return
     */
    ApiMemberJoin getOwnerStatus(ApiMemberJoin apiActivityOwner);

    ApMember selectMemberById(Long accountId);

    void updateActivityStatus(ApiMemberAct apiMemberAct);
}
