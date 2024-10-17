package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.ApiPartyPay;
import com.wr.api.owner.entity.vo.serve.ApiMemberVo;
import com.wr.api.owner.entity.vo.serve.ApiPartyPayVo;

import java.util.List;

/**
 * 党费缴费记录
 * @Author: lvzy
 * @Date: 2022-11-17 11:51:30
 * @Desc:党费缴费记录
 */
public interface PartyPayMapper {

    /**
     * delete by primary key
     * @param logId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * insert record to table selective
     * @param apiPartyPay the record
     * @return insert count
     */
    int insertSelective(ApiPartyPay apiPartyPay);

    /**
     * select by primary key
     * @param logId primary key
     * @return object by primary key
     */
    ApiPartyPay selectByPrimaryKey(Long logId);

    /**
     * update record selective
     * @param apiPartyPay the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiPartyPay apiPartyPay);

    /**
     * 分页查询
     * @param memberId
     * @return
     */
    List<ApiPartyPayVo> listPage(Long memberId);

    /**
     * 查最新缴费
     * @param member
     * @return
     */
    ApiMemberVo newList(ApiMemberVo member);

    /**
     * 历史缴费详情
     * @param logId
     * @return
     */
    ApiPartyPayVo oldDetail(Long logId);

    /**
     * 最新缴费详情
     * @param memberId
     * @return
     */
    ApiMemberVo newDetail(Long memberId);

    /**
     * 根据时间查数量
     * @param member
     * @return
     */
    Integer selectDateByCount(ApiMemberVo member);
}