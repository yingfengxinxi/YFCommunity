package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.PeoLostDTO;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:04:12
 * @Desc:
 */
public interface PeoLostService {

    /**
     * 寻人寻物启示
     * @param peoLostVo
     * @return
     */
    List<PeoLostDTO> getPeopleLostList(PeoLostVo peoLostVo);

    /**
     * 修改发布的状态
     * @param peoLostVo
     * @return
     */
    AjaxResult updateByLost(PeoLostVo peoLostVo);

    /**
     * 发布寻人启示
     * @param peoLostVo
     * @return
     */
    AjaxResult insertProLost(PeoLostVo peoLostVo);

    /**
     * 寻人启示详情
     * @param lostId
     * @return
     */
    List<PeoLostVo> getListInfo(Long lostId);


    /**
     * 我得寻人启示
     * @param accountId
     * @return
     */
    List<PeoLostVo> getPeopleMyLostList(Long accountId);
}
