package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.common.core.exception.ServiceException;
import com.wr.remote.domain.RentalVo;
import com.wr.api.owner.mapper.SensitiveMapper;
import com.wr.api.owner.mapper.serve.RentalMapper;
import com.wr.api.owner.service.serve.RentalService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ApiConstants;
import com.wr.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 09:12:17
 * @Desc: 出租
 */
@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private  SensitiveMapper sensitiveMapper;

    @Autowired
    private RedisService redisService;


    private static Lock reentrantLock = new ReentrantLock();
    /**
     * 获取所有在租的出租信息
     *
     * @param rentalVo
     * @return
     */
    @Override
    public List<RentalVo> getRental(RentalVo rentalVo) {

        return rentalMapper.getRentalList(rentalVo);
    }


    /**
     * 获取我得房屋出租信息
     *
     * @param rentalVo
     * @return
     */
    @Override
    public List<RentalVo> getMyRental(RentalVo rentalVo) {
        //获取当前登录用户的id
//        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
//        System.out.println(sysUser);
        return rentalMapper.getMyRental(rentalVo);
    }

    /**
     * 我要出租
     *
     * @param rentalVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertRenTal(RentalVo rentalVo) {
        // 查询房屋地址（暂时不处理）
        List<String> count = rentalMapper.getMyAddress(rentalVo.getRoomNumber(),rentalVo.getCommunityId());
         if(CollUtil.isNotEmpty( count )){
             if(count.contains( "2" )){
                 throw new ServiceException("该房屋已下架，请联系物业上架此房屋！");
             }else {
                 throw new ServiceException("该房屋已经被出租！");
             }
         }
        //获取当前小区
        //根据地址查询房间面积
//        BigDecimal area = rentalMapper.getTotalArea(rentalVo.getCommunityId(),rentalVo.getRoomName(),rentalVo.getBuildingId(),rentalVo.getUnitId());
        rentalVo.setPublishTime(new Date());
        rentalVo.setRentStatus("0");

        int i = rentalMapper.insertMyRental(rentalVo);

        return AjaxResult.success(i>0?"添加成功":"添加失败");
    }

    /**
     * 获取房屋详情
     * @param rentId
     * @return
     */
    @Override
    public List<RentalVo> getRentalInfo(Long rentId) {
        reentrantLock.lock();
        try {
            String key = ApiConstants.TIMES_KEY + DateUtils.dateTime() + ":" + rentId;
            //获取浏览次数
            int readNum =  rentalMapper.getReadNum(rentId);
            //将浏览次数添加到缓存中
//        redisService.setCacheObject(key,readNum);
            //获取缓存浏览次数
//        Integer cacheNum = redisService.getCacheObject(key);
            //如果点击所在房屋，查询房屋的详情，那么浏览次数+1
            readNum++;
            redisService.setCacheObject(key,readNum);
            //修改浏览次数
            rentalMapper.updateReadNum(readNum,rentId);
            return rentalMapper.RentalInfo(rentId);
        }finally {
            reentrantLock.unlock();
        }

    }

    @Override
    public List<TreeSelectVo> getCommunityTree(Long ownerId) {
        return rentalMapper.getCommunityTree(ownerId);
    }

    @Override
    public List<TreeSelectVo> getBuildingTree(Long communityId,Long ownerId) {
        return rentalMapper.getBuildingTree(communityId,ownerId);
    }

    @Override
    public List<TreeSelectVo> getUnitTree(Long buildingId,Long ownerId) {
        return rentalMapper.getUnitTree(buildingId,ownerId);
    }

    @Override
    public List<TreeSelectVo> getRoomTree(Long unitId,Long ownerId) {
        return rentalMapper.getRoomTree(unitId,ownerId);
    }

    @Override
    public Integer outStack(Long rentId, String rentStatus) {
        return rentalMapper.outStack(rentId, rentStatus);
    }


}
