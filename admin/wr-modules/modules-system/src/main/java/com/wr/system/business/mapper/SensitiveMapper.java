package com.wr.system.business.mapper;

import com.wr.remote.domain.Sensitive;

import java.util.List;

/**
 * @Description 敏感词管理mapper层
 * @Date 2022/9/19 11:37+
 * @Author DIANWEI
 **/
public interface SensitiveMapper {

    /**
     * 敏感词管理列表查询
     *
     * @Param 敏感词管理实体类
     **/
    List<Sensitive> getListBusSensitive(Sensitive busSensitive);

    /**
     * 添加敏感词
     * @param busSensitive
     * @return
     */
    Integer insertBusSensitive(Sensitive busSensitive);

    /**
     * 根据id查询数据
     *
     * @param sensitiveId
     * @return
     */
    Sensitive selectSensitiveById(Long sensitiveId);

    /**
     * 修改数据
     *
     * @param busSensitive
     * @return
     */
    Integer updateBusSensitive(Sensitive busSensitive);

    /**
     * 根据ids批量删除
     *
     * @param sensitiveIds
     * @return
     */
    Integer deleteSensitiveByIds(Long[] sensitiveIds);

    /**
     * 根据名称查询
     * @param phrases 词汇名称
     * @return com.wr.remote.domain.Sensitive
     */
    Sensitive checkPhrasesUnique(String phrases);
}
