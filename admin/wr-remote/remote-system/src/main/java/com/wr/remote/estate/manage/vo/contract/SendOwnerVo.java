package com.wr.remote.estate.manage.vo.contract;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-10-14 16:46:32
 * @Desc: 业主Vo
 */
@Data
public class SendOwnerVo extends BaseEntity {
    private static final long serialVersionUID = 3105003847010002784L;
    /**
     * 业主id
     */
    private Long ownerId;
    private Long communityId;
    private Long estateId;
    /**
     * 手机号
     */
    private String ownerPhone;
    /**
     * 业主姓名
     */
    private String ownerName;
    /**
     * 身份证证号
     */
    private String cardNo;
    //物业名称
    private String estateName;
    //短信key
    private String accessKeyId;
    //短信秘钥
    private String accessKeySecret;

    //短信签名
    private String signName;

    //短信模板code
    private String templateCode;
    //短信模板表id
    private String sendSmsId;
    private Long accountId;
    //
    private String userType;
}
