package com.wr.remote.govern.autonomy.investigation;


import com.wr.common.core.web.domain.BaseEntity;

import java.util.List;


/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 问题
 */
public class Question extends BaseEntity {

    private static final long serialVersionUID = -4672700920651154726L;
    /**
     *主键
     */
    private Long questionId;
    /**
     *表类型
     */
    private  String  tableType;
    /**
     * 表id
     */
    private Long tableId;
    /**
     *问题类型
     */
    private  Long questionType;
    /**
     * 问题名称
     */
    private  String questionName;
    /**
     * 选项集合
     */
    private List<Item> itemList;
    /**
     * 数量
     */
    private  Integer   paperNum;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }


    public Long getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Long questionType) {
        this.questionType = questionType;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Integer getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(Integer paperNum) {
        this.paperNum = paperNum;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
