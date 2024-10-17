package com.wr.govern.service.impl.autonomy.statute;


import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.statute.StatuteMapper;
import com.wr.govern.service.autonomy.statute.StatuteService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.statute.Statute;
import com.wr.remote.govern.autonomy.statute.StatuteRang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/25 14:46
 * @Desc: 自治规约实现类
 */
@Service
public class StatuteServiceImpl implements StatuteService {

    @Autowired
    private StatuteMapper statuteMapper;

    /**
     * 查询自治规约列表
     * @param statute
     * @return
     * @throws ParseException
     */
    @Override
    public List<Statute> getList(Statute statute) {
        statute.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return statuteMapper.getList(statute);
    }


    /**
     * 添加自治规约表格中的数据
     * @param statute
     * @return
     */
    @Override
    @Transactional
    public Integer addStatute(Statute statute) {
        //设置当前创建人
        statute.setCreateBy(SecurityUtils.getUsername());
        //获取当前登录账户的物业id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        statute.setEstateId(sysUser.getEstateId());

        //自治规约信息存储到自治规约表格
        Integer add = statuteMapper.addStatute(statute);
        //自治规约发布小区信息，存储到关联表
        AddStatuteRang(statute);
        return add;
    }



    /**
     * 自治规约发布小区信息，添加关联表
     * @param statute
     * @return
     */
    @Transactional
    public void AddStatuteRang(Statute statute) {
        //获取到社区id
        Long[] communityIds = statute.getCommunityIds();
        //讲community_id和statute_id封装到关联表实体类， 生成集合
        if(StringUtils.isNotEmpty(communityIds)){
            ArrayList<StatuteRang> list = new ArrayList<StatuteRang>();
            for (Long communityId : communityIds) {
                StatuteRang sta = new StatuteRang();
                sta.setStatuteId(statute.getStatuteId());
                sta.setCommunityId(communityId);
                list.add(sta);
            }
            statuteMapper.AddStatuteRang(list);
        }
    }


    /**
     * 根据bus_statute_id查询bus_statute的记录
     * @param statuteId
     * @return
     */
    @Override
    public Statute getStatuteById(Long statuteId) {
        return statuteMapper.getStatuteById(statuteId);
    }

    /**
     *根据statute_id查询bus_statute_rang  关联表的详情
     * @param statuteId
     * @return
     */
    @Override
    public List<Long> selectStatuteCommunityById(Long statuteId) {
        return statuteMapper.selectStatuteCommunityById(statuteId);
    }


    /**
     * 修改
     * @param statute
     * @return
     */
    @Override
    @Transactional
    public int updateStatute(Statute statute) {
        //获取修改人
        statute.setUpdateBy(SecurityUtils.getUsername());
        //先修改关联表格（1.先删  2.再增）
        delCommunityByStatuteId(statute.getStatuteId());
        AddStatuteRang(statute);
        //在修改治约(直接返回)
        return statuteMapper.updateStatute(statute);
    }

    /**
     * 删除
     * @param statuteId
     * @return
     */
    @Override
    @Transactional
    public int delStatute(Long[] statuteId) {
        //批量删除关联表信息
        //delCommunityByStatuteIds(statuteId);
        //批量删除bus_statute信息
        return statuteMapper.delStatute(statuteId);
    }


    /**
     * 通过治约id删除关联表中的所有社区id， 返回的是影响行数
     * @param statuteId
     * @return
     */
    @Transactional
    public int delCommunityByStatuteId(Long statuteId){
        return statuteMapper.delCommunityByStatuteId(statuteId);
    }


    /**
     * 批量删除关联表信息
     * @param statuteId
     * @return
     */
    @Transactional
    public int delCommunityByStatuteIds(Long[] statuteId){
        return statuteMapper.delCommunityByStatuteIds(statuteId);
    }


}
