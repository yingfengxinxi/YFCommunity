package com.wr.system.business.service;

import com.wr.system.business.domain.EstateVo;

import java.util.List;

/**
 * @author DIANWEI
 * @date 2022/9/20 10:02
 */
public interface EstateService {
    /**
     * 查询物业公司详情列表
     * @param estateVo
     * @return
     */
    List<EstateVo> selectEstateVoList(EstateVo estateVo);

    /**
     * 校验机构名称是否唯一
     * @param estateVo
     * @return
     */
    String checkEstateVoNameUnique(EstateVo estateVo);

    /**
     * 新增物业公司
     * @param estateVo
     * @return
     */
    Integer insertEstateVo(EstateVo estateVo);

    /**
     * 根据id查询
     * @param estateId
     * @return
     */
    EstateVo selectEstateVoById(Long estateId);
    /**
     * 根据id查询
     * @param name
     * @return
     */
    EstateVo selectEstateVoByName(String name);

    /**
     * 修改物业信息
     * @param estateVo
     * @return
     */
    Integer updateEstateVo(EstateVo estateVo);
    /**
     * 修改物业公司状态
     * @param estateVo
     * @return
     */
    Integer updateStatus(EstateVo estateVo);

    /**
     * 根据id查询子集
     * @param deptId
     * @return
     */
    List<EstateVo> selectChildrenDeptById(Long deptId);

    /**
     * 校验：统一社会信用代码
     * @param estateVo
     * @return
     */
    String checkEstateVoCreditCodeUnique(EstateVo estateVo);

    /**
     * 校验：机构编码
     * @param estateVo
     * @return
     */
    String checkEstateVoDeptCode(EstateVo estateVo);

    /**
     * 校验：工商注册号
     * @param estateVo
     * @return
     */
    String checkEstateVoBusinessCode(EstateVo estateVo);

    /**
     * 校验：纳税人识别号
     * @param estateVo
     * @return
     */
    String checkEstateVoTaxpayerCode(EstateVo estateVo);

    /**
     * 获取检查列表
     * @return
     */
    List<EstateVo> getCheckList();
}
