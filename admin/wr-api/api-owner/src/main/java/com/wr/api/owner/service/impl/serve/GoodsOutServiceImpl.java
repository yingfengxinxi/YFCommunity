package com.wr.api.owner.service.impl.serve;


import com.wr.api.owner.entity.vo.serve.ApiGoodsOutVo;
import com.wr.api.owner.mapper.serve.GoodsOutMapper;
import com.wr.api.owner.mapper.serve.ImageMapper;
import com.wr.api.owner.service.serve.GoodsOutService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.Image;
import com.wr.remote.estate.manage.Passage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 物品出门
 * @Author: lvzy
 * @Date: 2022-11-15 10:09:14
 * @Desc:物品出门
 */
@Service
@AllArgsConstructor
public class GoodsOutServiceImpl implements GoodsOutService {

    private final GoodsOutMapper goodsOutMapper;

    private final ImageMapper imageMapper;

    @Override
    public List<Passage> passageList(Long communityId) {
        return goodsOutMapper.passageList(communityId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult passageAdd(ApiGoodsOutVo goodsOutVo) {
        // 1.新增物品出门表
        goodsOutMapper.insertSelective(goodsOutVo);
        // 2.新增图片表
        if (goodsOutVo.getOutId() != null) {
            Image image = new Image();
            image.setTableId(goodsOutVo.getOutId());
            for (Image images : goodsOutVo.getImageArr()) {
                image.setImageUrl(images.getImageUrl());
                imageMapper.insertSelective(image);
            }
        }
        return AjaxResult.success();
    }

    @Override
    public List<ApiGoodsOutVo> listPage(Long accountId) {
        return goodsOutMapper.listPage(accountId);
    }

    @Override
    public ApiGoodsOutVo detail(Long outId) {
        ApiGoodsOutVo detail = goodsOutMapper.detail(outId);
        if (detail.getOutId() != null) {
            List<Image> stringList = goodsOutMapper.selectImageUrl(detail.getOutId());
            detail.setImageArr(stringList);
        }
        return detail;
    }
}
