package com.wr.govern.service.impl.autonomy.exposal;


import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.exposal.ExposalMapper;
import com.wr.govern.service.autonomy.exposal.ExposalService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.social.SocialRang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决实现类
 */
@Service
public class ExposalServiceImpl implements ExposalService {

    @Autowired
    private ExposalMapper exposalMapper;


    /**
     * 获取列表
     * @param social
     * @return
     */
    @Override
    public List<Social> getList(Social social) {
        social.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return exposalMapper.getList(social);
    }

    /**
     * 获取系统管理信息列表
     * @param social
     * @return
     */
    @Override
    public List<Social> systemList(Social social) {
        social.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return exposalMapper.systemList(social);
    }

    /**
     * 新增
     * @param social
     * @return
     */
    @Override
    @Transactional
    public Integer addSystemSocial(Social social) {
        //1.设置物业id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        social.setEstateId(sysUser.getEstateId());
        //2.设置发布人id
        social.setUserId(sysUser.getUserId());
        //3.设置发布人姓名
        social.setCreateBy(sysUser.getUserName());
        //4.信息存储到bus_social表格中
        System.out.println(social);
       int i= exposalMapper.addSystemSocial(social);
        //5.讲数据存储到关联表中
        addSocialRang(social);
        return i;
    }



    /**
     * 将social_id 和community_id  保存到关联表中
     * @param social
     */
    @Transactional
    public void addSocialRang(Social social){
        //1.获取小区数组
        Long[] communityIds = social.getCommunityIds();
        //2.判断非空，然后进行生成集合
        ArrayList<SocialRang> list = new ArrayList<SocialRang>();
        if(StringUtils.isNotEmpty(communityIds)){
            for (Long communityId : communityIds) {
                SocialRang socialRang = new SocialRang();
                socialRang.setSocialId(social.getSocialId());
                socialRang.setCommunityId(communityId);
                list.add(socialRang);
            }
            exposalMapper.addSocialRang(list);
        }
    }


    /**
     *删除信息社交记录
     * @param socialId
     * @return
     */
    @Override
    @Transactional
    public int delMessageSocial(Long[] socialId) {
        return exposalMapper.delMessageSocial(socialId);
    }

    /**
     * 删除系统社交记录
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int delSystemSocial(Long[] id) {
      //删除关联表信息
        exposalMapper.delSocialRang(id);
        return   exposalMapper.delMessageSocial(id);
    }

    /**
     * 修改系统信息
     * @param social
     * @return
     */
    @Override
    @Transactional
    public int updateSysSocial(Social social) {
        //设置修改人信息
        social.setUpdateBy(SecurityUtils.getUsername());
        //1.先将关联表中指定id的小区信息全部删除
        exposalMapper.delSocialRangByIds(social.getSocialId());
        //2.然后新增即可。
        addSocialRang(social);
        return exposalMapper.updateSysSocial(social);
    }

    /**
     * 查询social表格中信息
     * @param socialId
     * @return
     */
    @Override
    public Social getSocialById(Long socialId) {
        return exposalMapper.getSocialById(socialId);
    }

    /**
     * 查询小区与社交表的关联表
     * @param socialId
     * @return
     */
    @Override
    public List<Long> querySocialRangById(Long socialId) {
        return exposalMapper.querySocialRangById(socialId);
    }


}
