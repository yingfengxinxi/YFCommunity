package com.wr.remote.govern.security.vo;

import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.govern.security.Bulletin;
import com.wr.remote.govern.security.Clue;

import java.util.Date;
import java.util.List;

/**
 * bus_bulletin
 *
 * @author
 */
public class BulletinVO extends Bulletin {

    /**
     * 线索反馈集合
     */
    private List<Clue> clueList;

    public BulletinVO() {
    }

    public List<Clue> getClueList() {
        return clueList;
    }

    public void setClueList(List<Clue> clueList) {
        this.clueList = clueList;
    }
}