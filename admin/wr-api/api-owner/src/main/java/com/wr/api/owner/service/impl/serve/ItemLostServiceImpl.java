package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.bean.BeanUtil;
import com.wr.api.owner.entity.dto.ItemLostDTO;
import com.wr.api.owner.entity.dto.MyProItemLostDTO;
import com.wr.api.owner.entity.vo.serve.ItemLostVo;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;
import com.wr.api.owner.mapper.serve.ItemLostMapper;
import com.wr.api.owner.mapper.serve.PeoLostMapper;
import com.wr.api.owner.service.serve.ItemLostService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:43:49
 * @Desc:
 */
@Service
public class ItemLostServiceImpl implements ItemLostService {
    @Autowired
    private ItemLostMapper itemLostMapper;

    @Autowired
    private PeoLostMapper lostMapper;
    /**
     * 寻物品启示
     * @param itemLostVo
     * @return
     */
    @Override
    public List<ItemLostDTO> getItemLostList(ItemLostVo itemLostVo) {
        List<ItemLostDTO> itemLost = itemLostMapper.getItemLost(itemLostVo);
//        List<ItemLostDTO> itemLostDTOList = Objects.requireNonNull(BeanUtil.copyToList(itemLost,ItemLostDTO.class));
        return itemLost;
    }

    /**
     * 修改寻物发布的状态
     * @param itemLostVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult updateByLost(ItemLostVo itemLostVo) {
        int row =  itemLostMapper.updateLostStatus(itemLostVo);
        if (row>0){
            return AjaxResult.success("修改寻物状态成功");
        }
        return AjaxResult.error("修改寻物状态失败");
    }

    /**
     * 发布寻物启示
     * @param itemLostVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertItemLost(ItemLostVo itemLostVo) {
        //设置发布的状态为寻找中
        itemLostVo.setLostStatus("0");
        //发布日期
        itemLostVo.setPublishTime(new Date());
        int row =  itemLostMapper.addItemLost(itemLostVo);
        if (row>0){
            return AjaxResult.success("发布寻物启示成功");
        }
        return AjaxResult.error("发布寻物启示失败");

    }

    /**
     * 寻物启示详情
     * @param lostId
     * @return
     */
    @Override
    public List<ItemLostVo> getListInfo(Long lostId) {
        List<ItemLostVo> lostVos = itemLostMapper.getInfoById(lostId);
        return lostVos;
    }

    /**
     * 我得寻物启示
     * @param accountId
     * @return
     */
    @Override
    public List<MyProItemLostDTO> getItemMyLostList(Long accountId) {
        List<MyProItemLostDTO> itemLostMapperMyItem = itemLostMapper.getMyItem(accountId);
//        List<PeoLostVo> peoLostVos = lostMapper.getMyById(accountId);
        return itemLostMapperMyItem;
    }
}
