package com.wr.govern.service.impl.autonomy.decide;


import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.decide.DecideMapper;
import com.wr.govern.service.autonomy.decide.DecideService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.DecideRang;
import com.wr.remote.govern.autonomy.decide.DecideOwner;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决实现类
 */
@Service
public class DecideServiceImpl implements DecideService {

    @Autowired
    private DecideMapper decideMapper;


    /**
     * 查询自治表决列表
     * @param decide
     * @return
     */
    @Override
    public List<Decide> getList(Decide decide) {
        decide.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return decideMapper.getList(decide);
    }

    /**
     * 新增
     * @param decideVo
     * @return
     */
    @Override
    @Transactional
    public Integer addDecide(decideVo decideVo) {
        //1.先添加主表信息， 并生成decideId
        //设置创建人
        decideVo.setCreateBy(SecurityUtils.getUsername());
        //设置物业id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        decideVo.setEstateId(sysUser.getEstateId());
        //2.插入主表信息
        Integer integer = decideMapper.addDecide(decideVo);
        //3.为关联表添加信息
        addDecideRang(decideVo);
        //4.选项信息
        addDecideItem(decideVo);
        return integer;
    }




    /**
     * 通过DecideId讲发布小区的信息存储到关联表中
     * @return
     */
    @Transactional
    public void addDecideRang(decideVo decideVo){
        //获取小区id数组
        Long[] communityIds = decideVo.getCommunityIds();

        //确定当前数组不为空时， 讲decideId和communityId生成实体类， 然后存储到集合中。
        if(StringUtils.isNotEmpty(communityIds)){
            ArrayList<DecideRang> list = new ArrayList<DecideRang>();
            for (Long communityId : communityIds) {
                DecideRang decideRang = new DecideRang();
                decideRang.setStatuteId(decideVo.getDecideId());
                decideRang.setCommunityId(communityId);
                list.add(decideRang);
            }
            decideMapper.addDecideRang(list);
        }
    }


    /**
     * 通过DecideId讲选项信息添加到decide_item（选项）
     * @return
     */
    @Transactional
    public void addDecideItem(decideVo decideVo){
        /*
        存储的信息：bus_decide
        1.自治表决id
          statute_id=estateId
        2.选项内容decide_title
          decide_title=#{itemTitleOne}
          decide_title=#{itemTitleTwo}
        3.创建人
        4.创建时间
         */
        decideMapper.addDecideItem(decideVo);
    }


    /**
     * 修改
     * @param decideVo
     * @return
     */
    @Override
    @Transactional
    public int updateDecide(decideVo decideVo) {
        decideVo.setUpdateBy(SecurityUtils.getUsername());
        //1.修改主表信息
        int i=decideMapper.updateDecide(decideVo);
        //2.修改自联表-先删  在增
        delDecideRangById(decideVo.getDecideId());
        addDecideRang(decideVo);
        //3.修改选项表
        updateItemTitleOne(decideVo);
        updateItemTitleTwo(decideVo);
        return i;
    }






    /**
     * 根据表决id，删除关联表中的信息
     * @param statuteId
     */
    @Transactional
    public void delDecideRangById(Long statuteId){
        decideMapper.delDecideRangById(statuteId);
    }

    /**
     *修改第一个选项信息
     * @param decideVo
     */
    @Transactional
    public void updateItemTitleOne(decideVo decideVo){
        //先查询第一条选项id， 根据id进行修改
      Long id=  decideMapper.queryItemTitleOneId(decideVo.getDecideId());
      //生成实体类
        DecideItem decideItem = new DecideItem();
        decideItem.setItemId(id);
        decideItem.setItemTitle(decideVo.getItemTitleOne());
        decideItem.setUpdateBy(decideVo.getUpdateBy());
        decideMapper.updateItemTitleOne(decideItem);
    }
    /**
     *修改第二个选项信息
     * @param decideVo
     */
    @Transactional
    public void updateItemTitleTwo(decideVo decideVo){
        //根据主表id查询第二个选项id
        Long id= decideMapper.queryItemTitleTwoId(decideVo.getDecideId());
        //生成选项实体类
        DecideItem decideItem = new DecideItem();
        decideItem.setItemId(id);
        decideItem.setItemTitle(decideVo.getItemTitleTwo());
        decideItem.setUpdateBy(decideVo.getUpdateBy());
        decideMapper.updateItemTitleOne(decideItem);
    }



    /**
     * id查详情
     * @param decideId
     * @return
     */
    @Override
    public Decide queryDecideByIdOne(Long decideId) {
        return decideMapper.queryDecideByIdOne(decideId);
    }


    /**
     * id查询自联表信息
     * @param decideId
     * @return
     */
    @Override
    public List<Long> queryDecideRangById(Long decideId) {
        return decideMapper.queryDecideRangById(decideId);
    }

    /**
     * id查询选项信息
     * @param decideId
     * @return
     */
    @Override
    public List<DecideItem> queryDecideItemById(Long decideId) {
        return decideMapper.queryDecideItemById(decideId);
    }

    @Override
    @Transactional
    public int delDecide(Long[] decideId) {
        //先删主表信息
      int i=  decideMapper.delDecide(decideId);
        //然后删除关联表信息
        decideMapper.delDecideRandByIds(decideId);
        //然后删除选项信息
        decideMapper.delItemTitleByIds(decideId);
        return i;
    }

    /**
     * 获取选项信息，根据治决表id
     * @param statuteId
     * @return
     */
    @Override
    public List<DecideItem> getDecideItemById(Long statuteId) {
        return decideMapper.getDecideItemById(statuteId);
    }

    /**
     * 修改选项信息
     * @param itemId
     * @return
     */
    @Override
    @Transactional
    public int updateDecideItemById(Long itemId) {
        //思路：确认投票之前， 检测关联表改业主是否已投票，且登录人是否是业主（查业主， 查关联表）
        //查业主是否存在(oId为1时是业主！)
       Long userId = SecurityUtils.getUserId();
        //查关联表，该业主是否已经投票(num为0时，则可投票)
        int i =0;
      if(decideMapper.queryOwnerById(userId)>0 &&  decideMapper.queryDecideOwner(userId)==0 ){
          //DecideId和itemId存到关联表
          DecideOwner decideOwner = new DecideOwner();
          decideOwner.setDecideId(itemId);
          decideOwner.setOwnerId(userId);
          decideMapper.insertDecideItemByOwnerId(decideOwner);
          i = decideMapper.updateDecideItemById(itemId);
      }
        return i;
    }
}
