package com.wr.remote.govern.autonomy.decide.vo;

import com.wr.remote.govern.autonomy.decide.Decide;

/**
 * @Author: lusu
 * @Date: 2022-10-26 15:54:13
 * @Desc: 表决VO-用于新增时使用
 */
public class decideVo extends Decide {
    private static final long serialVersionUID = -6224652265567980186L;
    /**
     * 标题（选项内容一）
     */
    private String  itemTitleOne;
    /**
     * 标题（选项内容二）
     */
    private String itemTitleTwo;


    public String getItemTitleOne() {
        return itemTitleOne;
    }

    public void setItemTitleOne(String itemTitleOne) {
        this.itemTitleOne = itemTitleOne;
    }

    public String getItemTitleTwo() {
        return itemTitleTwo;
    }

    public void setItemTitleTwo(String itemTitleTwo) {
        this.itemTitleTwo = itemTitleTwo;
    }
}
