package com.wr.system.service;

import com.wr.remote.domain.TableColumn;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:13:53
 * @Desc:
 */
public interface TableColumnService {
    /**
     * 根据列值查询id
     * @Author: RainCity
     * @Date: 2022/8/23 16:16
     * @param tableColumn
     *  tableName
     *  idField
     *  delFlag
     *  filed
     *  fieldValue
     * @return java.lang.String
     */
    String selectFiledId(TableColumn tableColumn);
}
