package com.wr.estate.mapper.info;

import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.NoticeCommunity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-12 15:57:40
 * @Desc:
 */
@Repository
public interface CommunityNoticeMapper {

    /*
     * 查询列表
     * */
    List<CommunityNotice> selectList(CommunityNotice notice);

    /*
     * 根据物业id查询物业名称
     * */
    CommunityNotice selectEstateNameById(Long estateId);


    /**
     * 根据主键获取对象
     *
     * @param noticeId 主键
     * @return
     */
    CommunityNotice selectNoticeById(Long noticeId);

    /**
     * 批量新增用户角色信息
     *
     * @param communityList 用户角色列表
     * @return 结果
     */
    public int batchCommunityNotice(List<NoticeCommunity> communityList);

    /**
     * 通过id删除头条和社区关联
     *
     * @param noticeId 用户ID
     * @return 结果
     */
    public int deleteCommunityByNoticeId(Long noticeId);

    /**
     * 新增社区公示
     *
     * @param notice
     * @return
     */
    Integer insertNotice(CommunityNotice notice);

    /**
     * 修改公示
     *
     * @param notice 公示信息
     * @return
     */
    Integer updateNotice(CommunityNotice notice);

    /**
     * 修改公示状态
     *
     * @param noticeId 主键
     * @return
     */
    Integer updateStatus(Long noticeId);

    /**
     * 校验标题唯一性
     *
     * @param noticeTitle
     * @return
     */
    Integer checkTitleUnique(@Param("estateId") Long estateId, @Param("noticeId") Long noticeId, @Param("noticeTitle") String noticeTitle, @Param("communityId") Long communityId);

    /**
     * 修改公示状态
     *
     * @param noticeId 主键
     * @return
     */
    Integer updateStatus2(Long noticeId);

    /**
     * 删除公示
     *
     * @param noticeIds 主键
     * @return
     */
    Integer deleteNoticeByIds(Long[] noticeIds);
}
