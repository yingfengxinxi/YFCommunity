package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.dto.FitmentDTO;
import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.common.core.web.domain.AjaxResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 14:22:18
 * @Desc:
 */
public interface FitmentMapper {
    /**
     * 查询装修列表
     * @param fitmentVo
     * @return
     */
    List<FitmentDTO> getFitmentList(FitmentVo fitmentVo);

    /**
     * 查询装修人员信息
     */
    List<FitmentVo> findFitmentEr(FitmentVo fitmentVo);

    /**
     * 查询详情
     * @param fitmentVo
     * @return
     */
    List<FitmentVo> FitmentInfo(FitmentVo fitmentVo);

//    /**
//     * 查询所在小区编号
//     * @param fitmentVo
//     * @return
//     */
//    Long findAddress(FitmentVo fitmentVo);

    /**
     * 添加装修
     * @param fitmentVo
     * @return
     */
    int insertFitment(FitmentVo fitmentVo);

    /**
     * 我要延期
     * @param fitmentVo
     * @return
     */
    int updateByDecorate(FitmentVo fitmentVo);

    /**
     * 装修状态修改
     *
     * @param fitmentVo
     * @return
     */
    int updateByState(FitmentVo fitmentVo);

    /**
     * 修改装修
     * @param fitmentVo
     * @return
     */
    int updateAllFit(FitmentVo fitmentVo);

    /**
     * 查询我得房产
     * @param ownerId
     * @param communityId
     * @return
     */
    List<MyRoomsVo> getMyAllRoom(@Param("ownerId") Long ownerId,@Param("communityId") Long communityId);

    /**
     * 获取物业手机号
     * @param communityId
     * @return
     */
    String getPhoneEs(Long communityId);

    /**
     * 获取结束时间
     * @return
     */
    List<FitmentDTO> getEndTime();

    /**
     * 修改状态
     * @param decorateId
     */
    void updateEndStatus(Long decorateId);
}
