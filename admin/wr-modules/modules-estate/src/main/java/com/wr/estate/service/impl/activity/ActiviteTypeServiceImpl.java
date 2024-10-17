package com.wr.estate.service.impl.activity;

import com.wr.estate.mapper.activity.ActiviteTypeMapper;
import com.wr.estate.service.activity.ActiviteTypeService;
import com.wr.remote.estate.activity.ActiviteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActiviteTypeServiceImpl implements ActiviteTypeService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ActiviteTypeMapper activiteTypeMapper;


    @Override
    public List<ActiviteType> selectList(ActiviteType activiteType) {
        return activiteTypeMapper.selectList(activiteType);
    }

    @Override
    @Transactional(readOnly = false)
    public int insertActivite(ActiviteType activiteType) {
        return activiteTypeMapper.insertActivite(activiteType);
    }

    @Override
    @Transactional(readOnly = false)
    public int updateActiviteById(ActiviteType activiteType) {
        return activiteTypeMapper.updateActiviteById(activiteType);
    }

    @Override
    public ActiviteType selectInfoById(Long typeId) {
        return activiteTypeMapper.selectInfoById(typeId);
    }

    @Override
    @Transactional(readOnly = false)
    public int removeActiviteById(Long[] ids) {
        for (Long id : ids) {
            activiteTypeMapper.removeActiviteById(id);
        }
        return 1;
    }

    @Override
    public int selectTypeName(String typeName,Long communityId) {
        return activiteTypeMapper.selectTypeName(typeName,communityId);
    }
}
