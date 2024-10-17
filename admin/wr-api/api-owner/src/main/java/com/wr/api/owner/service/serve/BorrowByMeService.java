package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 我的借用
 * @Author: lvzy
 * @Date: 2022-11-14 20:54:15
 * @Desc:我的借用
 */
public interface BorrowByMeService {

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
    AjaxResult detail(Long borrowId);

    /**
     * 查询二维码
     * @param borrowId
     * @return
     */
    AjaxResult qrCode(Long borrowId);
}
