package com.wr.remote.estate.customer.vo.question;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * @ClassName:com.wr.property.entity.vo.QuestionVo
 * @Description 问卷调查题目选项内容对映实体类
 * Created by Yu Jin on 2021-10-29 10:40:53
 */
public class QuestionVo extends BaseEntity {


    private static final long serialVersionUID = -1727915456134942274L;

    /**题目*/
    private String title;

    /**选项*/
    private List<SelectValueVo> itemValue;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SelectValueVo> getItemValue() {
        return itemValue;
    }

    public void setItemValue(List<SelectValueVo> itemValue) {
        this.itemValue = itemValue;
    }

    @Override
    public String toString() {
        return "QuestionVo{" +
                "title='" + title + '\'' +
                ", itemValue=" +  itemValue +
                '}';
    }
}
