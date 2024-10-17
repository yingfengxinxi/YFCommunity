package com.wr.estate.service.customer.goods;

import com.wr.remote.estate.customer.goods.Classify;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 13:38:08
 * @Desc: 物品分类表服务接口
 */
public interface ClassifyService {

    /**
     * 查询Classify列表
     *
     * @param classify 筛选条件
     * @return 查询结果
     */
    List<Classify> selectClassifyList(Classify classify);

    /**
     * 根据id查询单条数据
     *
     * @param classifyId 主键
     * @return 实例对象
     */
    Classify getClassifyById(Long classifyId);

    /**
     * 新增数据
     *
     * @param classify 实例对象
     * @return 影响行数
     */
    int insertClassify(Classify classify);

    /**
     * 修改数据
     *
     * @param classify 实例对象
     * @return 影响行数
     */
    int updateClassify(Classify classify);


    /**
    * 批量删除
    *
    * @param classifyIds 需要删除的数据主键集合
    * @return 影响行数
    */
    int deleteClassifyByIds(Long[] classifyIds);

    /**
     * 查询当前登录人所在物业id
     * @return
     */
    Long getLoginUserEstateId();
}
