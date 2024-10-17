package com.wr.remote.estate.info.vo;

import com.wr.remote.domain.Estate;
import com.wr.remote.estate.info.Bulletin;
import lombok.Data;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-10 19:15:35
 * @Desc: 物业通告视图类
 */
public class BulletinVO extends Bulletin {

    private static final long serialVersionUID = 1L;

    /**
     * 物业集合
     */
    private List<Estate> estateList;

    /**
     * 物业id集合
     */
    private List<Long> estateIds;

    public List<Estate> getEstateList() {
        return estateList;
    }

    public void setEstateList(List<Estate> estateList) {
        this.estateList = estateList;
    }

    public List<Long> getEstateIds() {
        return estateIds;
    }

    public void setEstateIds(List<Long> estateIds) {
        this.estateIds = estateIds;
    }
}
