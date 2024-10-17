package com.wr.govern.service.memberact;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.govern.party.MemberAct;
import com.wr.remote.govern.party.vo.MemberActVO;

import java.util.List;
import java.util.Map;


/**
 * @Author: SJiang
 * @Date: 2022-10-31 13:26:39
 * @Desc: 党员活动 业务
 */
public interface MemberActService {
    /**
     * 获取党员活动列表
     * @param memberAct
     * @return: java.util.List<com.wr.govern.entity.vo.MemberActVO>
     */
    List<MemberActVO> getMemberActAll(MemberActVO memberAct);

    /**
     * 查看详情
     * @param activityId
     * @return: com.wr.govern.entity.vo.MemberActVO
     */
    MemberActVO getMemberActById(Long activityId);

    /**
     * 查看名称是否唯一
     *
     * @param member
     * @return: com.wr.govern.entity.Member
     */
    String checkNameUnique(MemberAct member);

    /**
     * 添加活动
     * @param memberAct
     * @return: java.lang.Integer
     */
    Integer addMeberAct(MemberAct memberAct);

    /**
     * 删除
     * @param activityIds
     * @return: java.lang.Integer
     */
    Integer delMeberAct(Long [] activityIds);

    /**
    * @Author SUNk
    * @Description 查询党员活动参与人员详情
    * @Date 17:16 2024/2/29
    * @Param [memberAct]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    List<Map<String,Object>> findListMemberPeople(Map<String,Object> map);
}
