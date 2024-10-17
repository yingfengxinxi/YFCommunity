package com.wr.estate.entity.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 14:52:52
 * @Desc: 字典数据
 */
public class DictDataVo {

    private static final long serialVersionUID = -5782957337168721196L;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典标签
     */
    private String dictValue;

    /**
     * 字典类型
     * energy_consumption_type 0水表，1电表
     * meter_reading_type 0消防用水,1办公用水,2绿化用水,3人防用水,4保洁用水,5公共能耗,6办公用电,7充电桩
     */
    private String dictType;

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
}
