package com.wr.estate.entity.screen;


/**
 * @Author: GuanYu
 * @Date: 2021-09-26 15:06:36
 * @Desc:
 */
public class OwnerAge {

    private static final long serialVersionUID = -8949534726788014138L;

    /** 0-10岁男性*/

    private Integer zeroToTenMan;
    /** 11-20男性*/
    private Integer elevenToTwentyMan;
    /** 21-30男性*/
    private Integer twentyOneToThirtyMan;
    /** 31-40男性*/
    private Integer thirtyOnetoFortyMan;
    /** 41-50男性*/
    private Integer fortyOneToFiftyMan;
    /** 51-60男性*/
    private Integer fiftyOneToSixtyMan;
    /** 61-70男性*/
    private Integer sixtyOneToSeventyMan;
    /** 70岁及以上*/
    private Integer overSeventyMan;
    /** 0-10岁男性*/
    private Integer zeroToTenWoman;
    /** 11-20男性*/
    private Integer elevenToTwentyWoman;
    /** 21-30男性*/
    private Integer twentyOneToThirtyWoman;
    /** 31-40男性*/
    private Integer thirtyOnetoFortyWoman;
    /** 41-50男性*/
    private Integer fortyOneToFiftyWoman;
    /** 51-60男性*/
    private Integer fiftyOneToSixtyWoman;
    /** 61-70女性*/
    private Integer sixtyOneToSeventyWoman;
    /** 70岁及以上*/
    private Integer overSeventyWoman;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getZeroToTenMan() {
        return zeroToTenMan;
    }

    public void setZeroToTenMan(Integer zeroToTenMan) {
        this.zeroToTenMan = zeroToTenMan;
    }

    public Integer getElevenToTwentyMan() {
        return elevenToTwentyMan;
    }

    public Integer getSixtyOneToSeventyMan() {
        return sixtyOneToSeventyMan;
    }

    public void setSixtyOneToSeventyMan(Integer sixtyOneToSeventyMan) {
        this.sixtyOneToSeventyMan = sixtyOneToSeventyMan;
    }

    public Integer getSixtyOneToSeventyWoman() {
        return sixtyOneToSeventyWoman;
    }

    public void setSixtyOneToSeventyWoman(Integer sixtyOneToSeventyWoman) {
        this.sixtyOneToSeventyWoman = sixtyOneToSeventyWoman;
    }

    public void setElevenToTwentyMan(Integer elevenToTwentyMan) {
        this.elevenToTwentyMan = elevenToTwentyMan;
    }

    public Integer getTwentyOneToThirtyMan() {
        return twentyOneToThirtyMan;
    }

    public void setTwentyOneToThirtyMan(Integer twentyOneToThirtyMan) {
        this.twentyOneToThirtyMan = twentyOneToThirtyMan;
    }

    public Integer getThirtyOnetoFortyMan() {
        return thirtyOnetoFortyMan;
    }

    public void setThirtyOnetoFortyMan(Integer thirtyOnetoFortyMan) {
        this.thirtyOnetoFortyMan = thirtyOnetoFortyMan;
    }

    public Integer getFortyOneToFiftyMan() {
        return fortyOneToFiftyMan;
    }

    public void setFortyOneToFiftyMan(Integer fortyOneToFiftyMan) {
        this.fortyOneToFiftyMan = fortyOneToFiftyMan;
    }

    public Integer getFiftyOneToSixtyMan() {
        return fiftyOneToSixtyMan;
    }

    public void setFiftyOneToSixtyMan(Integer fiftyOneToSixtyMan) {
        this.fiftyOneToSixtyMan = fiftyOneToSixtyMan;
    }

    public Integer getOverSeventyMan() {
        return overSeventyMan;
    }

    public void setOverSeventyMan(Integer overSeventyMan) {
        this.overSeventyMan = overSeventyMan;
    }

    public Integer getZeroToTenWoman() {
        return zeroToTenWoman;
    }

    public void setZeroToTenWoman(Integer zeroToTenWoman) {
        this.zeroToTenWoman = zeroToTenWoman;
    }

    public Integer getElevenToTwentyWoman() {
        return elevenToTwentyWoman;
    }

    public void setElevenToTwentyWoman(Integer elevenToTwentyWoman) {
        this.elevenToTwentyWoman = elevenToTwentyWoman;
    }

    public Integer getTwentyOneToThirtyWoman() {
        return twentyOneToThirtyWoman;
    }

    public void setTwentyOneToThirtyWoman(Integer twentyOneToThirtyWoman) {
        this.twentyOneToThirtyWoman = twentyOneToThirtyWoman;
    }

    public Integer getThirtyOnetoFortyWoman() {
        return thirtyOnetoFortyWoman;
    }

    public void setThirtyOnetoFortyWoman(Integer thirtyOnetoFortyWoman) {
        this.thirtyOnetoFortyWoman = thirtyOnetoFortyWoman;
    }

    public Integer getFortyOneToFiftyWoman() {
        return fortyOneToFiftyWoman;
    }

    public void setFortyOneToFiftyWoman(Integer fortyOneToFiftyWoman) {
        this.fortyOneToFiftyWoman = fortyOneToFiftyWoman;
    }

    public Integer getFiftyOneToSixtyWoman() {
        return fiftyOneToSixtyWoman;
    }

    public void setFiftyOneToSixtyWoman(Integer fiftyOneToSixtyWoman) {
        this.fiftyOneToSixtyWoman = fiftyOneToSixtyWoman;
    }

    public Integer getOverSeventyWoman() {
        return overSeventyWoman;
    }

    public void setOverSeventyWoman(Integer overSeventyWoman) {
        this.overSeventyWoman = overSeventyWoman;
    }

    @Override
    public String toString() {
        return "OwnerAge{" +
                "zeroToTenMan=" + zeroToTenMan +
                ", elevenToTwentyMan=" + elevenToTwentyMan +
                ", twentyOneToThirtyMan=" + twentyOneToThirtyMan +
                ", thirtyOnetoFortyMan=" + thirtyOnetoFortyMan +
                ", fortyOneToFiftyMan=" + fortyOneToFiftyMan +
                ", fiftyOneToSixtyMan=" + fiftyOneToSixtyMan +
                ", sixtyOneToSeventyMan=" + sixtyOneToSeventyMan +
                ", overSeventyMan=" + overSeventyMan +
                ", zeroToTenWoman=" + zeroToTenWoman +
                ", elevenToTwentyWoman=" + elevenToTwentyWoman +
                ", twentyOneToThirtyWoman=" + twentyOneToThirtyWoman +
                ", thirtyOnetoFortyWoman=" + thirtyOnetoFortyWoman +
                ", fortyOneToFiftyWoman=" + fortyOneToFiftyWoman +
                ", fiftyOneToSixtyWoman=" + fiftyOneToSixtyWoman +
                ", sixtyOneToSeventyWoman=" + sixtyOneToSeventyWoman +
                ", overSeventyWoman=" + overSeventyWoman +
                '}';
    }
}
