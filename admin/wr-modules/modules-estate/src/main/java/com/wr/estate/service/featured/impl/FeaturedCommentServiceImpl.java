package com.wr.estate.service.featured.impl;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.featured.FeaturedCommentMapper;
import com.wr.estate.service.featured.FeaturedCommentService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.featured.FeaturedComment;
import com.wr.remote.govern.autonomy.featured.FeaturedLike;
import com.wr.remote.govern.autonomy.social.SocialComment;
import com.wr.remote.govern.autonomy.social.SocialLike;
import com.wr.remote.govern.autonomy.social.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/28 22:20
 */
@Service
public class FeaturedCommentServiceImpl  implements FeaturedCommentService {

    @Autowired
    private FeaturedCommentMapper commentMapper;

    /**
     * 根据社交表查询总数
     *
     * @param socialId
     * @return
     */
    @Override
    public Long queryLikeAmount(Long featuredId) {
        return commentMapper.queryLikeAmount(featuredId);
    }

    /**
     * 点赞操作
     *
     * @param featuredId
     * @return
     */
    @Override
    @Transactional
    public Integer addLike(Long featuredId) {
        //创建实体类， 然后通过实体类
        FeaturedLike featuredLike = new FeaturedLike();
        featuredLike.setFeaturedId(featuredId);

        //获取当前登录用户的id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        featuredLike.setUserId(userId);
        featuredLike.setUserType(commentMapper.queryAccountType(userId));
        //设置用户类型
        return commentMapper.addLike(featuredLike);
    }

    /**
     * 新增评论
     *
     * @param featuredComment
     * @return
     */
    @Override
    @Transactional
    public Integer addComment(FeaturedComment featuredComment) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        featuredComment.setUserId(userId);
        featuredComment.setUserType(commentMapper.queryAccountType(userId));
        featuredComment.setCreateBy(sysUser.getUserName());
        return commentMapper.addComment(featuredComment);
    }

    /**
     * 获取系统管理信息列表
     *
     * @param featuredId
     * @return
     */
    @Override
    public List<CommentVo> getCommentList(Long featuredId) {
        //查询当前社交id人员类型
        String userType = commentMapper.queryFeaturedInfoUserType(featuredId);
        List<CommentVo> commentList = null;
        if (!"0".equals(userType)) {
            commentList = commentMapper.getCommentList(featuredId);
        } else {
            commentList = commentMapper.querySysCommentInfo(featuredId);
        }

        if (CollectionUtils.isNotEmpty(commentList)) {
            commentList.stream().forEach(commentVo -> {
                //判断是否可以撤回消息
                //获取该评论的创建时间的毫秒值
                Date Time = commentVo.getCreateTime();
                long createTime = Time.getTime();
                //获取本地时间的毫秒值
                long currentTime = System.currentTimeMillis();//获取当前时间的时间戳
                //计算时差
                long diff = (currentTime - createTime) / 1000 / 60;//获取两个时间相差的分钟
                commentVo.setFlag("0");
                //判断超过2分钟就不给撤回
                if (diff >= 2) {
                    commentVo.setFlag("1");
                }
            });
        }
        return commentList;
    }

    /**
     * 撤回
     *
     * @param featuredComment
     * @return
     */
    @Override
    public Integer revokeMsg(FeaturedComment featuredComment) {
        //评论时间和当前时间进行比较， 如果超过2分钟则不给删除， 若没有超过两分钟可撤回

        //获取该评论的创建时间的毫秒值
        Date Time = featuredComment.getCreateTime();
        long createTime = Time.getTime();
        //获取本地时间的毫秒值
        long currentTime = System.currentTimeMillis();//获取当前时间的时间戳
        //计算时差
        long diff = (currentTime - createTime) / 1000 / 60;//获取两个时间相差的分钟
        //判断超过2分钟就不给撤回
        if (diff >= 2) {
            return 0;
        }
        return commentMapper.revokeMsg(featuredComment);
    }


    //根据评论id获取该评论的创建时间。(如果前端可以传送createTime字段，则不用查询)

}
