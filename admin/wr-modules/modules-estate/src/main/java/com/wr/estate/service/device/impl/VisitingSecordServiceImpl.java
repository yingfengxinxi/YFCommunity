package com.wr.estate.service.device.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.device.VisitingSecordMapper;
import com.wr.estate.service.device.VisitingSecordService;
import com.wr.remote.aiot.Door;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.vo.VisitingSecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/7 19:31
 */
@Slf4j
@Service
public class VisitingSecordServiceImpl extends ServiceImpl<VisitingSecordMapper, VisitingSecord> implements VisitingSecordService {


    @Override
    public List<VisitingSecordVo> getList(VisitingSecordVo visitingSecordVo) {
        return baseMapper.getList(visitingSecordVo);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public VisitingSecordVo getByIdVisitingSecordInfo(String id) {
        return baseMapper.getByIdVisitingSecordInfo(id);
    }

}
