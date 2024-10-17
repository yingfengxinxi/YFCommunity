package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.dto.PeoLostDTO;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:04:20
 * @Desc:
 */
public interface PeoLostMapper {
    /**
     * 寻人寻物启示
     * @param peoLostVo
     * @return
     */
    List<PeoLostDTO> getPeoLost(PeoLostVo peoLostVo);

    /**
     * 修改发布的状态
     * @param peoLostVo
     * @return
     */
    int updateByStatus(PeoLostVo peoLostVo);

    /**
     * 发布寻人启示
     * @param peoLostVo
     * @return
     */
    int addPeoLost(PeoLostVo peoLostVo);

    /**
     * 寻人启示详情
     * @param lostId
     * @return
     */
    List<PeoLostVo> getInfoById(Long lostId);

    /**
     * 我得寻人启示
     * @param accountId
     * @return
     */
    List<PeoLostVo> getMyById(Long accountId);
}
