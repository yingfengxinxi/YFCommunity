package com.wr.api.base.mapper;

import com.wr.remote.domain.TableColumn;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:15:07
 * @Desc:
 */
public interface TableColumnMapper {
    /**
     * 根据列值查询id
     * @param tableColumn
     *  tableName
     *  idField
     *  delFlag
     *  filed
     *  fieldValue
     * @return java.lang.Long
     */
    String selectFiledId(TableColumn tableColumn);
}
