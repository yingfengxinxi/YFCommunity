package com.wr.estate.service.social.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.social.SocialCommentMapper;
import com.wr.estate.service.social.SocialCommentService;
import com.wr.remote.domain.SysUser;
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
public class SocialCommentServiceImpl implements SocialCommentService {


    @Autowired
    private SocialCommentMapper commentMapper;


    /**
     * 根据社交表查询总数
     *
     * @param socialId
     * @return
     */
    @Override
    public Long queryLikeAmount(Long socialId) {
        return commentMapper.queryLikeAmount(socialId);
    }

    /**
     * 点赞操作
     *
     * @param socialId
     * @return
     */
    @Override
    @Transactional
    public Integer addLike(Long socialId) {
        //创建实体类， 然后通过实体类
        SocialLike socialLike = new SocialLike();
        socialLike.setSocialId(socialId);

        //获取当前登录用户的id
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        socialLike.setUserId(userId);
        socialLike.setUserType(commentMapper.queryAccountType(userId));
        //设置用户类型
        return commentMapper.addLike(socialLike);
    }

    /**
     * 新增评论
     *
     * @param socialComment
     * @return
     */
    @Override
    @Transactional
    public Integer addComment(SocialComment socialComment) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        socialComment.setUserId(userId);
        socialComment.setUserType(commentMapper.queryAccountType(userId));
        socialComment.setCreateBy(sysUser.getUserName());
        return commentMapper.addComment(socialComment);
    }

    /**
     * 获取系统管理信息列表
     *
     * @param socialId
     * @return
     */
    @Override
    public List<CommentVo> getCommentList(Long socialId) {
        //查询当前社交id人员类型
        String userType = commentMapper.querySocialInfoUserType(socialId);
        List<CommentVo> commentList = null;
        if (!"0".equals(userType)) {
            //发布人类型：0=系统[物业]，1=业主，2=租客
            commentList = commentMapper.getCommentList(socialId);
        } else {
            commentList = commentMapper.querySysCommentInfo(socialId);
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
     * @param socialComment
     * @return
     */
    @Override
    public Integer revokeMsg(SocialComment socialComment) {
        //评论时间和当前时间进行比较， 如果超过2分钟则不给删除， 若没有超过两分钟可撤回

        //获取该评论的创建时间的毫秒值
        Date Time = socialComment.getCreateTime();
        long createTime = Time.getTime();
        //获取本地时间的毫秒值
        long currentTime = System.currentTimeMillis();//获取当前时间的时间戳
        //计算时差
        long diff = (currentTime - createTime) / 1000 / 60;//获取两个时间相差的分钟
        //判断超过2分钟就不给撤回
        if (diff >= 2) {
            return 0;
        }
        return commentMapper.revokeMsg(socialComment);
    }


    //根据评论id获取该评论的创建时间。(如果前端可以传送createTime字段，则不用查询)

}
