package com.wr.govern.service.impl.home;


import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.home.HomePageMapper;
import com.wr.govern.service.home.HomePageService;
import com.wr.remote.govern.home.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * 首页
 *
 * @Author: luSu
 * @Date: 2022-12-1 10:25:05
 * @Desc:首页实现层
 */
@Service
public class HomePageImpl implements HomePageService {

    @Autowired
    private HomePageMapper homePageMapper;

    /**
     * 获取首页数据
     *
     * @param census
     * @return
     */
    @Override
    public AjaxResult getParams(Census census) {
        //获取当前登录人的物业id
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();

        AjaxResult ajaxResult = AjaxResult.success();
        Census c = new Census();

        //查询入党审核数据
        Census partyAuditCensus = homePageMapper.selectMemberById(estateId);
        c.setMemberAudit(partyAuditCensus.getMemberAudit());
        c.setMemberRebut(partyAuditCensus.getMemberRebut());

        //查询党员活动
        Census rescue = homePageMapper.selectRescueByEstateId(estateId);
        c.setRescueNotStart(rescue.getRescueNotStart());
        c.setRescueStart(rescue.getRescueStart());

        //查询警民互动
        Census bulletin = homePageMapper.selectBulletinByEstateId(estateId);
        c.setBulletinNum(bulletin.getBulletinNum());
        c.setFileNum(bulletin.getFileNum());

        //党员统计
        ArrayList<MemberCensus> censuses = homePageMapper.selectMemberNumById(estateId);
        c.setMemberList(censuses);

        //线索反馈次数
        ArrayList<Clue> clueList  = homePageMapper.selectClueNumById(estateId);
        c.setClueList(clueList);

        //业主委员会
        ArrayList<Committee> committeeList = homePageMapper.selectCommitteeNumById(estateId);
        c.setCommitteeList(committeeList);

        //党费统计
        census.setEstateId(estateId);
        ArrayList<HomePartyPay> partyFeeList =   homePageMapper.selectPartyPayNumById(census);
        c.setPartyFeeList(partyFeeList);



        ajaxResult.put(AjaxResult.DATA_TAG, c);
        return ajaxResult;
    }
}
