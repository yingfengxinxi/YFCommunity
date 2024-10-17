package com.wr.api.owner.service.estateapi.featured.impl;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.mapper.estateapi.featured.FeaturedSectionMapper;
import com.wr.api.owner.service.estateapi.featured.FeaturedSectionService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.govern.autonomy.featured.FeaturedCommentVO;
import com.wr.remote.govern.autonomy.featured.FeaturedLikeVO;
import com.wr.remote.govern.autonomy.featured.FeaturedRang;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
@Service
public class FeaturedSectionServiceImpl  implements FeaturedSectionService {
    @Resource
    private FeaturedSectionMapper featuredSectionMapper;


    /**
     * 获取列表
     * @param featuredSection
     * @return
     */
    @Override
    public List<FeaturedSection> getList(FeaturedSection featuredSection) {
        List<FeaturedSection> listSection = featuredSectionMapper.findListSection( featuredSection );
        if(CollUtil.isNotEmpty( listSection )){
            for (FeaturedSection section : listSection) {
                section.setComentount( featuredSectionMapper.selectCountFeaturedComment( section.getFeaturedId() ) );
//                List<FeaturedCommentVO> featuredCommentVOS = featuredSectionMapper.selectFeaturedCommentList( section );
//                if(CollUtil.isNotEmpty( featuredCommentVOS )){
//                    for (FeaturedCommentVO vo : featuredCommentVOS) {
//                        List<FeaturedCommentVO> featuredCommentVOS1 = featuredSectionMapper.selectNextFeaturedCommentList( vo.getCommentId() );
//                        if(CollUtil.isNotEmpty( featuredCommentVOS1 )){
//                            vo.setFeaturedCommentVOS( featuredCommentVOS1 );
//                        }
//                    }
//                }
//                section.setCommentList( featuredCommentVOS );
            }
        }

        return listSection;
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
    public Integer saveSection(FeaturedSection featuredSection) {
        //保存特色板块表
        int i= featuredSectionMapper.addSystemFeatured(featuredSection);
        //保存关联表
        i = featuredSectionMapper.saveFeaturedRang( featuredSection );
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
     * @param featuredId
     * @return
     */
    @Override
    public FeaturedSection getFeaturedById(Long featuredId) {

        FeaturedSection featuredById = featuredSectionMapper.detilFeaturedSection( featuredId );
        featuredById.setComentount( featuredSectionMapper.selectCountFeaturedComment( featuredId ) );
        List<FeaturedCommentVO> featuredCommentVOS = featuredSectionMapper.selectFeaturedCommentList( featuredById );
        if(CollUtil.isNotEmpty( featuredCommentVOS )){
            for (FeaturedCommentVO vo : featuredCommentVOS) {
                List<FeaturedCommentVO> featuredCommentVOS1 = featuredSectionMapper.selectNextFeaturedCommentList( vo.getCommentId() );
                if(CollUtil.isNotEmpty( featuredCommentVOS1 )){
                    vo.setFeaturedCommentVOS( featuredCommentVOS1 );
                }
            }
        }
        featuredById.setCommentList( featuredCommentVOS );
        return featuredById;
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

    @Override
    @Transactional
    public AjaxResult likeFeatured(FeaturedLikeVO featuredLikeVO) {
        //判断是点赞还是取消点赞
        if(StringUtils.equals( featuredLikeVO.getPraise(),"0" )){
            Integer integer = featuredSectionMapper.checkFeaturedLike( featuredLikeVO );
            if(integer == null || integer > 0){
                throw new ServiceException("不可重复点赞");
            }
            return AjaxResult.success(featuredSectionMapper.saveFeaturedLike( featuredLikeVO ));
        }else {
            return AjaxResult.success(featuredSectionMapper.removeFeaturedLike( featuredLikeVO ));
        }
    }

    @Override
    @Transactional
    public AjaxResult commentFeatured(FeaturedCommentVO featuredCommentVO) {
        featuredSectionMapper.saveComment( featuredCommentVO );
        FeaturedCommentVO commentByPrimaryKey = featuredSectionMapper.findCommentByPrimaryKey( featuredCommentVO.getCommentId() );
        return AjaxResult.success(commentByPrimaryKey);
    }
}
