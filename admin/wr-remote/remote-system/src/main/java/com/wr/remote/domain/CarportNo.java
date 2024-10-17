package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-15 16:56:53
 * @Desc:
 */
@ApiModel("车位编号")
@Data
public class CarportNo extends BaseEntity {
    private static final long serialVersionUID = 729568269741632601L;

    /**
     * 主键
     */
    private Long carportNoId;
    //小区id
    private Long communityId;
    //车位编号
    @Excel(name = "车位编号")
    private String carportNo;

    @TableLogic
    private String delFlag;
}
