package com.wr.api.owner.service.impl.home.rescue;


import com.wr.api.owner.entity.home.rescue.ApiRescueUser;
import com.wr.api.owner.entity.home.rescue.vo.ApiRescueVo;
import com.wr.api.owner.mapper.home.rescue.ApiRescueMapper;
import com.wr.api.owner.service.home.rescue.ApiRescueService;
import com.wr.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/15 11:46
 * @Desc: 社区救助实现类
 */
@Service
public class ApiRescueServiceImpl implements ApiRescueService {


    @Autowired
    private ApiRescueMapper rescueMapper;


    /**
     * 获取社区救助列表
     *
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    @Override
    public List<ApiRescueVo>    getList(ApiRescueVo rescueVo) {
        return rescueMapper.getList(rescueVo);
    }

    /**
     * 获取社区救助详情
     *
     * @param rescueId
     * @return
     */
    @Override
    public ApiRescueVo selectOneById(Long rescueId) {
        return rescueMapper.selectOneById(rescueId);
    }

    /**
     * 根据求助id查询该求助的救助者信息
     *
     * @param rescueId
     * @return
     */
    @Override
    public List<ApiRescueUser> getRescueUserById(Long rescueId) {
        return rescueMapper.getRescueUserById(rescueId);
    }


    /**
     * 用户点击参与救助，讲用户信息新增关联表
     *
     * @param apiRescueUser
     * @return
     */
    @Override
    @Transactional
    public Integer add(ApiRescueUser apiRescueUser) {
        // 查询志愿者是否已经参与了社区救助
        int count = rescueMapper.countByUser(apiRescueUser);
        if (count > 0) {
            throw new ServiceException("您已经参与救助，不可以重复参与！");
        }
        return rescueMapper.add(apiRescueUser);
    }

    /**
     * 获取社区救助列表-别人发布，我已参加
     *
     * @param rescueVo
     * @return
     * @throws ParseException
     */
    @Override
    public List<ApiRescueVo> getJoinRescueList(ApiRescueVo rescueVo) {
        return rescueMapper.getJoinRescueList(rescueVo);
    }
}
