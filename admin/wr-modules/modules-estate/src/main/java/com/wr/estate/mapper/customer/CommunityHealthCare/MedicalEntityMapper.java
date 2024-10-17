package com.wr.estate.mapper.customer.CommunityHealthCare;

import com.wr.estate.entity.MedacalOlderEntity;
import com.wr.estate.entity.MedicalEntity;
import com.wr.estate.entity.vo.ExaminerExcelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医疗检测通告Mapper接口
 *
 * @author bajie
 * @date 2022-10-13
 */
public interface MedicalEntityMapper {
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
     * 删除医疗检测通告
     *
     * @param medicalId 医疗检测通告主键
     * @return 结果
     */
    public int deleteMedicalEntityByMedicalId(Long medicalId);

    /**
     * 批量删除医疗检测通告
     *
     * @param medicalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedicalEntityByMedicalIds(Long[] medicalIds);

    List<MedacalOlderEntity> lookoldere(MedacalOlderEntity medacalOlderEntity);

    MedacalOlderEntity lookoldere1(long examinerId);

    int getnamecount(String activityName);

    Long meid(String activityName);

    int insoldere(List<ExaminerExcelEntity> list);

    public int addpath(@Param("path") String path, @Param("examinerId") long examinerId, @Param("updateBy") String updateBy);

    /**
     * 添加参与人数
     * @param medicalId
     * @return:
     * @date: 2023/5/29 16:12
     * @author: SJiang
     **/
   Integer addJoinNum(@Param("medicalId") Long medicalId,@Param("joinNum") Integer joinNum);

}
