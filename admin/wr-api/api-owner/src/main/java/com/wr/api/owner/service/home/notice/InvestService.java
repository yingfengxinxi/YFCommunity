package com.wr.api.owner.service.home.notice;


import com.wr.api.owner.entity.home.notice.Invest;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 社区公示
 */
public interface InvestService {


    /**
     *列表
     * @param invest
     * @return
     */
    List<Invest> getList(Invest invest);




    /**
     * 公示详情
     * @param investId
     * @return
     */
    Invest getInvestById(Long investId);

    /**
     * 查询发布时间最新的公示
     * @return
     */
    Invest selectNews(Invest invest);

    /*
     * 查询列表
     * */
    List<com.wr.remote.estate.info.Invest> selectList(com.wr.remote.estate.info.Invest invest);

    /*
     * 根据物业id查询物业名称
     * */
    com.wr.remote.estate.info.Invest selectEstateNameById(Long estateId);


    /**
     * 根据主键获取对象
     * @param investId 主键
     * @return
     */
    com.wr.remote.estate.info.Invest selectInvestById(Long investId);

    /**
     * 新增招商公示
     * @param invest
     * @return
     */
    Integer insertInvest(com.wr.remote.estate.info.Invest invest);

    /**
     * 修改公示
     * @param invest 公示信息
     * @return
     */
    Integer updateInvest(com.wr.remote.estate.info.Invest invest);

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
    List<com.wr.remote.estate.info.Invest> checkTitleUnique(com.wr.remote.estate.info.Invest invest);

    /*
     * 修改时检查标题唯一性
     * */
    List<com.wr.remote.estate.info.Invest> checkTitleUnique2(com.wr.remote.estate.info.Invest invest);

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

    /**
    * @Author SUNk
    * @Description 增加阅读量
    * @Date 10:04 2024/2/28
    * @Param [investId]
    * @return
    **/
    Integer updateRedNum(Long investId);
}
