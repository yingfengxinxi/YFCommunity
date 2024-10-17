package com.wr.api.owner.web.estateapi.featured;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.service.estateapi.featured.FeaturedSectionService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.ApiSocialComment;
import com.wr.remote.domain.Sensitive;
import com.wr.remote.domain.vo.ApiSocialLikeVo;
import com.wr.remote.govern.autonomy.featured.FeaturedComment;
import com.wr.remote.govern.autonomy.featured.FeaturedCommentVO;
import com.wr.remote.govern.autonomy.featured.FeaturedLikeVO;
import com.wr.remote.govern.autonomy.featured.FeaturedSection;
import com.wr.remote.govern.autonomy.social.SocialComment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/2/28 17:27
 */
@RestController
@RequestMapping("/estateapi/featuredSection")
@Api(tags = "业主端-特色板块")
public class FeaturedSectionController extends BaseController {

    @Autowired
    private FeaturedSectionService featuredSectionService;
    @Autowired
    private RedisService redisService;

    /**
     * 获取信息管理列表
     *
     * @param featuredSection
     * @return
     * @throws ParseException
     */
    @PostMapping("/findPagefeaturedSection")
    @ApiOperation("获取特色板块管理话题分页")
    public TableDataInfo findPagefeaturedSection(@RequestBody FeaturedSection featuredSection) {
        PageUtils.startPage(featuredSection.getPageNum(), featuredSection.getPageSize());
        List<FeaturedSection> list = featuredSectionService.getList(featuredSection);
        return getDataTable(list);
    }

    /**
     * 新增特色板块话题
     *
     * @param featuredSection
     * @return
     */
    @PostMapping("/saveSection")
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @ApiOperation("新增特色板块话题")
    public AjaxResult saveSection(@Validated @RequestBody FeaturedSection featuredSection) {
        if(featuredSection == null){
            throw new ServiceException("获取板块发布信息失败，请填写相关信息后进行提交！");
        }

        List<Sensitive> sensitiveList = redisService.getCacheObject( CacheConstants.SENSITIVE);
        if (CollUtil.isNotEmpty( sensitiveList )) {
            List<String> phrasesList = sensitiveList.stream().map(Sensitive::getPhrases).collect( Collectors.toList());
            FeaturedSection socialComment1 = SensitiveUtil.sensitiveFilterOwn(featuredSection, phrasesList);
            if (socialComment1.getContent().contains("**")) {
                return AjaxResult.error("输入内容存在敏感词信息,请重新输入");
            }
        }

        Integer inte = featuredSectionService.saveSection(featuredSection);
        return inte > 0 ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

    /**
     * 根据id查询社交的详情内容
     *
     * @param featuredId
     * @return
     */
    @ApiOperation("根据id查询社交的详情内容")
    @GetMapping("/detilFeaturedSection")
    public AjaxResult detilFeaturedSection(@RequestParam Long featuredId) {

        FeaturedSection featuredSection = featuredSectionService.getFeaturedById(featuredId);
        if (StringUtils.isNotEmpty(featuredSection.getFeaturedUrl())) {
            featuredSection.setFeaturedUrls(Arrays.asList(featuredSection.getFeaturedUrl().split(",")));
        }
        return AjaxResult.success(featuredSection);
    }

    @PostMapping("/likeFeatured")
    @ApiLog(title = "特色板块点赞", businessType = BusinessType.INSERT)
    public AjaxResult likeFeatured(@RequestBody FeaturedLikeVO featuredLikeVO) {
        return featuredSectionService.likeFeatured( featuredLikeVO );
    }

    @PostMapping("/commentFeatured")
    @ApiLog(title = "特色板块社交评论", businessType = BusinessType.INSERT)
    public AjaxResult commentFeatured(@Validated @RequestBody FeaturedCommentVO featuredCommentVO) {
        FeaturedComment featuredComment = new FeaturedComment();
        featuredComment.setContent(featuredCommentVO.getContent());
        List<Sensitive> sensitiveList = redisService.getCacheObject(CacheConstants.SENSITIVE);
        if (CollUtil.isNotEmpty( sensitiveList )) {
            List<String> phrasesList = sensitiveList.stream().map(Sensitive::getPhrases).collect( Collectors.toList());
            FeaturedComment socialComment1 = SensitiveUtil.sensitiveFilterOwn(featuredComment, phrasesList);
            if (socialComment1.getContent().contains("**")) {
                return AjaxResult.error("输入内容存在敏感词信息,请重新输入");
            }
        }

        return featuredSectionService.commentFeatured( featuredCommentVO );
    }
}
