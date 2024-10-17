package com.wr.remote.govern.security;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.common.core.xss.Xss;
import io.swagger.annotations.ApiModel;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:15:39
 * @Desc: 防控信息实现类
 */
@Data
@ApiModel("防控信息")
public class Prevent extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long preventId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    //小区id
    private Long communityId;

    /**
     * 标题
     */
    @Xss(message = "标题不能包含脚本字符")
    @NotBlank(message = "标题不能为空")
    private String preventTitle;

//    @NotBlank(message = "简介不能为空")
    private String briefIntroduction;

    /**
     * 防控信息类型
     */
    private String preventTypeId;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 内容
     */
    @NotBlank(message = "内容标题不能为空")
    private String content;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

}