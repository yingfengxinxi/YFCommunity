package com.wr.estate.service.customer.goods.impl;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.customer.goods.BorrowMapper;
import com.wr.estate.mapper.customer.goods.GoodsMapper;
import com.wr.estate.service.customer.goods.GoodsService;
import com.wr.remote.estate.customer.goods.vo.BorrowVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 19:41:37
 * @Desc: 物品管理服务实现类
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private BorrowMapper borrowMapper;

    /**
     * 物品列表
     *
     * @param goodsVo
     * @return
     */
    @Override
    public List<GoodsVo> selectGoodsList(GoodsVo goodsVo) {
        return goodsMapper.selectGoodsList(goodsVo);
    }

    /**
     * 物品列表
     *
     * @param goodsVo
     * @return
     */
    @Override
    public List<GoodsVo> export(GoodsVo goodsVo) {
        return goodsMapper.export(goodsVo);
    }

    /**
     * 物品详情
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo getGoodsInfo(Long goodsId) {
        GoodsVo goodsVoInfo = goodsMapper.getGoodsInfo(goodsId,null);
        if (null != goodsVoInfo.getAccountId()){
            GoodsVo ownerInfo = goodsMapper.getOwnerInfo(goodsVoInfo.getAccountId());
            goodsVoInfo.setOwnerName(ownerInfo.getOwnerName());
            goodsVoInfo.setOwnerPhone(ownerInfo.getOwnerPhone());
            goodsVoInfo.setCommunityName(ownerInfo.getCommunityName());
            goodsVoInfo.setBuildingName(ownerInfo.getBuildingName());
            goodsVoInfo.setUnitName(ownerInfo.getUnitName());
            goodsVoInfo.setRoomName(ownerInfo.getRoomName());
            int borrowCount = borrowMapper.getBorrowCount(goodsVoInfo.getGoodsId(), goodsVoInfo.getAccountId());
            goodsVoInfo.setBorrowCount(borrowCount);
        }

        List<BorrowVo> borrowVoHistory = goodsMapper.selectBorrowHistory(goodsVoInfo.getGoodsId());
        goodsVoInfo.setBorrows(borrowVoHistory);
        return goodsVoInfo;
    }

    /**
     * 修改
     *
     * @param goodsVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int updateGoods(GoodsVo goodsVo) {
        goodsVo.setUpdateBy(SecurityUtils.getUsername());
        goodsMapper.updateGoods(goodsVo);
        int exsit = goodsMapper.getIsExsit(goodsVo);
        if (exsit > 1){
            throw new RuntimeException("物品编号：“" + goodsVo.getGoodsNo() + "”已存在");
        }
        return 1;
    }

    /**
     * 新增
     *
     * @param goodsVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int insertClassify(GoodsVo goodsVo) {
        goodsVo.setCreateBy(SecurityUtils.getUsername());

        int exsit = goodsMapper.getIsExsit(goodsVo);
        if (exsit > 0){
            throw new RuntimeException("物品编号：“" + goodsVo.getGoodsNo() + "”已存在");
        }

        return goodsMapper.insertClassify(goodsVo);
    }

    /**
     * 删除
     *
     * @param goodsId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteById(Long goodsId) {
        return goodsMapper.deleteById(goodsId);
    }

    /**
     * 新增提醒
     *
     * @param goodsVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int insertRemind(GoodsVo goodsVo) {
        goodsVo.setCreateBy(SecurityUtils.getUsername());
        return goodsMapper.insertRemind(goodsVo);
    }
}
