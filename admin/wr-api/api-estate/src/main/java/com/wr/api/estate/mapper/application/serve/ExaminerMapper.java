package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.vo.application.serve.ExaminerVO;
import com.wr.api.estate.entity.vo.application.serve.MedicalVO;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 10:22:21
 * @Desc: 服务-体检报告 mapper
 */
public interface ExaminerMapper {

    /**
     * 获取体检报告
     *
     * @param communityId 小区id
     * @param idCard      身份证号
     * @return:
     */
    List<ExaminerVO> getExaminerAll(@Param("communityId") Long communityId, @Param("idCard") String idCard);

    /**
     * 医疗检测通告列表
     *
     * @param communityId 社区id
     * @return {@code List<MedicalVO>}
     */
    List<MedicalVO> listMedical(Long communityId);

    /**
     * 列表
     *
     * @param medicalId 医疗标识
     * @return {@code List<ExaminerVO>}
     */
    List<ExaminerVO> listExaminerByMedicalId(Long medicalId);

    /**
     * 根据手机号获取身份证号
     *
     * @param phone
     * @return
     */
    Staff getIdNoByPhone(String phone);

}
