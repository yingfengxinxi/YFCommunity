package com.wr.api.estate.entity.vo.application.info;

import com.wr.api.estate.entity.application.serve.VisitLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 15:29:51
 * @Desc: VisitLogVo
 */
@Data
public class VisitLogVo extends VisitLog implements Serializable {

    private static final long serialVersionUID = 1061968079985552392L;

    /*拜访次数*/
    private Integer visitNum;
    /*动态表名*/
    private String tableName;
}
