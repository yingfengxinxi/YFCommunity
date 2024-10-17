package com.wr.api.estate.service.impl.application.manage;

import com.wr.api.estate.entity.application.manage.GoodsOut;
import com.wr.api.estate.mapper.application.manage.GoodsOutMapper;
import com.wr.api.estate.service.application.manage.GoodsOutService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 14:11:16
 * @Desc: 业务层处理
 */
@Service
public class GoodsOutServiceImpl implements GoodsOutService {
    @Autowired
    private GoodsOutMapper goodsOutMapper;

    /**
     * 列表
     * @param goodsOut
     * @return
     */
    @Override
    public List<GoodsOut> getList(GoodsOut goodsOut) {
        return goodsOutMapper.getList(goodsOut);
    }

    /**
     * 修改出门状态
     * @param goodsOut
     * @return
     */
    @Override
    @Transactional
    public int updateGoodsOut(GoodsOut goodsOut) {
        return goodsOutMapper.updateGoodsOut(goodsOut);
    }

    /**
     * 获取详情
     * @param outId
     * @return
     */
    @Override
    public AjaxResult getById(Long outId) {
        AjaxResult ajaxResult = AjaxResult.success();
        GoodsOut goodsOut = goodsOutMapper.getById(outId);
        List<GoodsOut> imageList = goodsOutMapper.getImage(outId);
        ajaxResult.put("goodsOut",goodsOut);
        ajaxResult.put("imageList",imageList);
        return ajaxResult;
    }

    /**
     * 今日出门总数
     * @return
     */
    @Override
    public int goodsOutCount(Long communityId) {
        return goodsOutMapper.goodsOutCount(communityId);
    }

}
