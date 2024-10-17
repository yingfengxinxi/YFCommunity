package com.wr.system.business.domain.screen;

import java.io.Serializable;

/**
 * @Author: LiuBei
 * @Date: 2022-01-08 15:19:53
 * @Desc:
 */
public class TableName implements Serializable {
    private static final long serialVersionUID = 8018770259551749225L;
    /**
     * 表名
     * */
    private String tablesName;
    /**
     * 参数名称
     * */
    private String table;

    public String getTablesName() {
        return tablesName;
    }

    public void setTablesName(String tablesName) {
        this.tablesName = tablesName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "TableName{" +
                "tablesName='" + tablesName + '\'' +
                ", table='" + table + '\'' +
                '}';
    }
}
