package com.wr.estate.service.satisfaction.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.utils.Tools;
import com.wr.common.security.utils.SecurityUtils;

import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.satisfaction.SatisfactionSurveyBuildingMapper;
import com.wr.estate.mapper.satisfaction.SatisfactionSurveyMapper;
import com.wr.estate.mapper.satisfaction.SatisfactionSurveyOwnerMapper;
import com.wr.estate.mapper.satisfaction.SatisfactionSurveyProblemMapper;

import com.wr.estate.service.satisfaction.SatisfactionSurveyService;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.SatisfactionSurveyBuilding;
import com.wr.remote.domain.SatisfactionSurveyOwner;
import com.wr.remote.domain.SatisfactionSurveyProblem;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:07
 */
@Service
public class SatisfactionSurveyServiceImpl extends ServiceImpl<SatisfactionSurveyMapper, SatisfactionSurvey> implements SatisfactionSurveyService {

    @Autowired
    private SatisfactionSurveyBuildingMapper satisfactionSurveyBuildingMapper;

    @Autowired
    private SatisfactionSurveyProblemMapper satisfactionSurveyProblemMapper;

    @Autowired
    private SatisfactionSurveyOwnerMapper satisfactionSurveyOwnerMapper;

    @Autowired
    private RoomMapper roomMapper;


    @Override
    public List<SatisfactionSurvey> getList(SatisfactionSurvey satisfactionSurvey) {
        return baseMapper.getList(satisfactionSurvey);
    }

    @Override
    public void saveSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey) {
        satisfactionSurvey.setCreateBy(SecurityUtils.getUsername());
        satisfactionSurvey.setCreateTime(new Date());
        satisfactionSurvey.setDelFlag("0");
        super.save(satisfactionSurvey);


        extracted(satisfactionSurvey);
    }

    @Override
    public void updateSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey) {
        satisfactionSurvey.setUpdateBy(SecurityUtils.getUsername());
        satisfactionSurvey.setUpdateTime(new Date());
        super.updateById(satisfactionSurvey);
        extracted(satisfactionSurvey);
    }


    /**
     * 保存参与范围
     *
     * @param satisfactionSurvey
     */
    private void extracted(SatisfactionSurvey satisfactionSurvey) {
        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurvey.getId());
        List<SatisfactionSurveyProblem> satisfactionSurveyProblems = satisfactionSurveyProblemMapper.selectList(queryWrapper);
        satisfactionSurveyProblemMapper.delete(queryWrapper);
        for (SatisfactionSurveyProblem satisfactionSurveyProblem : satisfactionSurveyProblems) {
            LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SatisfactionSurveyBuilding::getSurveyProblemId, satisfactionSurveyProblem.getId());
            satisfactionSurveyBuildingMapper.delete(queryWrapper1);
        }

        List<SatisfactionSurveyProblem> satisfactionSurveyProblemList = satisfactionSurvey.getSatisfactionSurveyProblemList();
        if (CollectionUtils.isNotEmpty(satisfactionSurveyProblemList)) {
            for (SatisfactionSurveyProblem satisfactionSurveyProblem : satisfactionSurveyProblemList) {
                satisfactionSurveyProblem.setId(UUID.randomUUID().toString().replace("-", ""));
                satisfactionSurveyProblem.setSatisfactionSurveyId(satisfactionSurvey.getId());
                satisfactionSurveyProblem.setCreateBy(satisfactionSurvey.getCreateBy());
                satisfactionSurveyProblem.setCreateTime(satisfactionSurvey.getCreateTime());
                satisfactionSurveyProblem.setDelFlag("0");
                satisfactionSurveyProblemMapper.insert(satisfactionSurveyProblem);
                List<String> buildingList = Arrays.asList(satisfactionSurvey.getBuildingIds().split(","));
                if (CollectionUtils.isNotEmpty(buildingList)) {
                    for (String buildingId : buildingList) {
                        SatisfactionSurveyBuilding satisfactionSurveyBuilding = new SatisfactionSurveyBuilding();
                        satisfactionSurveyBuilding.setSurveyProblemId(satisfactionSurveyProblem.getId());
                        satisfactionSurveyBuilding.setSatisfactionSurveyId(satisfactionSurvey.getId());
                        satisfactionSurveyBuilding.setCommunityId(satisfactionSurvey.getCommunityId());
                        satisfactionSurveyBuilding.setBuildingId(Long.valueOf(buildingId));
                        satisfactionSurveyBuilding.setCreateBy(satisfactionSurvey.getCreateBy());
                        satisfactionSurveyBuilding.setCreateTime(new Date());
                        satisfactionSurveyBuilding.setDelFlag("0");
                        satisfactionSurveyBuildingMapper.insert(satisfactionSurveyBuilding);

                    }

                }
            }
        }


    }

    @Override
    public SatisfactionSurvey get(String id, String flag) {
        SatisfactionSurvey satisfactionSurvey = baseMapper.selectById(id);

        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurvey.getId());
        List<SatisfactionSurveyProblem> satisfactionSurveyProblems = satisfactionSurveyProblemMapper.selectList(queryWrapper);
        satisfactionSurvey.setSatisfactionSurveyProblemList(satisfactionSurveyProblems);
        if (flag.equals("1")) {
            List<SatisfactionSurveyOwnerVo> surveyOwnerList = this.getSatisfactionProblemCount(id);
            if (CollectionUtils.isNotEmpty(surveyOwnerList)) {
                satisfactionSurvey.setProblemList(surveyOwnerList);
            }
        }

        LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SatisfactionSurveyBuilding::getSatisfactionSurveyId, satisfactionSurvey.getId());
        List<SatisfactionSurveyBuilding> satisfactionSurveyOwners = satisfactionSurveyBuildingMapper.selectList(queryWrapper1);
        if (CollectionUtils.isNotEmpty(satisfactionSurveyOwners)) {
            List<Long> buildingIds = satisfactionSurveyOwners.stream().map(SatisfactionSurveyBuilding::getBuildingId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(buildingIds)) {
                List<Long> buildingList = buildingIds.stream().distinct().collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                for (Long buildingId : buildingList) {
                    sb.append(buildingId).append(",");
                }
                String buildings = sb.toString();
                if (CollectionUtils.isNotEmpty(buildingIds)) {
                    satisfactionSurvey.setBuildingIds(buildings.substring(0, buildings.length() - 1));
                }
            }
        }

        return satisfactionSurvey;
    }

    @Override
    public void delete(String id) {
        super.removeById(id);
        LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SatisfactionSurveyBuilding::getSatisfactionSurveyId, id);
        satisfactionSurveyBuildingMapper.delete(queryWrapper);

        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SatisfactionSurveyProblem::getSatisfactionSurveyId, id);
        satisfactionSurveyProblemMapper.delete(queryWrapper1);
    }

    /**
     * @param satisfactionSurveyId
     * @return
     */
    @Override
    public List<SatisfactionSurveyOwnerVo> getSatisfactionProblemCount(String satisfactionSurveyId) {
        List<SatisfactionSurveyOwnerVo> list = Lists.newArrayList();

        // LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper = new LambdaQueryWrapper<>();
        //  queryWrapper.eq(SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurveyId);
        //  List<SatisfactionSurveyProblem> problemList = satisfactionSurveyProblemMapper.selectList(queryWrapper);
        //应投票数量
//        int roomCount = 0;
//        for (SatisfactionSurveyProblem satisfactionSurveyProblem : problemList) {
//            LambdaQueryWrapper<SatisfactionSurveyOwner> queryWrapper3 = new LambdaQueryWrapper<>();
//            queryWrapper3.eq(SatisfactionSurveyOwner::getSurveyProblemId, satisfactionSurveyProblem.getId());
//            roomCount = roomCount + satisfactionSurveyOwnerMapper.selectList(queryWrapper3).size();
//        }
        //roomCount = 5;
        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurveyId);
        List<SatisfactionSurveyProblem> problemList = satisfactionSurveyProblemMapper.selectList(queryWrapper1);
        if (CollectionUtils.isNotEmpty(problemList)) {
            for (SatisfactionSurveyProblem satisfactionSurveyProblem : problemList) {

                LambdaQueryWrapper<SatisfactionSurveyOwner> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(SatisfactionSurveyOwner::getSurveyProblemId, satisfactionSurveyProblem.getId());
                Integer roomCount = satisfactionSurveyOwnerMapper.selectList(queryWrapper3).size();

                SatisfactionSurveyOwnerVo surveyOwnerVo = new SatisfactionSurveyOwnerVo();
                surveyOwnerVo.setProblemName(satisfactionSurveyProblem.getProblemName());
                for (int i = 0; i < 5; i++) {
                    String dividePercentage = "0.00%";
                    LambdaQueryWrapper<SatisfactionSurveyOwner> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.eq(SatisfactionSurveyOwner::getSurveyProblemId, satisfactionSurveyProblem.getId());
                    queryWrapper2.eq(SatisfactionSurveyOwner::getAnswer, String.valueOf(i));
                    Long count = satisfactionSurveyOwnerMapper.selectCount(queryWrapper2);
                    if (count != null && count >= 1 && roomCount >= 1) {
                        Double divide = (Double.valueOf(count) / Double.valueOf(roomCount)) * 100;
                        dividePercentage = Tools.DecimalFormat(divide) + "%";
                    }
                    if (i == 0) {
                        surveyOwnerVo.setAnswer0Percentage(dividePercentage);
                    }
                    if (i == 1) {
                        surveyOwnerVo.setAnswer1Percentage(dividePercentage);
                    }
                    if (i == 2) {
                        surveyOwnerVo.setAnswer2Percentage(dividePercentage);
                    }
                    if (i == 3) {
                        surveyOwnerVo.setAnswer3Percentage(dividePercentage);
                    }
                    if (i == 4) {
                        surveyOwnerVo.setAnswer4Percentage(dividePercentage);
                    }

                }
                list.add(surveyOwnerVo);
            }
        }
        return list;
    }
}
