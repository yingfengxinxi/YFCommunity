package com.wr.api.owner.service.estateapi.vote.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wr.api.owner.mapper.estateapi.AccountManageMapper;
import com.wr.api.owner.mapper.estateapi.vote.BusVoteMapper;
import com.wr.api.owner.mapper.estateapi.vote.BusVoteOwnerMapper;
import com.wr.api.owner.mapper.estateapi.vote.BusVoteScopeMapper;
import com.wr.api.owner.service.estateapi.vote.BusVoteService;
import com.wr.api.owner.service.estateapi.vote.OwnerService;
import com.wr.common.core.utils.Tools;
import com.wr.remote.domain.vo.BusVoteResultVo;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:26
 */
@Service
public class BusVoteServiceImpl extends ServiceImpl<BusVoteMapper, BusVote> implements BusVoteService {

    @Resource
    private BusVoteScopeMapper voteScopeMapper;

    @Resource
    private BusVoteOwnerMapper voteOwnerMapper;

    @Resource
    private OwnerService ownerService;

    /**
     * @param busVote
     * @return
     */
    @Override
    public List<BusVote> getList(BusVote busVote) {
        List<BusVote> list = baseMapper.getList( busVote );
        if (CollUtil.isNotEmpty( list )) {
            for (BusVote vote : list) {
                vote.setCountnum( baseMapper.findCountNumVote( vote) );
            }
        }
        return list;
    }

    /**
     * @param busVoteVo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVote(BusVoteVo busVoteVo) {
        busVoteVo.setVoteNo( "TP" + IdWorker.getIdStr() );
        //保存投票数据
        super.save( busVoteVo );
        //增加 投票范围
        extracted( busVoteVo );


    }

    /**
     * @param busVoteVo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateVote(BusVoteVo busVoteVo) {
        //修改投票数据
        super.updateById( busVoteVo );
        //增加 投票范围
        extracted( busVoteVo );
    }


    /**
     * @param busVoteVo
     */
    private void extracted(BusVoteVo busVoteVo) {
        List<BusVoteScope> voteScopeList = busVoteVo.getVoteScopeList();
        LambdaQueryWrapper<BusVoteScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( BusVoteScope::getVoteId, busVoteVo.getId() );
        List<BusVoteScope> busVoteScopeList = voteScopeMapper.selectList( queryWrapper );
        //删除投标业主
        if (CollectionUtils.isNotEmpty( busVoteScopeList )) {
            busVoteScopeList.stream().forEach( busVoteScope -> {
                LambdaQueryWrapper<BusVoteOwner> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq( BusVoteOwner::getVoteScopeId, busVoteScope.getId() );
                voteOwnerMapper.delete( queryWrapper1 );
            } );
        }
        //删除投票范围
        voteScopeMapper.delete( queryWrapper );
        if (CollectionUtils.isNotEmpty( voteScopeList )) {
            voteScopeList.stream().forEach( voteScope -> {
                voteScope.setVoteId( busVoteVo.getId() );
                voteScope.setCreateBy( busVoteVo.getUpdateBy() );
                voteScope.setCreateTime( busVoteVo.getUpdateTime() );
                voteScope.setDelFlag( busVoteVo.getDelFlag() );
                voteScope.setId( UUID.randomUUID().toString().replace( "-", "" ) );
                voteScopeMapper.insert( voteScope );
                Long accountId = ownerService.getByOwnerPhoneAccountId( voteScope.getOwnerPhone() );
                BusVoteOwner busVoteOwner = new BusVoteOwner();
                busVoteOwner.setAccountId( accountId );
                busVoteOwner.setVoteScopeId( voteScope.getId() );
                busVoteOwner.setCreateBy( voteScope.getCreateBy() );
                busVoteOwner.setCreateTime( voteScope.getCreateTime() );
                busVoteOwner.setDelFlag( voteScope.getDelFlag() );
                busVoteOwner.setId( UUID.randomUUID().toString().replace( "-", "" ) );
                voteOwnerMapper.insert( busVoteOwner );
            } );
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public BusVoteVo get(String id) {
        BusVoteVo busVoteVo = new BusVoteVo();
        BusVote busVote = baseMapper.selectById( id );
        BeanUtil.copyProperties( busVote, busVoteVo, true );
        return busVoteVo;
    }

    /**
     * @param id
     */
    @Override
    public void removeVote(String id) {
        baseMapper.deleteById( id );
        LambdaQueryWrapper<BusVoteScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( BusVoteScope::getVoteId, id );
        List<BusVoteScope> busVoteScopeList = voteScopeMapper.selectList( queryWrapper );
        //删除投标业主
        if (CollectionUtils.isNotEmpty( busVoteScopeList )) {
            busVoteScopeList.stream().forEach( busVoteScope -> {
                LambdaQueryWrapper<BusVoteOwner> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq( BusVoteOwner::getVoteScopeId, busVoteScope.getId() );
                voteOwnerMapper.delete( queryWrapper1 );
            } );
        }
        //删除投票范围
        voteScopeMapper.delete( queryWrapper );
    }

    @Override
    public BusVoteVo getResult(String id) {
        BusVoteVo busVoteVo = new BusVoteVo();
        BusVote busVote = baseMapper.selectById( id );
        busVoteVo.setVoteNo( busVote.getVoteNo() );
        busVoteVo.setVoteName( busVote.getVoteName() );
        busVoteVo.setIssuesName( busVote.getIssuesName() );


        Integer voteTotal = baseMapper.getByIdStatusCount( id, Lists.newArrayList() );
        busVoteVo.setVoteTotal( voteTotal );
        //已投票
        String status = "1,2,3";
        Integer yVoteTotal = baseMapper.getByIdStatusCount( id, Arrays.asList( status.split( "," ) ) );
        busVoteVo.setYVoteTotal( yVoteTotal );
        //已投票百分比
        if (yVoteTotal >= 1) {
            Double yVotePercentage = (Double.valueOf( yVoteTotal ) / Double.valueOf( voteTotal )) * 100;
            busVoteVo.setYVotePercentage( yVotePercentage + "%" );
        } else {
            busVoteVo.setYVotePercentage( "0.00%" );
        }


        //未反馈数量
        busVoteVo.setWVoteTotal( busVoteVo.getVoteTotal() - busVoteVo.getYVoteTotal() );

        //已投票百分比
        if (busVoteVo.getWVoteTotal() >= 1) {
            Double wVotePercentage = (Double.valueOf( busVoteVo.getWVoteTotal() ) / Double.valueOf( voteTotal )) * 100;
            busVoteVo.setWVotePercentage( wVotePercentage + "%" );
        } else {
            busVoteVo.setWVotePercentage( "0.00%" );
        }
        //总面积
        BigDecimal totalArea = baseMapper.getByIdStatusArea( id, Lists.newArrayList() );
        if (totalArea == null) {
            totalArea = new BigDecimal( "0.00" );
        }
        busVoteVo.setTotalArea( totalArea );

        //已投面积
        BigDecimal yTotalArea = baseMapper.getByIdStatusArea( id, Arrays.asList( "1,2,3".split( "," ) ) );
        if (yTotalArea == null) {
            yTotalArea = new BigDecimal( "0.00" );
        }
        busVoteVo.setYTotalArea( yTotalArea );
        //已投面积百分比
        if (Double.valueOf( yTotalArea.toString() ) >= 1) {
            Double yTotalAreaPercentage = (Double.valueOf( String.valueOf( yTotalArea ) ) / Double.valueOf( String.valueOf( totalArea ) )) * 100;
            busVoteVo.setYTotalAreaPercentage( Tools.DecimalFormat( yTotalAreaPercentage ) + "%" );
        } else {
            busVoteVo.setYTotalAreaPercentage( "0.00%" );
        }

        //未反馈面积
        BigDecimal wTotalArea = baseMapper.getByIdStatusArea( id, Arrays.asList( "0".split( "," ) ) );
        if (wTotalArea == null) {
            wTotalArea = new BigDecimal( "0.00" );
        }
        busVoteVo.setWTotalArea( wTotalArea );
        //未反馈面积百分比
        if (Double.valueOf( wTotalArea.toString() ) >= 1) {
            Double wTotalAreaPercentage = (Double.valueOf( String.valueOf( wTotalArea ) ) / Double.valueOf( String.valueOf( totalArea ) )) * 100;
            busVoteVo.setWTotalAreaPercentage( Tools.DecimalFormat( wTotalAreaPercentage ) + "%" );
        } else {
            busVoteVo.setWTotalAreaPercentage( "0.00%" );
        }

        List<BusVoteResultVo> resultVoList = Lists.newArrayList();
        extracted( resultVoList );
        resultVoList.stream().forEach( voteResultVo -> {
            String ownerStatus = voteResultVo.getOwnerStatus();
            List<BusVoteResultVo> voteResultVos = baseMapper.getByIdCount( id, ownerStatus );
            if (CollectionUtils.isNotEmpty( voteResultVos )) {
                BusVoteResultVo voteResultVo1 = voteResultVos.get( 0 );
                voteResultVo.setNumber( voteResultVo1.getNumber() );
                //计算百分比
                Double percentage = (Double.valueOf( voteResultVo1.getNumber() ) / Double.valueOf( busVoteVo.getVoteTotal() )) * 100;
                voteResultVo.setPercentage( new BigDecimal( Tools.DecimalFormat( percentage ) ) );
            }

        } );

        busVoteVo.setVoteResultList( resultVoList );

        //同意票数
        Integer tVoteTotal = baseMapper.getByIdStatusCount( id, Arrays.asList( "1".split( "," ) ) );
        //反对票数
        Integer fVoteTotal = baseMapper.getByIdStatusCount( id, Arrays.asList( "2".split( "," ) ) );

        if (tVoteTotal > fVoteTotal) {
            busVoteVo.setResult( "通过" );
        } else {
            busVoteVo.setResult( "反对" );
        }

        return busVoteVo;
    }

    private static void extracted(List<BusVoteResultVo> resultVoList) {
        BusVoteResultVo voteResult = new BusVoteResultVo();
        voteResult.setName( "赞成" );
        voteResult.setNumber( 0 );
        voteResult.setPercentage( new BigDecimal( "0.00" ) );
        voteResult.setOwnerStatus( "1" );
        resultVoList.add( voteResult );

        voteResult = new BusVoteResultVo();
        voteResult.setName( "反对" );
        voteResult.setNumber( 0 );
        voteResult.setPercentage( new BigDecimal( "0.00" ) );
        voteResult.setOwnerStatus( "2" );
        resultVoList.add( voteResult );

        voteResult = new BusVoteResultVo();
        voteResult.setName( "弃权" );
        voteResult.setNumber( 0 );
        voteResult.setPercentage( new BigDecimal( "0.00" ) );
        voteResult.setOwnerStatus( "3" );
        resultVoList.add( voteResult );
    }


}
