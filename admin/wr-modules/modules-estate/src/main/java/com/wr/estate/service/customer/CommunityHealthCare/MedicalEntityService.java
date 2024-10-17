package com.wr.estate.service.customer.CommunityHealthCare;

import com.wr.estate.entity.MedacalOlderEntity;
import com.wr.estate.entity.MedicalEntity;
import com.wr.estate.entity.vo.ExaminerExcelEntity;

import java.util.List;

/**
 * 医疗检测通告Service接口
 *
 * @author bajie
 * @date 2022-10-13
 */
public interface MedicalEntityService
{
    /**
     * 查询医疗检测通告
     *
     * @param medicalId 医疗检测通告主键
     * @return 医疗检测通告
     */
    public MedicalEntity selectMedicalEntityByMedicalId(Long medicalId);

    /**
     * 查询医疗检测通告列表
     *
     * @param medicalEntity 医疗检测通告
     * @return 医疗检测通告集合
     */
    public List<MedicalEntity> selectMedicalEntityList(MedicalEntity medicalEntity);

    public List<ExaminerExcelEntity> SelectExaminerExcelEntity(ExaminerExcelEntity medicalEntity);

    /**
     * 新增医疗检测通告
     *
     * @param medicalEntity 医疗检测通告
     * @return 结果
     */
    public int insertMedicalEntity(MedicalEntity medicalEntity);

    /**
     * 修改医疗检测通告
     *
     * @param medicalEntity 医疗检测通告
     * @return 结果
     */
    public int updateMedicalEntity(MedicalEntity medicalEntity);

    /**
     * 批量删除医疗检测通告
     *
     * @param medicalIds 需要删除的医疗检测通告主键集合
     * @return 结果
     */
    public int deleteMedicalEntityByMedicalIds(Long[] medicalIds);

    /**
     * 删除医疗检测通告信息
     *
     * @param medicalId 医疗检测通告主键
     * @return 结果
     */
    public int deleteMedicalEntityByMedicalId(Long medicalId);

    List<MedacalOlderEntity> lookoldere(MedacalOlderEntity medacalOlderEntity);

    MedacalOlderEntity lookoldere1(long examinerId);

    public String importUser(List<ExaminerExcelEntity> userList, Boolean isUpdateSupport, String operName);

    public int addpath(String path,long examinerId);
}
