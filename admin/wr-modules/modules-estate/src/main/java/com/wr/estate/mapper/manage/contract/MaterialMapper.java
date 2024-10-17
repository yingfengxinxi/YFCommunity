package com.wr.estate.mapper.manage.contract;


import com.wr.remote.estate.manage.contract.Material;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
@Mapper
public interface MaterialMapper {


    /**
     * 新增采购信息
     * @param material
     * @return
     */
    Integer addMaterial(Material material);
}
