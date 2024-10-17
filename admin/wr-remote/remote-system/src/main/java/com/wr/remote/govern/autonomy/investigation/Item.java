package com.wr.remote.govern.autonomy.investigation;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 投票选项值
 */
public class Item extends BaseEntity {

    private static final long serialVersionUID = -8624264779518682472L;
    /**
     *主键
     */
    private Long itemId;
    /**
     *选项名称
     */
    private String itemName;
    /**
     *题目id
     */
    private Long questionId;
    /**
     * 所选人数
     */
    private Long personNum;

    /**
     * 纸质人数
     */
    private Long paperNum;

    public Long getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(Long paperNum) {
        this.paperNum = paperNum;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Long personNum) {
        this.personNum = personNum;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", questionId=" + questionId +
                ", personNum=" + personNum +
                ", paperNum=" + paperNum +
                '}';
    }
}
