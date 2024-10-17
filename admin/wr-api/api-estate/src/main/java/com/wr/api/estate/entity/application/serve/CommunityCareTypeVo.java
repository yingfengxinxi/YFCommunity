package com.wr.api.estate.entity.application.serve;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:13
 * 社区关怀
 */
@Data
public class CommunityCareTypeVo implements Serializable {

    private Long id;
    //名称
    private String name;
    //背景色
    private String backgroupColor;
}
