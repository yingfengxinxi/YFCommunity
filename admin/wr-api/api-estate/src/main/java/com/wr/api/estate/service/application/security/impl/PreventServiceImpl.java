package com.wr.api.estate.service.application.security.impl;

import com.wr.api.estate.mapper.application.security.PreventMapper;
import com.wr.api.estate.service.application.security.PreventService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:46
 * @Desc: 防控信息业务层
 */
@Service
public class PreventServiceImpl implements PreventService {

    @Autowired
    private PreventMapper preventMapper;

    @Override
    public List<Prevent> getList(Prevent prevent) {

        return preventMapper.getList(prevent);
    }

    @Override
    public PreventVO getPreventDetail(Long preventId) {
        PreventVO preventDetail = preventMapper.getPreventDetail(preventId);
        return preventDetail;
    }

}
