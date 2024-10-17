package com.wr.govern.service.autonomy.social;


import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;
import com.wr.remote.govern.autonomy.social.Social;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决接口
 */
public interface SocialService {

    /**
     * 获取列表
     * @param social
     * @return
     */
    List<Social> getList(Social social);

    /**
     * 获取系统管理信息列表
     * @param social
     * @return
     */
    List<Social> systemList(Social social);

    /**
     *新增系统信息
     * @param social
     * @return
     */
    Integer addSystemSocial(Social social);

    /**
     * 删除社交记录
     * @param socialId
     * @return
     */
    int delMessageSocial(Long[] socialId);

    /**
     * 删除系统社交记录
     * @param id
     * @return
     */
    int delSystemSocial(Long[] id);

    /**
     * 修改系统信息
     * @param social
     * @return
     */
    int updateSysSocial(Social social);

    /**
     * 查询social表格中信息
     * @param socialId
     * @return
     */
    Social getSocialById(Long socialId);

    /**
     * 查询小区与社交表的关联表
     * @param socialId
     * @return
     */
    List<Long> querySocialRangById(Long socialId);
}
