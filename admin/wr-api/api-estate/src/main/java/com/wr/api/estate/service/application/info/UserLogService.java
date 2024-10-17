package com.wr.api.estate.service.application.info;

import com.wr.api.estate.entity.vo.application.info.UserLogVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 14:04:22
 * @Desc: 业务层
 */
public interface UserLogService {
    /**
     * 列表
     * @param userLogVo
     * @return
     */
    List<UserLogVo> getList(UserLogVo userLogVo);

    /**
     * 表单详情
     * @param userLogVo
     * @return
     */
    UserLogVo getDetail (UserLogVo userLogVo);

    /**
     * 今日人行总数
     * @param userLogVo
     * @return
     */
    int getUserCount(UserLogVo userLogVo);

}
