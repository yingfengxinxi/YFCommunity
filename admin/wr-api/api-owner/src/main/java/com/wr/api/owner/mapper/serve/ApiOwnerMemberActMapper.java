package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.MemberAct;
import com.wr.api.owner.entity.vo.serve.MemberActVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党员活动
 * @Author: lvzy
 * @Date: 2022-11-15 15:08:55
 * @Desc:党员活动
 */
@Mapper
public interface ApiOwnerMemberActMapper {

    /**
     * delete by primary key
     * @param activityId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long activityId);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(MemberAct record);

    /**
     * select by primary key
     * @param activityId primary key
     * @return object by primary key
     */
    MemberAct selectByPrimaryKey(Long activityId);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(MemberAct record);

    /**
     * 分页
     * @param communityId
     * @return
     */
    List<MemberActVo> listPage(Long communityId);

    /**
     * @param memberAct   参数
     * @return
     */
    Integer signUp(MemberActVo memberAct);

    /**
     * 详情展示
     * @param memberAct
     * @return
     */
    MemberActVo detail(MemberActVo memberAct);
}