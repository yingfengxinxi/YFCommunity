package com.wr.api.owner.entity.home.activity.vo;

import com.wr.api.owner.entity.home.activity.ApiActSupply;
import com.wr.api.owner.entity.home.activity.ApiActivity;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022-11-16 15:18:42
 * @Desc: 社区活动vo
 */
public class apiActivityVo extends ApiActivity {


    private static final long serialVersionUID = 3611521165379178520L;




    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 物资 一对多
     * @return
     */
    private List<ApiActSupply> actSupplyList;


    public List<ApiActSupply> getActSupplyList() {
        return actSupplyList;
    }

    public void setActSupplyList(List<ApiActSupply> actSupplyList) {
        this.actSupplyList = actSupplyList;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
