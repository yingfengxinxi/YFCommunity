package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.wr.api.owner.entity.dto.ComAcDTO;
import com.wr.api.owner.entity.vo.serve.ComAcVo;
import com.wr.api.owner.mapper.serve.ComActivityMapper;
import com.wr.api.owner.service.serve.ComActivityService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: xuShu
 * @Date: 2022-11-16 09:50:05
 * @Desc:
 */
@Service
public class ComActivityServiceImpl implements ComActivityService {

    @Autowired
    private ComActivityMapper comActivityMapper;

    /**
     * 查询本小区的活动
     *
     * @param ownerId
     * @return
     */
    @Override
    public List<ComAcDTO> searchByCommunityId(Long ownerId, Long communityId) {
        //根据ownId查询我是否参加了本小区活动
        List<ComAcVo> activityOwn = comActivityMapper.getActivityOwn(ownerId);
        //如果为空，说明没有参加
        if (activityOwn.size() == 0) {
            return null;
        } else {
            List<ComAcDTO> myActivity = comActivityMapper.getMyActivity(ownerId, communityId);
//            List<ComAcDTO> comAcDTOList = Objects.requireNonNull(BeanUtil.copyToList(myActivity,ComAcDTO.class));
            return myActivity;
        }
    }

    /**
     * 查询我参与的活动详情
     *
     * @param comAcVo
     * @return
     */
    @Override
    public ComAcVo getComActivityInfo(ComAcVo comAcVo) {
        ComAcVo info = comActivityMapper.getActivityInfo(comAcVo.getActivityId());
        //查询所需要物资
        List<Map<String,Object>> maps = comActivityMapper.getSupplyName(comAcVo.getActivityId());
        info.setSupplys(maps);
        return info;
    }

    /**
     * 签到
     *
     * @param comAcVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult updateSignTime(ComAcVo comAcVo) {
        comAcVo.setSignUpTime(Convert.toDate(DateUtil.now()));
        //如果是0 可以签到  1 没在签到时间
        int enable = comActivityMapper.getStartTime(comAcVo.getActivityId(), comAcVo.getSignUpTime());
        if (enable == 0) {
            int i = comActivityMapper.newSign(comAcVo);
            if (i > 0) {
                return AjaxResult.success("签到成功");
            }
            return AjaxResult.error("签到失败");
        }
        return AjaxResult.error("没到签到时间");


    }


}
