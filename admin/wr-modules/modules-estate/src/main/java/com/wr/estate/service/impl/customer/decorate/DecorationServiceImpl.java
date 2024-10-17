package com.wr.estate.service.impl.customer.decorate;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.domain.R;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.mapper.customer.decorate.DecorationMapper;
import com.wr.estate.service.customer.decorate.DecorationService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.estate.customer.decorate.*;
import com.wr.remote.estate.customer.vo.decorate.DecorateInfoVo;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import com.wr.remote.system.RemoteFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:35:51
 * @Desc: 装修列表
 */
@Service
public class DecorationServiceImpl extends ServiceImpl<DecorationMapper, BusDecorate> implements DecorationService {
    private static final Logger log = LoggerFactory.getLogger(DecorationServiceImpl.class);


    /**
     * 查询装修列表
     *
     * @param decorateVo
     * @return
     */
    @Override
    public List<DecorateVo> getDecorateVoList(DecorateVo decorateVo) {
        return baseMapper.getDecorateVoList(decorateVo);
    }

    /**
     * 查询装修详情
     *
     * @param id
     * @return
     */
    @Override
    public AjaxResult getDecorationDetail(String id) {
        AjaxResult ajaxResult = AjaxResult.success();

        //查询装修详情
        DecorateInfoVo decorateVo = baseMapper.getDecorationDetail(id);
        String warrant = decorateVo.getWarrant();
        if (StringUtils.isNotEmpty(decorateVo.getProjectItem())) {
            if (StringUtils.isNotEmpty(decorateVo.getRemarks())) {
                decorateVo.setProjectItem(decorateVo.getProjectItem() + "," + decorateVo.getRemarks());
            }
        }
        if (StringUtils.isNotEmpty(warrant)) {
            decorateVo.setWarrants(Arrays.asList(warrant.split(",")));
        }
        String designScheme = decorateVo.getDesignScheme();
        if (StringUtils.isNotEmpty(designScheme)) {
            decorateVo.setDesignSchemes(Arrays.asList(designScheme.split(",")));
        }
        String decorationContract = decorateVo.getDecorationContract();
        if (StringUtils.isNotEmpty(decorationContract)) {
            decorateVo.setDecorationContracts(Arrays.asList(decorationContract.split(",")));
        }
        String other = decorateVo.getOther();
        if (StringUtils.isNotEmpty(other)) {
            decorateVo.setOthers(Arrays.asList(other.split(",")));
        }
        ajaxResult.put(AjaxResult.DATA_TAG, decorateVo);

        //查询装修工信息
        List<DecorateViolation> decorateViolationList = baseMapper.getViolationList(id);
        if (CollectionUtils.isNotEmpty(decorateViolationList)) {
            for (DecorateViolation decorateViolation : decorateViolationList) {
                String image = decorateViolation.getImage();
                if (StringUtils.isNotEmpty(image)) {
                    decorateViolation.setImages(Arrays.asList(image.split(",")));
                }
            }
        }

        ajaxResult.put("violationList", decorateViolationList);

        return ajaxResult;
    }
}
