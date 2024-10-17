package com.wr.estate.service.impl.manage.contract;


import com.wr.estate.mapper.manage.contract.MaterialMapper;
import com.wr.estate.service.manage.contract.MaterialService;
import com.wr.remote.estate.manage.contract.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 新增采购信息
     * @param material
     * @return
     */
    @Override
    @Transactional
    public Integer addMaterial(Material material) {
        return materialMapper.addMaterial(material);
    }
}
