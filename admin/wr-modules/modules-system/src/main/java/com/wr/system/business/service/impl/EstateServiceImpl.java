package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.CustomException;
import com.wr.common.core.utils.StringUtils;
import com.wr.remote.domain.SysDept;
import com.wr.system.business.domain.EstateVo;
import com.wr.system.business.mapper.EstateMapper;
import com.wr.system.business.service.EstateService;
import com.wr.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物业公司详细信息业务层
 *
 * @Date 2022/9/20 10:28
 * @Author DIANWEI
 **/
@Service
public class EstateServiceImpl implements EstateService {

    @Autowired
    private EstateMapper estateMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public List<EstateVo> selectEstateVoList(EstateVo estateVo) {

        return estateMapper.selectEstateVoList(estateVo);
    }

    @Override
    public String checkEstateVoNameUnique(EstateVo estateVo) {
        Long deptId = StringUtils.isNull(estateVo.getDeptId()) ? -1L : estateVo.getDeptId();
        EstateVo info = estateMapper.checkEstateVoNameUnique(estateVo.getSysDept().getDeptName(), estateVo.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertEstateVo(EstateVo estateVo) {
        EstateVo estateVo1 = estateMapper.selectEstateVoById(estateVo.getParentId());
        Integer count = 0;
        if (StringUtils.isNotNull(estateVo1)) {
            // 如果父节点不为正常状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(estateVo1.getSysDept().getStatus())) {
                throw new CustomException("物业停用，不允许新增");
            }
            estateVo.getSysDept().setAncestors(estateVo1.getSysDept().getAncestors() + "," + estateVo1.getSysDept().getDeptId());
            estateVo.getSysDept().setPhone(estateVo.getHotline());
            estateVo.getSysDept().setParentId(estateVo1.getSysDept().getDeptId());
            estateVo.getSysDept().setDeptName(estateVo.getEstateName());
            estateVo.getSysDept().setDeptType("10");
            count = estateMapper.insertDept(estateVo.getSysDept());
            if (count == 0) {
                return null;
            }

            estateVo.setDeptId(estateVo.getSysDept().getDeptId());
            estateVo.setParentId(estateVo1.getEstateId());

            estateVo.setAncestors(estateVo1.getAncestors() + "," + estateVo1.getEstateId());
            count = estateMapper.insertEstateVo(estateVo);
            if (count == 0) {
                return null;
            }
        } else {
            estateVo.getSysDept().setAncestors("0");
            estateVo.getSysDept().setPhone(estateVo.getHotline());
            estateVo.getSysDept().setParentId(0L);
            estateVo.getSysDept().setDeptName(estateVo.getEstateName());
            estateVo.getSysDept().setDeptType("10");
            count = estateMapper.insertDept(estateVo.getSysDept());
            if (count == 0) {
                return null;
            }

            estateVo.setDeptId(estateVo.getSysDept().getDeptId());
            estateVo.setParentId(0L);

            estateVo.setAncestors("0");
            count = estateMapper.insertEstateVo(estateVo);
            if (count == 0) {
                return null;
            }
        }


        return count;
    }

    @Override
    public EstateVo selectEstateVoById(Long estateId) {

        return estateMapper.selectEstateVoById(estateId);
    }

    @Override
    public EstateVo selectEstateVoByName(String name) {

        return estateMapper.selectEstateVoByName(name);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateEstateVo(EstateVo estateVo) {
        EstateVo newParentDept = estateMapper.selectEstateVoById(estateVo.getParentId());
        EstateVo oldDept = estateMapper.selectEstateVoById(estateVo.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            estateVo.setAncestors(newAncestors);
            updateDeptChildren(estateVo.getDeptId(), newAncestors, oldAncestors);
        }
        estateVo.getSysDept().setPhone(estateVo.getHotline());
        estateVo.getSysDept().setDeptName(estateVo.getEstateName());
        int result = deptMapper.updateDept(estateVo.getSysDept());
        if (UserConstants.DEPT_NORMAL.equals(estateVo.getSysDept().getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(estateVo.getSysDept());
        }
        //修改物业信息
        estateVo.setDeptId(estateVo.getSysDept().getDeptId());
        estateMapper.updateEstateVo(estateVo);
        return result;
    }

    @Override
    public Integer updateStatus(EstateVo estateVo) {

        return estateMapper.updateStatus(estateVo);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept) {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        //deptMapper.updateDeptStatus(dept);
    }

    @Override
    public List<EstateVo> selectChildrenDeptById(Long deptId) {

        return estateMapper.selectChildrenDeptById(deptId);
    }

    /**
     * 校验：统一社会信用代码
     *
     * @param estateVo
     * @return
     */
    @Override
    public String checkEstateVoCreditCodeUnique(EstateVo estateVo) {
        //获取记录数
        String creditCode = estateVo.getCreditCode();
        Integer integer = estateMapper.checkEstateVoCreditCodeUnique(creditCode);
        //判断记录数
        if (integer > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验：机构编码
     *
     * @param estateVo
     * @return
     */
    @Override
    public String checkEstateVoDeptCode(EstateVo estateVo) {
        Integer integer = estateMapper.checkEstateVoDeptCode(estateVo.getEstateCode());
        if (integer > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验：工商注册号
     *
     * @param estateVo
     * @return
     */
    @Override
    public String checkEstateVoBusinessCode(EstateVo estateVo) {
        Integer integer = estateMapper.checkEstateVoBusinessCode(estateVo.getBusinessCode());
        if (integer > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验：纳税人识别号
     *
     * @param estateVo
     * @return
     */
    @Override
    public String checkEstateVoTaxpayerCode(EstateVo estateVo) {

        String taxpayerCode = estateVo.getTaxpayerCode();
        Integer integer = estateMapper.checkEstateVoTaxpayerCode(taxpayerCode);
        if (integer > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;

    }

    @Override
    public List<EstateVo> getCheckList() {
       return estateMapper.getCheckList();
    }
}
