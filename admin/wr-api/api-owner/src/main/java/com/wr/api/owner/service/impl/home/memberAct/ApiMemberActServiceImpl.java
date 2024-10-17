package com.wr.api.owner.service.impl.home.memberAct;


import com.wr.api.owner.entity.home.activity.ApiActivity;
import com.wr.api.owner.entity.home.activity.ApiActivityOwner;
import com.wr.api.owner.entity.home.activity.vo.apiActivityVo;
import com.wr.api.owner.entity.home.memberAct.ApMember;
import com.wr.api.owner.entity.home.memberAct.ApiMemberAct;

import com.wr.api.owner.entity.home.memberAct.ApiMemberJoin;
import com.wr.api.owner.mapper.home.memberAct.ApiMemberActMapper;
import com.wr.api.owner.service.home.activity.ApiActivityService;
import com.wr.api.owner.service.home.memberAct.ApiMemberActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 党建活动
 */
@Service
public class ApiMemberActServiceImpl implements ApiMemberActService {


    @Autowired
    private ApiMemberActMapper apiMemberActMapper;


    /**
     * 查列表
     * @param apiMemberAct
     * @return
     */
    @Override
    public List<ApiMemberAct> getList(ApiMemberAct apiMemberAct) {
        return apiMemberActMapper.getList(apiMemberAct);
    }

    /**
     * 查党建活动 详情
     * @param apiMemberAct
     * @return
     */
    @Override
    public ApiMemberAct getInfo(ApiMemberAct apiMemberAct) {
        //查询活动详情
        ApiMemberAct info = apiMemberActMapper.getInfo(apiMemberAct);

        //如果状态码正确， 则直接返回info，否则需更新状态码！
        if(info.getStartTime().compareTo(new Date())>0 && "0".equals(info.getStatus())){
            return info;
        }else if(info.getStartTime().compareTo(new Date())<0 && "1".equals(info.getStatus() )){
            return info;
        }else{
            //倘若状态码和当前活动状态不一致，则更新状态码status字段
            String s = this.updateActivityStatus(info);
            info.setStatus(s);
            return info;
        }
    }


    /**
     * 根据开始、结束时间、 更新活动状态码
     * status      活动状态 0 开启 1 结束
     * planStatus  活动进度 0 活动待启动 1 活动报名中 2 活动结束
     * @param apiMemberAct
     */
    @Transactional
    public String updateActivityStatus(ApiMemberAct apiMemberAct){
        // now dateTime < startTime  status      活动状态 0 开启 1 结束
        if(apiMemberAct.getStartTime().compareTo(new Date())>0){
            //状态更新
            apiMemberAct.setStatus("0");
            apiMemberActMapper.updateActivityStatus(apiMemberAct);
            return "0";
        }else{
            //状态更新
            apiMemberAct.setStatus("1");
            apiMemberActMapper.updateActivityStatus(apiMemberAct);
            return "1";
        }



    }




    /**
     * 报名党建活动
     * @param apiMemberJoin
     * @return
     */
    @Override
    @Transactional
    public Integer add(ApiMemberJoin apiMemberJoin) {
        //通过accountId判断当前登录者是否是党员
        ApMember member = apiMemberActMapper.selectMemberById(apiMemberJoin.getAccountId());

        if(member!=null){
        //校验当前用户是否已经参与
        ApiMemberJoin ownerStatus = apiMemberActMapper.getOwnerStatus(apiMemberJoin);
        if(!(ownerStatus!=null)){
            apiMemberJoin.setMemberId(member.getMemberId());
            return apiMemberActMapper.add(apiMemberJoin);
        }
        }
        //非党员，则返回0,直接error
        return 0;
    }



    @Override
    public ApiMemberJoin getOwnerStatus(ApiMemberJoin apiActivityOwner) {
        return apiMemberActMapper.getOwnerStatus(apiActivityOwner);
    }
}
