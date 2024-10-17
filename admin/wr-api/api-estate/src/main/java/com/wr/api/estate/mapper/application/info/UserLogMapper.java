package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.application.info.UserLogVo;
import com.wr.remote.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 14:04:22
 * @Desc: 数据层
 */
public interface UserLogMapper {
    /**
     * 列表
     *
     * @param userLogVo
     * @return
     */
    List<UserLogVo> getList(UserLogVo userLogVo);

    Integer getByPhone(@Param("phonenumber") String phonenumber, @Param("userId") Long userId);

    /**
     * 表单详情
     *
     * @param userLogVo
     * @return
     */
    UserLogVo getDetail(UserLogVo userLogVo);

    /**
     * 今日人行总数
     *
     * @param userLogVo
     * @return
     */
    int getUserCount(UserLogVo userLogVo);

    SysUser selectUserById(@Param("userId") Long userId);

    /**
     * @param sysUser
     */
    void updateUser(SysUser sysUser);
}
