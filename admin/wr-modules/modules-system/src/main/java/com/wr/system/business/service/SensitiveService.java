package com.wr.system.business.service;

import com.wr.remote.domain.Sensitive;

import java.util.List;

/**
 * @Description 敏感词管理service层
 * @Date 2022/9/19 11:39
 * @Author DIANWEI
 **/

public interface SensitiveService {
    /**
     * 敏感词管理列表查询
     *
     * @Param 敏感词管理实体类
     **/
    List<Sensitive> getListBusSensitive(Sensitive busSensitive);

    /**
     * 校验名称不唯一
     * @param sensitive
     * @return java.lang.String
     */
    String checkPhrasesUnique(Sensitive sensitive);

    /**
     * 添加敏感词
     *
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
}
