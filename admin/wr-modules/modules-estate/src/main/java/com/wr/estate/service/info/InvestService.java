package com.wr.estate.service.info;

import com.wr.remote.estate.info.Invest;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-13 09:50:22
 * @Desc:
 */
public interface InvestService {

  /*
   * 查询列表
   * */
  List<Invest> selectList(Invest invest);

  /*
   * 根据物业id查询物业名称
   * */
  Invest selectEstateNameById(Long estateId);


  /**
   * 根据主键获取对象
   * @param investId 主键
   * @return
   */
  Invest selectInvestById(Long investId);

  /**
   * 新增招商公示
   * @param invest
   * @return
   */
  Integer insertInvest(Invest invest);

  /**
   * 修改公示
   * @param invest 公示信息
   * @return
   */
  Integer updateInvest(Invest invest);

  /**
   * 修改公示状态
   * @param investId 主键
   * @return
   */
  Integer updateStatus(Long investId);

  /**
   * 校验标题唯一性
   *  banner
   * @param
   * @return
   */
  List<Invest> checkTitleUnique(Invest invest);

  /*
   * 修改时检查标题唯一性
   * */
  List<Invest> checkTitleUnique2(Invest invest);

  /**
   * 修改公示状态
   * @param investId 主键
   * @return
   */
  Integer updateStatus2(Long investId);

  /**
   * 删除公示
   * @param investIds 主键
   * @return
   */
  Integer deleteInvestByIds(Long[] investIds);
}
