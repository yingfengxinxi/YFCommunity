package com.wr.api.owner.service.estateapi.busQuestionnaire.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.estateapi.BusQuestionnaireScope;
import com.wr.api.owner.mapper.estateapi.busQuestionnaire.BusQuestionnaireScopeMapper;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireScopeService;
import com.wr.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/24 11:20
 */
@Service
public class BusQuestionnaireScopeImpl extends ServiceImpl<BusQuestionnaireScopeMapper, BusQuestionnaireScope> implements BusQuestionnaireScopeService {


    @Override
    public void add(BusQuestionnaireScope busQuestionnaireScope) {
        LambdaQueryWrapper<BusQuestionnaireScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusQuestionnaireScope::getQuestionnaireId, busQuestionnaireScope.getQuestionnaireId());
        baseMapper.delete(queryWrapper);
        List<Long> buildingIds = busQuestionnaireScope.getBuildingIds();
        if (CollectionUtils.isNotEmpty(buildingIds)) {
            buildingIds.stream().forEach(buildingId -> {
                if(buildingId!=null && buildingId!=0){
                    BusQuestionnaireScope qs = new BusQuestionnaireScope();
                    qs.setQuestionnaireId(busQuestionnaireScope.getQuestionnaireId());
                    qs.setCommunityId(busQuestionnaireScope.getCommunityId());
                    qs.setBuildingId(buildingId);
                    qs.setCreateTime(new Date());
                    qs.setDelFlag("0");
                    super.save(qs);
                }
            });
        }
    }
}
