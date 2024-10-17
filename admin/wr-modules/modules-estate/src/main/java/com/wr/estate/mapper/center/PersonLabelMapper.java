package com.wr.estate.mapper.center;

import com.wr.remote.estate.center.PersonLabel;
import com.wr.remote.estate.center.vo.PersonLabelVo;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 09:43:56
 * @Desc: 人员标签
 */
public interface PersonLabelMapper {
    
    /**
     * 查询人员标签设置
     *
     * @param labelId 人员标签设置主键
     * @return 人员标签设置
     */
     PersonLabelVo selectPersonLabelByLabelId(Long labelId);
    //PersonLabelVo selectPersonLabelByLabelId(PersonLabelVo personLabelVo);
    /**
     * 查询人员标签设置列表
     *
     * @param personLabelVo 人员标签设置
     * @return 人员标签设置集合
     */
     List<PersonLabelVo> selectPersonLabelList(PersonLabelVo personLabelVo);

    /**
     * 查询人员姓名列表
     *
     * @param personLabelVo 人员标签设置
     * @return 人员标签设置集合
     */
    List<PersonLabelVo> selectNameList(PersonLabelVo personLabelVo);

    /**
     * 新增人员标签设置
     *
     * @param personLabelVo 人员标签设置
     * @return 结果
     */
     int insertPersonLabel(PersonLabelVo personLabelVo);

    /**
     * 修改人员标签设置
     *
     * @param personLabel 人员标签设置
     * @return 结果
     */
     int updatePersonLabel(PersonLabel personLabel);

    /**
     * 批量删除人员标签设置
     *
     * @param labelIds 需要删除的数据主键集合
     * @return 结果
     */
     int deletePersonLabelByLabelIds(Long[] labelIds);

    /**
     * 校验出入口名称
     *
     * @param id 出入口名称
     * @return 结果
     */
    int checkUnique(Long id);
}
