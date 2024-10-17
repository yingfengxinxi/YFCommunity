package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.dto.ItemLostDTO;
import com.wr.api.owner.entity.dto.MyProItemLostDTO;
import com.wr.api.owner.entity.vo.serve.ItemLostVo;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:43:57
 * @Desc:
 */
public interface ItemLostMapper {
    /**
     * 寻物品启示
     * @param itemLostVo
     * @return
     */
    List<ItemLostDTO> getItemLost(ItemLostVo itemLostVo);

    /**
     * 修改寻物发布的状态
     * @param itemLostVo
     * @return
     */
    int updateLostStatus(ItemLostVo itemLostVo);

    /**
     * 发布寻物启示
     * @param itemLostVo
     * @return
     */
    int addItemLost(ItemLostVo itemLostVo);

    /**
     * 寻物启示详情
     * @param lostId
     * @return
     */
    List<ItemLostVo> getInfoById(Long lostId);


    /**
     * 我得寻物启示
     * @param accountId
     * @return
     */
    List<MyProItemLostDTO> getMyItem(Long accountId);
}
