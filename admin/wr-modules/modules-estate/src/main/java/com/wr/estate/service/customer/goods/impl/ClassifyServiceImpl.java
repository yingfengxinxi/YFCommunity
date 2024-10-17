package com.wr.estate.service.customer.goods.impl;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.customer.goods.ClassifyMapper;
import com.wr.estate.service.customer.goods.ClassifyService;
import com.wr.remote.estate.customer.goods.Classify;
import com.wr.remote.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 13:38:12
 * @Desc: 物品分类表服务实现类
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 查询Classify列表
     *
     * @param classify 筛选条件
     * @return 查询结果
     */
    @Override
    public List<Classify> selectClassifyList(Classify classify){
        return classifyMapper.selectClassifyList(classify);
    }

    /**
     * 根据id查询单条数据
     *
     * @param classifyId 主键
     * @return 实例对象
     */
    @Override
    public Classify getClassifyById(Long classifyId){
        return classifyMapper.getClassifyById(classifyId);
    };

    /**
     * 修改数据
     *
     * @param classify 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(readOnly = false)
    public int updateClassify(Classify classify){
        Long estateId = getLoginUserEstateId();
        classify.setEstateId(estateId);
        classify.setUpdateBy(SecurityUtils.getUsername());
        classifyMapper.updateClassify(classify);
        int exsit = classifyMapper.getIsExsit(classify);
        if (exsit > 1){
            throw new RuntimeException("分类名称：“" + classify.getClassifyName() + "”已存在");
        }
        return 1;
    }

    /**
     * 新增数据
     *
     * @param classify 实例对象
     * @return 影响行数
     */
    @Override
    @Transactional(readOnly = false)
    public int insertClassify(Classify classify){
        Long estateId = getLoginUserEstateId();
        classify.setEstateId(estateId);
        int exsit = classifyMapper.getIsExsit(classify);
        if (exsit > 0){
            throw new RuntimeException("分类名称：“" + classify.getClassifyName() + "”已存在");
        }
        classify.setCreateBy(SecurityUtils.getUsername());
        return classifyMapper.insertClassify(classify);
    }

    /**
    * 批量删除
    *
    * @param classifyIds 需要删除的数据主键集合
    * @return 影响行数
    */
    @Override
    @Transactional(readOnly = false)
    public int deleteClassifyByIds(Long[] classifyIds){
        return classifyMapper.deleteClassifyByIds(classifyIds);
    }

    /**
     * 查询当前登录人所在物业id
     * @return
     */
    @Override
    public Long getLoginUserEstateId(){
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        return sysUser.getEstateId();
    }

}
