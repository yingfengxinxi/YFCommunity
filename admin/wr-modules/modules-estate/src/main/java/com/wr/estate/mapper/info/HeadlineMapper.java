package com.wr.estate.mapper.info;

import com.wr.remote.estate.info.Headline;
import com.wr.remote.estate.info.HeadlineCommunity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-11 10:50:42
 * @Desc:
 */
@Repository
public interface HeadlineMapper {

  /*
  * 查询列表
  * */
  List<Headline> selectList(Headline headline);

  /**
   * 根据主键获取对象
   * @param headlineId 主键
   * @return
   */
  Headline selectHeadlineById(Long headlineId);

  /**
   * 校验标题唯一性
   *  banner
   * @param
   * @return
   */
  List<Headline> checkTitleUnique(Headline headline);

  /*
   * 修改时检查标题唯一性
   * */
  List<Headline> checkTitleUnique2(Headline headline);

  /**
   * 批量新增用户角色信息
   *
   * @param communityList 用户角色列表
   * @return 结果
   */
  public int batchHeadlineCommunity(List<HeadlineCommunity> communityList);

  /**
   * 通过id删除头条和社区关联
   *
   * @param headlineId 用户ID
   * @return 结果
   */
  public int deleteCommunityByHeadlineId(Long headlineId);


  /**
   * 新增社区头条
   * @param headline
   * @return
   */
  Integer insertHeadline(Headline headline);

  /**
   * 修改阅读量
   * @param headline
   * @return
   */
  Integer updateReadNum(Headline headline);

  /**
   * 修改头条
   * @param headline 车辆信息
   * @return
   */
  Integer updateHeadline(Headline headline);

  /**
   * 删除头条
   * @param headlineIds 主键
   * @return
   */
  Integer deleteHeadlineByIds(Long[] headlineIds);
}
