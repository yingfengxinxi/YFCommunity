package com.wr.remote.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/2/23 16:39
 */
@Data
public class BusVoteOwnerVo {

    //业主手机号
    @TableField(exist = false)
    private String ownerPhone;
    //业主姓名
    @TableField(exist = false)
    private String ownerName;
    //业主身份证号
    @TableField(exist = false)
    private String ownerCarNo;
}
