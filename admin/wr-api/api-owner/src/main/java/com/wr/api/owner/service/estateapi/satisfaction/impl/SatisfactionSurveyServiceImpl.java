package com.wr.api.owner.service.estateapi.satisfaction.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.satisfaction.SatisfactionSurveyBuildingMapper;
import com.wr.api.owner.mapper.estateapi.satisfaction.SatisfactionSurveyMapper;
import com.wr.api.owner.mapper.estateapi.satisfaction.SatisfactionSurveyOwnerMapper;
import com.wr.api.owner.mapper.estateapi.satisfaction.SatisfactionSurveyProblemMapper;
import com.wr.api.owner.service.estateapi.satisfaction.SatisfactionSurveyService;
import com.wr.common.core.utils.Tools;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.SatisfactionSurveyBuilding;
import com.wr.remote.domain.SatisfactionSurveyOwner;
import com.wr.remote.domain.SatisfactionSurveyProblem;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:07
 */
@Service
public class SatisfactionSurveyServiceImpl extends ServiceImpl<SatisfactionSurveyMapper, SatisfactionSurvey> implements SatisfactionSurveyService {

    @Resource
    private SatisfactionSurveyBuildingMapper satisfactionSurveyBuildingMapper;

    @Resource
    private SatisfactionSurveyProblemMapper satisfactionSurveyProblemMapper;

    @Resource
    private SatisfactionSurveyOwnerMapper satisfactionSurveyOwnerMapper;


    @Override
    public List<SatisfactionSurvey> getList(SatisfactionSurvey satisfactionSurvey) {
        return baseMapper.getList( satisfactionSurvey );
    }

    @Override
    public void saveSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey) {
        satisfactionSurvey.setCreateBy( SecurityUtils.getUsername() );
        satisfactionSurvey.setCreateTime( new Date() );
        satisfactionSurvey.setDelFlag( "0" );
        super.save( satisfactionSurvey );


        extracted( satisfactionSurvey );
    }

    @Override
    public void updateSatisfactionSurvey(SatisfactionSurvey satisfactionSurvey) {
        satisfactionSurvey.setUpdateBy( SecurityUtils.getUsername() );
        satisfactionSurvey.setUpdateTime( new Date() );
        super.updateById( satisfactionSurvey );
        extracted( satisfactionSurvey );
    }


    /**
     * 保存参与范围
     *
     * @param satisfactionSurvey
     */
    private void extracted(SatisfactionSurvey satisfactionSurvey) {
        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurvey.getId() );
        List<SatisfactionSurveyProblem> satisfactionSurveyProblems = satisfactionSurveyProblemMapper.selectList( queryWrapper );
        satisfactionSurveyProblemMapper.delete( queryWrapper );
        for (SatisfactionSurveyProblem satisfactionSurveyProblem : satisfactionSurveyProblems) {
            LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq( SatisfactionSurveyBuilding::getSurveyProblemId, satisfactionSurveyProblem.getId() );
            satisfactionSurveyBuildingMapper.delete( queryWrapper1 );
        }

        List<SatisfactionSurveyProblem> satisfactionSurveyProblemList = satisfactionSurvey.getSatisfactionSurveyProblemList();
        if (CollectionUtils.isNotEmpty( satisfactionSurveyProblemList )) {
            for (SatisfactionSurveyProblem satisfactionSurveyProblem : satisfactionSurveyProblemList) {
                satisfactionSurveyProblem.setId( UUID.randomUUID().toString().replace( "-", "" ) );
                satisfactionSurveyProblem.setSatisfactionSurveyId( satisfactionSurvey.getId() );
                satisfactionSurveyProblem.setCreateBy( satisfactionSurvey.getCreateBy() );
                satisfactionSurveyProblem.setCreateTime( satisfactionSurvey.getCreateTime() );
                satisfactionSurveyProblem.setDelFlag( "0" );
                satisfactionSurveyProblemMapper.insert( satisfactionSurveyProblem );
                List<String> buildingList = Arrays.asList( satisfactionSurvey.getBuildingIds().split( "," ) );
                if (CollectionUtils.isNotEmpty( buildingList )) {
                    for (String buildingId : buildingList) {
                        SatisfactionSurveyBuilding satisfactionSurveyBuilding = new SatisfactionSurveyBuilding();
                        satisfactionSurveyBuilding.setSurveyProblemId( satisfactionSurveyProblem.getId() );
                        satisfactionSurveyBuilding.setSatisfactionSurveyId( satisfactionSurvey.getId() );
                        satisfactionSurveyBuilding.setCommunityId( satisfactionSurvey.getCommunityId() );
                        satisfactionSurveyBuilding.setBuildingId( Long.valueOf( buildingId ) );
                        satisfactionSurveyBuilding.setCreateBy( satisfactionSurvey.getCreateBy() );
                        satisfactionSurveyBuilding.setCreateTime( new Date() );
                        satisfactionSurveyBuilding.setDelFlag( "0" );
                        satisfactionSurveyBuildingMapper.insert( satisfactionSurveyBuilding );

                    }

                }
            }
        }


    }

    @Override
    public SatisfactionSurvey get(String id, String flag) {
        SatisfactionSurvey satisfactionSurvey = baseMapper.selectById( id );

        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurvey.getId() );
        List<SatisfactionSurveyProblem> satisfactionSurveyProblems = satisfactionSurveyProblemMapper.selectList( queryWrapper );
        satisfactionSurvey.setSatisfactionSurveyProblemList( satisfactionSurveyProblems );
        if (flag.equals( "1" )) {
            List<SatisfactionSurveyOwnerVo> surveyOwnerList = this.getSatisfactionProblemCount( id );
            if (CollectionUtils.isNotEmpty( surveyOwnerList )) {
                satisfactionSurvey.setProblemList( surveyOwnerList );
            }
        }

        LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq( SatisfactionSurveyBuilding::getSatisfactionSurveyId, satisfactionSurvey.getId() );
        List<SatisfactionSurveyBuilding> satisfactionSurveyOwners = satisfactionSurveyBuildingMapper.selectList( queryWrapper1 );
        if (CollectionUtils.isNotEmpty( satisfactionSurveyOwners )) {
            List<Long> buildingIds = satisfactionSurveyOwners.stream().map( SatisfactionSurveyBuilding::getBuildingId ).collect( Collectors.toList() );
            if (CollectionUtils.isNotEmpty( buildingIds )) {
                List<Long> buildingList = buildingIds.stream().distinct().collect( Collectors.toList() );
                StringBuilder sb = new StringBuilder();
                for (Long buildingId : buildingList) {
                    sb.append( buildingId ).append( "," );
                }
                String buildings = sb.toString();
                if (CollectionUtils.isNotEmpty( buildingIds )) {
                    satisfactionSurvey.setBuildingIds( buildings.substring( 0, buildings.length() - 1 ) );
                }
            }
        }

        return satisfactionSurvey;
    }

    @Override
    public void delete(String id) {
        super.removeById( id );
        LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( SatisfactionSurveyBuilding::getSatisfactionSurveyId, id );
        satisfactionSurveyBuildingMapper.delete( queryWrapper );

        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq( SatisfactionSurveyProblem::getSatisfactionSurveyId, id );
        satisfactionSurveyProblemMapper.delete( queryWrapper1 );
    }

    @Override
    public List<SatisfactionSurveyOwnerVo> getSatisfactionProblemCount(String satisfactionSurveyId) {
        List<SatisfactionSurveyOwnerVo> list = Lists.newArrayList();

        LambdaQueryWrapper<SatisfactionSurveyBuilding> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( SatisfactionSurveyBuilding::getSatisfactionSurveyId, satisfactionSurveyId );
        List<SatisfactionSurveyBuilding> buildingList = satisfactionSurveyBuildingMapper.selectList( queryWrapper );
        //应投票数量
        int roomCount = 0;
        for (SatisfactionSurveyBuilding satisfactionSurveyBuilding : buildingList) {
            roomCount = roomCount + baseMapper.listByBuildingId( satisfactionSurveyBuilding.getBuildingId() ).size();
        }
        roomCount = 5;
        LambdaQueryWrapper<SatisfactionSurveyProblem> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq( SatisfactionSurveyProblem::getSatisfactionSurveyId, satisfactionSurveyId );
        List<SatisfactionSurveyProblem> problemList = satisfactionSurveyProblemMapper.selectList( queryWrapper1 );
        if (CollectionUtils.isNotEmpty( problemList )) {
            for (SatisfactionSurveyProblem satisfactionSurveyProblem : problemList) {
                SatisfactionSurveyOwnerVo surveyOwnerVo = new SatisfactionSurveyOwnerVo();
                surveyOwnerVo.setProblemName( satisfactionSurveyProblem.getProblemName() );
                for (int i = 0; i < 4; i++) {
                    LambdaQueryWrapper<SatisfactionSurveyOwner> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.eq( SatisfactionSurveyOwner::getSurveyProblemId, satisfactionSurveyProblem.getId() );
                    int c = i + 1;
                    queryWrapper2.eq( SatisfactionSurveyOwner::getAnswer, String.valueOf( c ) );
                    Long count = satisfactionSurveyOwnerMapper.selectCount( queryWrapper2 );
                    if (count != null && count >= 1 && roomCount >= 1) {
                        Double divide = (Double.valueOf( count ) / Double.valueOf( roomCount )) * 100;
                        if (c == 1) {
                            surveyOwnerVo.setAnswer1Percentage( Tools.DecimalFormat( divide ) + "%" );
                        }
                        if (c == 2) {
                            surveyOwnerVo.setAnswer2Percentage( Tools.DecimalFormat( divide ) + "%" );
                        }
                        if (c == 3) {
                            surveyOwnerVo.setAnswer3Percentage( Tools.DecimalFormat( divide ) + "%" );
                        }
                        if (c == 4) {
                            surveyOwnerVo.setAnswer4Percentage( Tools.DecimalFormat( divide ) + "%" );
                        }
                    } else {
                        surveyOwnerVo.setAnswer1Percentage( "0.00%" );
                        surveyOwnerVo.setAnswer2Percentage( "0.00%" );
                        surveyOwnerVo.setAnswer3Percentage( "0.00%" );
                        surveyOwnerVo.setAnswer4Percentage( "0.00%" );
                    }

                }
                list.add( surveyOwnerVo );
            }
        }
        return list;
    }

    @Override
    public List<SatisfactionSurvey> findListSatisfaction(SatisfactionSurvey satisfactionSurvey) {
        List<SatisfactionSurvey> listSatisfaction = new ArrayList<>();
        if(StringUtils.isNotBlank( satisfactionSurvey.getBuildingId() )){
            String[] split = satisfactionSurvey.getBuildingId().split( "," );
            for (String s : split) {
                satisfactionSurvey.setBuildingId( s );
                List<SatisfactionSurvey> list = baseMapper.findListSatisfaction( satisfactionSurvey );
                if(CollUtil.isNotEmpty( list )){
                    listSatisfaction.addAll( list );
                    break;
                }
            }
        }
        return listSatisfaction;
    }

    @Override
    public SatisfactionSurvey findDetilSatisfaction(SatisfactionSurvey satisfactionSurvey) {
        //查询满意度
        SatisfactionSurvey byId = this.getById( satisfactionSurvey.getId() );
        if (byId != null && byId.getId() != null) {
            //查询满意度问题
            LambdaQueryWrapper<SatisfactionSurveyProblem> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq( SatisfactionSurveyProblem::getSatisfactionSurveyId, byId.getId() );
            List<SatisfactionSurveyProblem> problems = satisfactionSurveyProblemMapper.selectList( wrapper );
            byId.setSatisfactionSurveyProblemList( problems );

            //查询楼栋列表
            //判断业主的楼栋如果有多套则只展示一套
            List<SatisfactionSurveyBuilding> buildings = new ArrayList<>();
            if(StringUtils.isNotBlank( satisfactionSurvey.getBuildingId() )) {
                String[] split = satisfactionSurvey.getBuildingId().split( "," );
                for (String s : split) {
                    LambdaQueryWrapper<SatisfactionSurveyBuilding> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq( SatisfactionSurveyBuilding::getSatisfactionSurveyId, byId.getId() )
                            .eq( SatisfactionSurveyBuilding::getCommunityId, satisfactionSurvey.getCommunityId() )
                            .eq( SatisfactionSurveyBuilding::getBuildingId,s )
                            .eq( SatisfactionSurveyBuilding::getDelFlag,"0" );
                    List<SatisfactionSurveyBuilding> buildingList = satisfactionSurveyBuildingMapper.selectList( lambdaQueryWrapper );
                    if(CollUtil.isNotEmpty( buildingList )){
                        buildings.addAll( buildingList );
                        break;
                    }
                }
            }

            byId.setBuildingList( buildings );

            //查询业主答案
            if (CollUtil.isNotEmpty( buildings )) {
                List<SatisfactionSurveyOwner> owners = new ArrayList<>();
                for (SatisfactionSurveyBuilding building : buildings) {
                    LambdaQueryWrapper<SatisfactionSurveyOwner> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq( SatisfactionSurveyOwner::getSurveyProblemId, building.getSurveyProblemId() )
                            .eq( SatisfactionSurveyOwner::getSatisfactionSurveyBuilding, building.getId() )
                            .eq( SatisfactionSurveyOwner::getOwnerId, satisfactionSurvey.getOwnerId() );
                    List<SatisfactionSurveyOwner> selectList = satisfactionSurveyOwnerMapper.selectList( queryWrapper );
                    owners.addAll( selectList );
                }
                byId.setOwnerList( owners );
            }
        }

        return byId;
    }

    @Override
    @Transactional
    public AjaxResult saveSurveyOwner(SatisfactionSurvey satisfactionSurvey) {
        List<SatisfactionSurveyOwner> ownerList = satisfactionSurvey.getOwnerList();
        if(StringUtils.isNotBlank( satisfactionSurvey.getBuildingId() )) {
            String[] split = satisfactionSurvey.getBuildingId().split( "," );
            for (String s : split) {
                LambdaQueryWrapper<SatisfactionSurveyBuilding> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq( SatisfactionSurveyBuilding::getSatisfactionSurveyId, satisfactionSurvey.getId() )
                        .eq( SatisfactionSurveyBuilding::getCommunityId, satisfactionSurvey.getCommunityId() )
                        .eq( SatisfactionSurveyBuilding::getBuildingId,s )
                        .eq( SatisfactionSurveyBuilding::getDelFlag,"0" );
                List<SatisfactionSurveyBuilding> buildingList = satisfactionSurveyBuildingMapper.selectList( lambdaQueryWrapper );

                if (CollUtil.isNotEmpty(buildingList)) {
                    buildingList.forEach(building -> {
                        ownerList.stream()
                                .filter(surveyOwner -> StringUtils.equals(surveyOwner.getSurveyProblemId(), building.getSurveyProblemId()))
                                .map(surveyOwner -> {
                                    SatisfactionSurveyOwner newSurveyOwner = new SatisfactionSurveyOwner();
                                    BeanUtil.copyProperties(surveyOwner, newSurveyOwner);
                                    newSurveyOwner.setSatisfactionSurveyBuilding(building.getId());
                                    return newSurveyOwner;
                                })
                                .forEach(satisfactionSurveyOwnerMapper::insert);
                    });
                }

            }
        }

        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateSurveyOwner(List<SatisfactionSurveyOwner> surveyOwnerList) {
        surveyOwnerList.forEach( surveyOwner -> {
            satisfactionSurveyOwnerMapper.updateById( surveyOwner );
        } );
        return AjaxResult.success();
    }
}
