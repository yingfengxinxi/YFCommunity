package com.wr.govern.service.impl.autonomy.exposal;


import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.exposal.ExposalCommentMapper;
import com.wr.govern.service.autonomy.exposal.ExposalCommentService;
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
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决实现类
 */
@Service
public class ExposalCommentServiceImpl implements ExposalCommentService {

    @Autowired
    private ExposalCommentMapper exposalCommentMapper;


    /**
     * 根据社交表查询总数
     * @param socialId
     * @return
     */
    @Override
    public Long queryLikeAmount(Long socialId) {
        return exposalCommentMapper.queryLikeAmount(socialId);
    }

    /**
     * 点赞操作
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
        socialLike.setUserType(exposalCommentMapper.queryAccountType(userId));
        //设置用户类型
        return exposalCommentMapper.addLike(socialLike);
    }

    /**
     *新增评论
     * @param socialComment
     * @return
     */
    @Override
    @Transactional
    public Integer addComment(SocialComment socialComment) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        socialComment.setUserId(userId);
        socialComment.setUserType(exposalCommentMapper.queryAccountType(userId));
        socialComment.setCreateBy(sysUser.getUserName());
        return exposalCommentMapper.addComment(socialComment);
    }

    /**
     * 获取系统管理信息列表
     * @param socialId
     * @return
     */
    @Override
    public List<CommentVo> getCommentList(Long socialId) {
        //查询当前社交id人员类型
      String userType= exposalCommentMapper.querySocialInfoUserType(socialId);
        List<CommentVo> commentList=null;
      if(!userType.equals("0")){
          commentList=exposalCommentMapper.getCommentList(socialId);
      }else{
          commentList=exposalCommentMapper.querySysCommentInfo(socialId);
      }
        return commentList;
    }

    /**
     * 撤回
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
        long currentTime =System.currentTimeMillis();//获取当前时间的时间戳
        //计算时差
        long diff=(currentTime-createTime)/1000/60;//获取两个时间相差的分钟
        //判断超过2分钟就不给撤回
        if(diff>=2){
            return 0;
        }
        return exposalCommentMapper.revokeMsg(socialComment);
    }


}
