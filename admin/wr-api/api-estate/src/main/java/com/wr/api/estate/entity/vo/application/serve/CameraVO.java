package com.wr.api.estate.entity.vo.application.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 20:00:18
 * @Desc: 视频监视点
 */
@Data
public class CameraVO extends BaseEntity {

    /**
     * 资源名称
     */
    private String cameraName;


    /**
     * 设备唯一编码
     */
    private String indexCode;
    //状态
    private Integer online;

    /**
     * 小区id
     */
    private Long communityId;
}
