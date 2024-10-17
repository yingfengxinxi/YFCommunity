package com.wr.remote.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/3/2 23:50
 */
@Data
public class PatrolWorkOrderVo {

    //id
    private String id;
    //工单编号
    private String no;
    //最后巡检时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finalInspectionTime;
}
