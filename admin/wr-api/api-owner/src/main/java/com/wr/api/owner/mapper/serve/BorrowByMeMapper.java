package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.ApiBorrowVo;
import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;

import java.util.List;

/**
 * 我的借用
 * @Author: lvzy
 * @Date: 2022-11-14 20:55:47
 * @Desc:我的借用
 */
public interface BorrowByMeMapper {

    /**
     * 分页
     * @param accountId
     * @return
     */
    List<ApiGoodsVo> listPage(Long accountId);

    /**
     * 详情
     * @param borrowId
     * @return
     */
    ApiBorrowVo detail(Long borrowId);

    /**
     * 查找二维码
     * @param borrowId
     * @return
     */
    ApiBorrowVo qrCode(Long borrowId);
}
