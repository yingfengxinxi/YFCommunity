package com.wr.api.owner.entity.vo.location;

import com.wr.api.owner.entity.home.location.ApiOrderAssist;
import lombok.Data;

import java.io.Serializable;

/**
 * 工单协助人员表扩展
 * @Author: lvzy
 * @Date: 2022-12-03 17:41:20
 * @Desc:工单协助人员表扩展
 */
@Data
public class ApiOrderAssistVo extends ApiOrderAssist implements Serializable {

    private static final long serialVersionUID = 5113329327972842264L;

    /**
     * 协助人手机号
     */
    private String assistPhone;

    /**
     * 协助人名称
     */
    private String assistName;
}
