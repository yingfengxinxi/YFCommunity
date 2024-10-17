package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.bean.BeanUtil;
import com.wr.api.owner.entity.dto.PeoLostDTO;
import com.wr.api.owner.entity.vo.serve.PeoLostVo;
import com.wr.api.owner.mapper.serve.PeoLostMapper;
import com.wr.api.owner.service.serve.PeoLostService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 10:04:04
 * @Desc:
 */
@Service
public class PeoLostServiceImpl implements PeoLostService {
    @Autowired
    private PeoLostMapper peoLostMapper;

    /**
     * 寻人启示
     * @param peoLostVo
     * @return
     */
    @Override
    public List<PeoLostDTO> getPeopleLostList(PeoLostVo peoLostVo) {

        List<PeoLostDTO> peoLost = peoLostMapper.getPeoLost(peoLostVo);
//        List<PeoLostDTO> peoLostDTOList = Objects.requireNonNull(BeanUtil.copyToList(peoLost,PeoLostDTO.class));
        return peoLost;
    }

    /**
     * 修改寻人发布的状态
     * @param peoLostVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult updateByLost(PeoLostVo peoLostVo) {
        int row = peoLostMapper.updateByStatus(peoLostVo);
        if (row>0){
            return AjaxResult.success("修改寻人状态成功");
        }
        return AjaxResult.error("修改寻人状态失败");
    }

    /**
     * 发布寻人启示
     * @param peoLostVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertProLost(PeoLostVo peoLostVo) {
        //设置发布的状态为寻找中
        peoLostVo.setLostStatus("0");
        //发布日期
        peoLostVo.setPublishTime(new Date());
        int row =  peoLostMapper.addPeoLost(peoLostVo);
        if (row>0){
            return AjaxResult.success("添加寻人启示成功");
        }
        return AjaxResult.error("添加寻人启示失败");
    }

    /**
     * 寻人启示详情
     * @param lostId
     * @return
     */
    @Override
    public List<PeoLostVo> getListInfo(Long lostId) {
       List<PeoLostVo> list = peoLostMapper.getInfoById(lostId);
        return list;
    }

    /**
     * 我得寻人启示
     * @param accountId
     * @return
     */
    @Override
    public List<PeoLostVo> getPeopleMyLostList(Long accountId) {
        List<PeoLostVo> list = peoLostMapper.getMyById(accountId);
        return list;
    }

}
