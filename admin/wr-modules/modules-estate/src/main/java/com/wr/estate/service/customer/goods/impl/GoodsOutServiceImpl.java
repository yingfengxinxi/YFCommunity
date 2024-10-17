package com.wr.estate.service.customer.goods.impl;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.customer.goods.ImageMapper;
import com.wr.estate.mapper.customer.goods.GoodsOutMapper;
import com.wr.estate.service.customer.goods.GoodsOutService;
import com.wr.remote.estate.Image;
import com.wr.remote.estate.customer.goods.vo.GoodsOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 14:03:40
 * @Desc:
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class GoodsOutServiceImpl implements GoodsOutService {

    @Autowired
    private GoodsOutMapper goodsOutMapper;
    @Autowired
    private ImageMapper imageMapper;

    /**
     * 物品出门列表
     *
     * @param goodsOutVo
     * @return
     */
    @Override
    public List<GoodsOutVo> selectGoodsOutList(GoodsOutVo goodsOutVo) {
        return goodsOutMapper.selectGoodsOutList(goodsOutVo);
    }

    /**
     * 详情
     *
     * @param outId
     * @return
     */
    @Override
    public GoodsOutVo getInfo(Long outId) {
        GoodsOutVo goodsOutVo = goodsOutMapper.getInfo(outId);
        List<Image> imageList = imageMapper.getImageList("0", goodsOutVo.getOutId());
        goodsOutVo.setImages(imageList);
        return goodsOutVo;
    }

    /**
     * 出门
     *
     * @param goodsOutVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int updateOutStatus(GoodsOutVo goodsOutVo) {
        Long userId = SecurityUtils.getUserId();
        String staffId = goodsOutMapper.getStaffId(userId);
        if (null != staffId){
            goodsOutVo.setStaffId(Long.valueOf(staffId));
        }else {
            goodsOutVo.setStaffId(0L);
        }
        return goodsOutMapper.updateOutStatus(goodsOutVo);
    }

}
