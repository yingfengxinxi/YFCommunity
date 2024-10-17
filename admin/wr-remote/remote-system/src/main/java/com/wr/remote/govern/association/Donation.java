package com.wr.remote.govern.association;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.common.core.xss.Xss;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-28 09:30:24
 * @Desc: 业主捐赠实现类
 */
public class Donation extends BaseEntity {
    /**
     * 主键
     */
    private Long donationId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 标题
     */
    @Xss(message = "标题不能包含脚本字符")
    @NotBlank(message = "标题不能为空")
    @Size(min = 0, max = 50, message = "标题不能超过50个字符")
    private String donationTitle;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    @Size(min = 3, max = 500, message = "内容不能超过500个字符")
    private String content;


    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Donation() {
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public String getDonationTitle() {
        return donationTitle;
    }

    public void setDonationTitle(String donationTitle) {
        this.donationTitle = donationTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
