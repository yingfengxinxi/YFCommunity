package com.wr.system.business.domain.screen;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: GuanYu
 * @Date: 2021-10-09 17:09:06
 * @Desc: 社区人流量统计
 */
public class PeopleVisit {

    private static final long serialVersionUID = -5782957337168721196L;

    private int hourNum;

    private int passNum;

    public int getHourNum() {
        return hourNum;
    }

    public void setHourNum(int hourNum) {
        this.hourNum = hourNum;
    }

    public int getPassNum() {
        return passNum;
    }

    public void setPassNum(int passNum) {
        this.passNum = passNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("hourNum", hourNum)
                .append("passNum", passNum)
                .toString();
    }
}
