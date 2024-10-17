package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.ItemLostDTO;
import com.wr.api.owner.entity.dto.MyProItemLostDTO;
import com.wr.api.owner.entity.vo.serve.ItemLostVo;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:43:42
 * @Desc:
 */
public interface ItemLostService {
    /**
     * 寻物品启示
     * @param itemLostVo
     * @return
     */
    List<ItemLostDTO> getItemLostList(ItemLostVo itemLostVo);

    /**
     * 修改寻物发布的状态
     * @param itemLostVo
     * @return
     */
    AjaxResult updateByLost(ItemLostVo itemLostVo);

    /**
     * 发布寻物启示
     * @param itemLostVo
     * @return
     */
    AjaxResult insertItemLost(ItemLostVo itemLostVo);

    /**
     * 寻物启示详情
     * @param lostId
     * @return
     */
    List<ItemLostVo> getListInfo(Long lostId);

    /**
     * 我得寻物启示
     * @param accountId
     * @return
     */
    List<MyProItemLostDTO> getItemMyLostList(Long accountId);
}
