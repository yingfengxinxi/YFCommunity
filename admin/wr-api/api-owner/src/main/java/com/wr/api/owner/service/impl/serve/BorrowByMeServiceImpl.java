package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.api.owner.mapper.serve.BorrowByMeMapper;
import com.wr.api.owner.service.serve.BorrowByMeService;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的借用
 * @Author: lvzy
 * @Date: 2022-11-14 20:55:02
 * @Desc:我的借用
 */
@Service
@AllArgsConstructor
public class BorrowByMeServiceImpl implements BorrowByMeService {

    private final BorrowByMeMapper borrowByMeMapper;

    @Override
    public List<ApiGoodsVo> listPage(Long accountId) {
        return borrowByMeMapper.listPage(accountId);
    }

    @Override
    public AjaxResult detail(Long borrowId) {
        return AjaxResult.success(borrowByMeMapper.detail(borrowId));
    }

    @Override
    public AjaxResult qrCode(Long borrowId) {
        return AjaxResult.success(borrowByMeMapper.qrCode(borrowId));
    }
}
