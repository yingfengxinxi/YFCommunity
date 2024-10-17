package com.wr.estate.service.manage.contract;


import com.wr.remote.estate.manage.contract.Material;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
public interface MaterialService {

    /**
     * 新增采购信息
     * @param material
     * @return
     */
    Integer addMaterial(Material material);
}
