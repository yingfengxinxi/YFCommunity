package com.wr.estate.service.featured.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.featured.FeaturedSectionMapper;
import com.wr.estate.service.featured.FeaturedSectionService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.featured.FeaturedRang;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import com.wr.remote.govern.autonomy.social.Social;
import com.wr.remote.govern.autonomy.social.SocialRang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
@Service
public class FeaturedSectionServiceImpl  implements FeaturedSectionService {


    @Autowired
    private FeaturedSectionMapper featuredSectionMapper;


    /**
     * 获取列表
     * @param featuredSection
     * @return
     */
    @Override
    public List<FeaturedSection> getList(FeaturedSection featuredSection) {

        return featuredSectionMapper.getList(featuredSection);
    }

    /**
     * 获取系统管理信息列表
     * @param featuredSection
     * @return
     */
    @Override
    public List<FeaturedSection> systemList(FeaturedSection featuredSection) {
        featuredSection.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return featuredSectionMapper.systemList(featuredSection);
    }

    /**
     * 新增
     * @param featuredSection
     * @return
     */
    @Override
    @Transactional
    public Integer addSystemFeatured(FeaturedSection featuredSection) {
        //1.设置物业id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        featuredSection.setEstateId(sysUser.getEstateId());
        //2.设置发布人id
        featuredSection.setUserId(sysUser.getUserId());
        //3.设置发布人姓名
        featuredSection.setCreateBy(sysUser.getUserName());
        //4.信息存储到bus_social表格中
        System.out.println(featuredSection);
        int i= featuredSectionMapper.addSystemFeatured(featuredSection);
        //5.讲数据存储到关联表中
        addSocialRang(featuredSection);
        return i;
    }



    /**
     * 将social_id 和community_id  保存到关联表中
     * @param featuredSection
     */
    @Transactional
    public void addSocialRang(FeaturedSection featuredSection){
        //1.获取小区数组
        Long[] communityIds = featuredSection.getCommunityIds();
        //2.判断非空，然后进行生成集合
        ArrayList<FeaturedRang> list = new ArrayList<FeaturedRang>();
        if(StringUtils.isNotEmpty(communityIds)){
            for (Long communityId : communityIds) {
                FeaturedRang featuredRang = new FeaturedRang();
                featuredRang.setFeaturedId(featuredSection.getFeaturedId());
                featuredRang.setCommunityId(communityId);
                list.add(featuredRang);
            }
            featuredSectionMapper.addFeaturedRang(list);
        }
    }


    /**
     *删除信息社交记录
     * @param featuredId
     * @return
     */
    @Override
    @Transactional
    public int delMessageFeatured(Long[] featuredId) {
        return featuredSectionMapper.delMessageFeatured(featuredId);
    }

    /**
     * 删除系统社交记录
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int delSystemFeatured(Long[] id) {
        //删除关联表信息
        featuredSectionMapper.delFeaturedRang(id);
        return   featuredSectionMapper.delMessageFeatured(id);
    }

    /**
     * 修改系统信息
     * @param featuredSection
     * @return
     */
    @Override
    @Transactional
    public int updateSysFeatured(FeaturedSection featuredSection) {
        //设置修改人信息
        featuredSection.setUpdateBy(SecurityUtils.getUsername());
        //1.先将关联表中指定id的小区信息全部删除
        featuredSectionMapper.delFeaturedRangByIds(featuredSection.getFeaturedId());
        //2.然后新增即可。
        addSocialRang(featuredSection);
        return featuredSectionMapper.updateSysFeatured(featuredSection);
    }

    /**
     * 查询social表格中信息
     * @param socialId
     * @return
     */
    @Override
    public FeaturedSection getFeaturedById(Long socialId) {
        return featuredSectionMapper.getFeaturedById(socialId);
    }

    /**
     * 查询小区与社交表的关联表
     * @param socialId
     * @return
     */
    @Override
    public List<Long> queryFeaturedRangById(Long socialId) {
        return featuredSectionMapper.queryFeaturedRangById(socialId);
    }


}
