package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.FitmentDTO;
import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 14:21:59
 * @Desc:
 */
public interface FitmentService {
    /**
     * 查询装修列表
     * @param fitmentVo
     * @return
     */
    List<FitmentDTO> getFitment(FitmentVo fitmentVo);

    /**
     * 新增装修
     * @param fitmentVo
     * @return
     */
    AjaxResult insertFitment(FitmentVo fitmentVo);

    /**
     * 查询装修人员信息
     */
    List<FitmentVo> getFitmentEr(FitmentVo fitmentVo);

    /**
     * 查询装修详情
     * @param fitmentVo
     * @return
     */
    List<FitmentVo> getFitmentInfo(FitmentVo fitmentVo);

    /**
     * 我要延期
     * @param fitmentVo
     * @return
     */
    int updatePostpone(FitmentVo fitmentVo);

    /**
     * 装修状态修改
     * @param fitmentVo
     * @return
     */
    int updateState(FitmentVo fitmentVo);

    /**
     * 人员申请
     * @param fitmentVo
     * @return
     */
    AjaxResult insertHuman(FitmentVo fitmentVo);

    /**
     * 修改装修
     * @param fitmentVo
     * @return
     */
    int updateAll(FitmentVo fitmentVo);

    /**
     * 查询我得房产
     * @param ownerId
     * @return
     */
    List<MyRoomsVo> getMyRoomS(Long ownerId,Long communityId);

    /**
     * 获取业主手机号
     * @param communityId
     * @return
     */
    String getEstatePhone(Long communityId);
}
