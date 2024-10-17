package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/11/1
 * @Description:
 * @FileName: AliPayEntity
 * @History:
 */
public class AliPayEntity extends BaseEntity {

    private Long alipayId;
    private Long communityId;
    private String appId;
    private String publicKey;
    private String privateKey;
    private String aliStatus;

    private String alipayName;

    private String communityName;

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(Long alipayId) {
        this.alipayId = alipayId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAliStatus() {
        return aliStatus;
    }

    public void setAliStatus(String aliStatus) {
        this.aliStatus = aliStatus;
    }
}
