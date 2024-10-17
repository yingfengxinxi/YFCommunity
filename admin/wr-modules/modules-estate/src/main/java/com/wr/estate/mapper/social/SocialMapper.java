package com.wr.estate.mapper.social;

import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.vo.ApiSocialCommentVo;
import com.wr.remote.domain.vo.ApiSocialVo;
import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.social.SocialRang;

import java.util.ArrayList;
import java.util.List;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 15:56:31
 * @Desc:社交表
 */
public interface SocialMapper {


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
     * 新增
     * @param social
     * @return
     */
    Integer addSystemSocial(Social social);

    /**
     * 将social_id 和community_id  保存到关联表中
     * @param list
     */
    void addSocialRang(ArrayList<SocialRang> list);

    /**
     * 删除社交记录
     * @param socialId
     * @return
     */
    int delMessageSocial(Long[] socialId);

    /**
     * 删除关联表信息
     * @param id
     */
    void delSocialRang(Long[] id);

    /**
     * 修改
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

    /**
     * 根据社交表id删除关联表中的信息
     * @param socialId
     */
    void delSocialRangByIds(Long socialId);
}