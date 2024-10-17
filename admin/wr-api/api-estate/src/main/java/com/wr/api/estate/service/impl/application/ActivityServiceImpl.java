package com.wr.api.estate.service.impl.application;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.api.estate.entity.vo.application.ActivityVO;
import com.wr.api.estate.mapper.application.ActivityMapper;
import com.wr.api.estate.service.application.ActivityService;
import com.wr.common.core.utils.StringUtils;
import com.wr.remote.estate.activity.ActiviteManage;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 15:32:45
 * @Desc:
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;

    @Override
    public List<ActivityVO> searchByCommunityId(Long communityId, String state) {
        List<ActivityVO> list = activityMapper.searchByCommunityId(communityId, state);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(activityVO -> {
                if (StringUtils.isNotEmpty(activityVO.getState())) {
                    activityVO.setState(stateName(activityVO.getState()));
                }

            });
        }
        return list;
    }

    private String stateName(String state) {
        if ("0".equals(state)) {
            state = "进行中";
        }
        if ("1".equals(state)) {
            state = "未开始";
        }
        if ("2".equals(state)) {
            state = "已结束";
        }
        return state;
    }

    @Override
    public ActivityVO detailByActivityId(Long activityId) {
        //增加访问量
        activityMapper.updateByIdViewNumber(activityId);
        ActivityVO activityVO = activityMapper.detailByActivityId(activityId);
        if (StringUtils.isNotEmpty(activityVO.getState())) {
            activityVO.setState(stateName(activityVO.getState()));
        }
        return activityVO;
    }

    @Override
    public List<ActiviteManage> selectJoinPeopleList(ActiviteManage activiteManage) {
        return activityMapper.selectJoinPeopleList(activiteManage);
    }


}
