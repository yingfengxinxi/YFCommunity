package com.wr.api.owner.entity.vo.serve;

import com.wr.api.owner.entity.serve.ApiGoodsOut;
import com.wr.remote.estate.Image;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物品出门表扩展
 * @Author: lvzy
 * @Date: 2022-11-15 11:04:22
 * @Desc:物品出门表扩展
 */
@Data
public class ApiGoodsOutVo extends ApiGoodsOut implements Serializable {

    private static final long serialVersionUID = -7038215363159703116L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 图片组
     */
    private List<Image> imageArr;

    /**
     * 列表物品图片
     */
    private String imageUrl;

    /**
     * 用户名
     */
    private String accountName;

    /**
     * 手机号
     */
    private String loginName;

    /**
     * 出入口名称
     */
    private String passageName;
}
