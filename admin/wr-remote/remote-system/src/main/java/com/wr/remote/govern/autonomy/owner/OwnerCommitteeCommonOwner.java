package com.wr.remote.govern.autonomy.owner;

import com.wr.remote.estate.manage.contract.Owner;

/**
 * @Author: liubei
 * @Date: 2022/10/12 14:46
 * @Desc: 投票业主关联
 */
public class OwnerCommitteeCommonOwner extends Owner {

  private static final long serialVersionUID = -4462905989090807870L;
  /**
   *主键
   */
  private Long ownerId;
  /**
   *小区id，表bus_community
   */
  private Long committeeId;


  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public Long getCommitteeId() {
    return committeeId;
  }

  public void setCommitteeId(Long committeeId) {
    this.committeeId = committeeId;
  }

  @Override
  public String toString() {
    return "BusCommitteeOwner{" +
            "ownerId=" + ownerId +
            ", committeeId=" + committeeId +
            '}';
  }
}
