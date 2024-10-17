package com.wr.api.base.service.impl;

import com.wr.api.base.mapper.TableColumnMapper;
import com.wr.api.base.service.TableColumnService;
import com.wr.remote.domain.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:14:24
 * @Desc:
 */
@Service
public class TableColumnServiceImpl implements TableColumnService {
    @Autowired
    private TableColumnMapper tableColumnMapper;

    @Override
    public String selectFiledId(TableColumn tableColumn) {

        return tableColumnMapper.selectFiledId(tableColumn);
    }
}
