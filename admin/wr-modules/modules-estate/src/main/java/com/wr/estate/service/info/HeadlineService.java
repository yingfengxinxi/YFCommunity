package com.wr.estate.service.info;

import com.wr.remote.estate.info.Headline;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-11 11:41:25
 * @Desc:
 */
public interface HeadlineService {

  /*
   * 查询列表
   * */
  List<Headline> selectList(Headline headline);

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
   * 修改阅读量
   * @param headlineId
   * @return
   */
  /*Integer updateReadNum(Long headlineId);*/

  /**
   * 根据主键获取对象
   * @param headlineId 主键
   * @return
   */
  Headline selectHeadlineById(Long headlineId);

  /**
   * 新增社区头条
   * @param headline
   * @return
   */
  Integer insertHeadline(Headline headline);

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
