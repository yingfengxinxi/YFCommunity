package com.wr.system.business.domain;

import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.*;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 社区信息管理实体类Vo
 *
 * @author DIANWEI
 * @date 2022/9/21 15:15
 */
@Data
public class CommunityVo extends Community {

    private static final long serialVersionUID = 5685910115771400538L;
    /**
     * 所属物业
     */
    private String estateName;
    /**
     * 子社区
     */
    private List<CommunityVo> children = Lists.newArrayList();
    /**
     * 社区类型
     **/
    private String communityType;
    /**
     * 农行配置信息
     **/
    private CommunityAbc communityAbc;
    /**
     * 支付宝配置信息
     **/
    private CommunityAli communityAli;
    /**
     * 海康配置信息
     **/
    private CommunityHik communityHik;
    /**
     * 微信配置信息
     **/
    private CommunityWechat communityWechat;
}
