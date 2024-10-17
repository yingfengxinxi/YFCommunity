package com.wr.estate.service.social;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import com.wr.remote.govern.autonomy.social.Social;

import java.util.List;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 16:07:23
 * @Desc:社交表
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
