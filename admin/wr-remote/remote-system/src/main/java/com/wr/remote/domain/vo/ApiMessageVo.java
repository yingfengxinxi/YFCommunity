package com.wr.remote.domain.vo;

import com.wr.remote.domain.ApiMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * 党建资讯
 * @Author: lvzy
 * @Date: 2022-11-28 13:51:43
 * @Desc:党建资讯
 */
@Data
public class ApiMessageVo extends ApiMessage implements Serializable {

    private static final long serialVersionUID = -1823133359458156466L;


    /**
     * 组织名称
     */
    private String partyName;

}
