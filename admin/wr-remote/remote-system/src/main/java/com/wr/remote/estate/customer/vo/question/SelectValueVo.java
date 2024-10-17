package com.wr.remote.estate.customer.vo.question;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: ZHONGHUI
 * @Date: 2021-08-27 08:26:36
 * @Desc: 问卷调查选项映射实体类
 */
public class SelectValueVo extends BaseEntity {

    private static final long serialVersionUID = 2131032852143492866L;

    /**选项*/
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SelectValueVo{" +
                "value='" + value + '\'' +
                '}';
    }
}
