package com.wr.system.business.mapper;

import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysDept;
import com.wr.system.business.domain.EstateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业公司详情数据层
 *
 * @Date 2022/9/20 10:29
 * @Author DIANWEI
 **/
public interface EstateMapper {

    /**
     * 查询物业公司详情列表
     *
     * @param estateVo
     * @return
     */
    List<EstateVo> selectEstateVoList(EstateVo estateVo);

    /**
     * 校验物业公司名称是否唯一
     *
     * @param deptName
     * @param parentId
     * @return
     */
    EstateVo checkEstateVoNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据id查询数据
     *
     * @param estateId
     * @return
     */
    EstateVo selectEstateVoById(Long estateId);
    /**
     * 根据name查询数据
     *
     * @param name
     * @return
     */
    EstateVo selectEstateVoByName(String name);
    /**
     * 添加机构
     *
     * @param dept
     * @return
     */
    Integer insertDept(SysDept dept);

    /**
     * 添加物业信息
     *
     * @param estate
     * @return
     */
    Integer insertEstateVo(EstateVo estate);

    /**
     * 修改物业信息
     *
     * @param estate
     * @return
     */
    Integer updateEstateVo(EstateVo estate);

    /**
     * 修改物业公司状态
     *
     * @param estateVo
     * @return
     */
    Integer updateStatus(EstateVo estateVo);

    /**
     * 根据id查询子集
     *
     * @param deptId
     * @return
     */
    List<EstateVo> selectChildrenDeptById(Long deptId);

    /**
     * 校验：统一社会信用代码
     *
     * @param creditCode
     */
    Integer checkEstateVoCreditCodeUnique(String creditCode);

    /**
     * 校验：机构编码
     *
     * @param deptCode
     */
    Integer checkEstateVoDeptCode(String deptCode);

    /**
     * 校验：工商注册号
     *
     * @param BusinessCode
     * @return
     */
    Integer checkEstateVoBusinessCode(String BusinessCode);

    /**
     * 校验：纳税人识别号
     *
     * @param taxpayerCode
     * @return
     */
    Integer checkEstateVoTaxpayerCode(String taxpayerCode);
    List<EstateVo> getCheckList();
}
