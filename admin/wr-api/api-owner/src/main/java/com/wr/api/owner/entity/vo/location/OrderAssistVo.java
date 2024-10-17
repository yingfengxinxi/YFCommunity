package com.wr.api.owner.entity.vo.location;

import lombok.Data;

/**
 * @Author: lvzy
 * @Date: 2022-12-10 17:19:40
 * @Desc: 工单协助人
 */
@Data
public class OrderAssistVo {
    //协助人
    private String staffName;
    //协助电话
    private String staffPhone;
}
