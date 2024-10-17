package com.wr.remote.govern.autonomy.featured;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/2/28 11:10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("特色板块类型")
@TableName(value = "xccj_govern.bus_featured_section_type")
public class FeaturedSectionType extends BaseEntity {

    //	主键
    private String id;
    //物业id
    private Long estateId;
    //	小区id，表xccj_community.bus_community
    private Long communityId;
    //封面
    private String cover;

    //板块名称
    private String name;
    //顺序
    private Integer sort;
    //跳转路径
    private String pageUrl;
    //大图
    private String postersImage;
    //群二维码
    private String groupCode;
    //是否停用0=否1=是
    private String status;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
