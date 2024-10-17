package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.dto.VenueDTO;
import com.wr.api.owner.entity.serve.StadiumPoint;
import com.wr.api.owner.entity.vo.serve.StadiumPointVo;
import com.wr.api.owner.entity.vo.serve.StadiumRuleVo;
import com.wr.api.owner.entity.vo.serve.StadiumVo;
import com.wr.api.owner.entity.vo.serve.TypeActVo;
import com.wr.api.owner.mapper.serve.StadiumMapper;
import com.wr.api.owner.service.serve.StadiumService;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:19:45
 * @Desc:
 */
@Service
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    private StadiumMapper stadiumMapper;
    /**
     * 查询本小区的活动场馆
     * @param stadiumVo
     * @return
     */
    @Override
    public List<VenueDTO> getStadiumList(StadiumVo stadiumVo) {

        List<VenueDTO> stadiumVos = stadiumMapper.stadiumList(stadiumVo);
//        List<VenueDTO> venueDTOList = Objects.requireNonNull(BeanUtil.copyToList(stadiumVos,VenueDTO.class));
        return stadiumVos;
    }

    /**
     * 查询本小区的活动场馆详情信息
     * @param stadiumVo
     * @return
     */
    @Override
    public List<StadiumVo> getStadiumInfo(StadiumVo stadiumVo) {
        //根据日期查询本场馆的预约信息
       List<StadiumPointVo> appointList =  stadiumMapper.getAppointyId(stadiumVo.getVenueId(),stadiumVo.getCreateTime(),stadiumVo.getCommunityId());
       //查询场馆详细信息
       List<StadiumVo> stadiumVos = stadiumMapper.getStadiumInfo(stadiumVo.getVenueId());
       //遍历并将预约信息插入列表中
       stadiumVos.forEach(stadiumVo1 -> stadiumVo1.setStadiumPointVos(appointList));
       return stadiumVos;
    }

    /**
     * 预约场馆
     * @param stadiumPoint
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertAppoint(StadiumPoint stadiumPoint) {
        stadiumPoint.setCreateTime(new Date());
//        String username = SecurityUtils.getLoginUser().getUsername();
        stadiumPoint.setCreateBy(stadiumPoint.getAccountName());
        //查询预约时间内有无场馆使用
        //获取间隔时间段
        //获取先i个小时的时间
        /*Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(stadiumPoint.getPlanStart());
        Date dt1=c1.getTime();f
        c2.setTime(stadiumPoint.getPlanEnd());
        Date dt2=c2.getTime();*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = DateUtils.addHours(stadiumPoint.getPlanStart(),-stadiumPoint.getGapHour());
        Date dt2 = DateUtils.addHours(stadiumPoint.getPlanEnd(),stadiumPoint.getGapHour());
        int getcount = stadiumMapper.getcount(sdf.format(dt1),sdf.format(dt2), stadiumPoint.getVenueId());
        if (getcount == 0){
            //如果没有 就添加
            return AjaxResult.success(stadiumMapper.insertStadium(stadiumPoint));
        }
        else {
            return AjaxResult.error("场馆该时段已被预约");
        }
    }
    /**
     * 查询我得预约
     * @param stadiumVo
     * @return
     */
    @Override
    public List<VenueDTO> getMyAppointInfo(StadiumVo stadiumVo) {
        //根据用户查询场馆预约
        List<VenueDTO> list = stadiumMapper.getAppointMine(stadiumVo);
//        List<VenueDTO> venueDTOList = Objects.requireNonNull(BeanUtil.copyToList(list,VenueDTO.class));
        return list;
    }

    /**
     * 对于预约申请进行修改
     * @param stadiumPoint
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult updateStatus(StadiumPoint stadiumPoint) {
        stadiumPoint.setCurrentEnd(new Date());
        stadiumPoint.setAppointStatus("3");
       int row =  stadiumMapper.updateEndStatus(stadiumPoint);
       if (row>0){
           return AjaxResult.success("修改成功");
       }
        return AjaxResult.success("修改失败");
    }


    /**
     * 查询本小区活动类型
     * @param communityId
     * @return
     */
    @Override
    public List<TypeActVo> getTypeName(Long communityId) {
        return stadiumMapper.getCommunityAct(communityId);
    }


    /**
     * 根据小区查询物业id并查询场馆规则
     * @param communityId
     * @return
     */
    @Override
    public List<StadiumRuleVo> getRule(Long communityId) {

        return stadiumMapper.getVenueRule(communityId);
    }

    /**
     * 查询我得预约详细信息
     * @param communityId
     * @param venueId
     * @param cardNo
     * @return
     */
    @Override
    public List<StadiumPointVo> getMyInfo(Long communityId, Long venueId, String cardNo, Long appointId) {
        //根据身份证、小区id、场馆id查询我得预约详情
        List<StadiumPointVo> appointList =stadiumMapper.getMyPoint(communityId,venueId,cardNo, appointId);
//        StadiumVo stadiumVo = new StadiumVo();
//        stadiumVo.setStadiumPointVos(appointList);
        return appointList;
    }


}
