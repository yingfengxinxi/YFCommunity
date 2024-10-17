package com.wr.estate.service.impl.customer.decorate;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.mapper.customer.decorate.LedgerMapper;
import com.wr.estate.service.customer.decorate.LedgerService;
import com.wr.remote.estate.customer.decorate.Worker;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 13:32:27
 * @Desc: 装修台账
 */
@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerMapper ledgerMapper;

    /**
     * 查询装修台账列表
     *
     * @param decorateVo
     * @return
     */
    public List<DecorateVo> getDecorateLedgerList(DecorateVo decorateVo) {
        return ledgerMapper.getDecorateLedgerList(decorateVo);
    }

    /**
     * 查询装修台账详情
     *
     * @param decorateId
     * @return
     */
    public AjaxResult getDecorateLedgerDetail(Long decorateId) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修台账详情
        DecorateVo decorateVo = ledgerMapper.getDecorateLedgerDetail(decorateId);
        //查询装修工信息
        List<Worker> workerList = ledgerMapper.getWorkerList(decorateId);

        ajaxResult.put(AjaxResult.DATA_TAG, decorateVo);
        ajaxResult.put("workerList", workerList);

        return ajaxResult;
    }
}
