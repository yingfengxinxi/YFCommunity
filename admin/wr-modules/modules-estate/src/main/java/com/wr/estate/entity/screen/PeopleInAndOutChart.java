package com.wr.estate.entity.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-10-16 09:17:29
 * @Desc:
 */
public class PeopleInAndOutChart {

    private static final long serialVersionUID = -8949534726788014138L;

    /**
     * 进门
     */

    private String inNum;

    /**
     * 出门
     */
    private String outNum;

    public String getInNum() {
        return inNum;
    }

    public void setInNum(String inNum) {
        this.inNum = inNum;
    }

    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }

    @Override
    public String toString() {
        return "PeopleInAndOutChart{" +
                "inNum=" + inNum +
                ", outNum=" + outNum +
                '}';
    }
}
