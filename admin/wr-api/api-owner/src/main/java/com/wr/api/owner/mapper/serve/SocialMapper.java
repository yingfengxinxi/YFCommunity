package com.wr.api.owner.mapper.serve;

import com.wr.remote.domain.ApiSocial;
import com.wr.remote.domain.vo.ApiSocialCommentVo;
import com.wr.remote.domain.vo.ApiSocialVo;

import java.util.List;

/**
 * 社交表
 * @Author: lvzy
 * @Date: 2022-11-16 15:56:31
 * @Desc:社交表
 */
public interface SocialMapper {

    /**
     * delete by primary key
     * @param socialId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long socialId);

    /**
     * insert record to table selective
     * @param apiSocial the record
     * @return insert count
     */
    int insertSelective(ApiSocial apiSocial);
    int insertRang(ApiSocial apiSocial);

    /**
     * select by primary key
     * @param socialId primary key
     * @return object by primary key
     */
    ApiSocial selectByPrimaryKey(Long socialId);

    /**
     * update record selective
     * @param apiSocial the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiSocial apiSocial);

    /**
     * 分页
     * @param estateId
     * @return
     */
    List<ApiSocialVo> listPage(ApiSocialVo apiSocialVo);

    /**
     * 详情
     * @param socialId
     * @return
     */
    ApiSocialVo detail(Long socialId);

    /**
     * 分页List拆分 (子list计算total不可用动态SQL)
     * @param socialId
     * @return
     */
    List<ApiSocialCommentVo> selectCommentList(Long socialId);

    /**
    * @Author SUNk
    * @Description 查询子列表
    * @Date 18:22 2024/3/18
    * @Param [commentId]
    * @return java.util.List<com.wr.remote.domain.vo.ApiSocialCommentVo>
    **/
    List<ApiSocialCommentVo> selectNextCommentList(Long commentId);
    /**
    * @Author SUNk
    * @Description 查询评论总数
    * @Date 18:35 2024/3/18
    * @Param [socialId]
    * @return java.lang.Integer
    **/
    Integer selectCountComment(Long socialId);

    /**
    * @Author SUNk
    * @Description 社交详情查询
    * @Date 15:53 2024/3/19
    * @Param [socialId]
    * @return com.wr.remote.domain.vo.ApiSocialVo
    **/
    ApiSocialVo detilSocial(Long socialId);
}