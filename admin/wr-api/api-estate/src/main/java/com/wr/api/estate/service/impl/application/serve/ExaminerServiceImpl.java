package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.vo.application.serve.ExaminerVO;
import com.wr.api.estate.entity.vo.application.serve.MedicalVO;
import com.wr.api.estate.mapper.application.serve.ExaminerMapper;
import com.wr.api.estate.service.application.serve.ExaminerService;
import com.wr.remote.estate.manage.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 10:32:31
 * @Desc:
 */
@Service
public class ExaminerServiceImpl implements ExaminerService {
    @Autowired
    private ExaminerMapper examinerMapper;

    @Override
    public List<ExaminerVO> getExaminerAll(Long communityId, String idCard) {
        return examinerMapper.getExaminerAll(communityId, idCard);
    }

    /**
     * 医疗检测通告列表
     *
     * @param communityId 社区id
     * @return {@code List<MedicalVO>}
     */
    @Override
    public List<MedicalVO> listMedical(Long communityId) {
        return examinerMapper.listMedical(communityId);
    }

    /**
     * 列表
     *
     * @param medicalId 医疗标识
     * @return {@code List<ExaminerVO>}
     */
    @Override
    public List<ExaminerVO> listExaminerByMedicalId(Long medicalId) {
        return examinerMapper.listExaminerByMedicalId(medicalId);
    }

    @Override
    public Staff getIdNoByPhone(String phone) {
        return examinerMapper.getIdNoByPhone(phone);
    }
}
