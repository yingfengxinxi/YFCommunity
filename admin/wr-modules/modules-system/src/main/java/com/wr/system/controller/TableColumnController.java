package com.wr.system.controller;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.TableColumn;
import com.wr.system.service.TableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:06:06
 * @Desc:
 */
@RestController
@RequestMapping("/sys_field")
public class TableColumnController {
    @Autowired
    private TableColumnService tableColumnService;

    /**
     * 查询主键值
     */
    @GetMapping("/id_value")
    public R<String> getPrimaryKey(TableColumn tableColumn) {
        String id = tableColumnService.selectFiledId(tableColumn);
        return R.ok(id);
    }
}
