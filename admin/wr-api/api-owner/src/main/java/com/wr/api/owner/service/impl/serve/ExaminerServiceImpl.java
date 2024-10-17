package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.ExaminerVO;
import com.wr.api.owner.mapper.serve.ExaminerMapper;
import com.wr.api.owner.service.serve.ExaminerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 11:31:05
 * @Desc: 服务-体检报告 业务实现
 */
@Service
@Slf4j
public class ExaminerServiceImpl implements ExaminerService {
    @Autowired
    private ExaminerMapper examinerMapper;

    @Override
    public List<ExaminerVO> getExaminerAll(Long communityId, String idCard) {
        return examinerMapper.getExaminerAll(communityId, idCard);
    }

    @Override
    public ExaminerVO getExaminerById(Long examinerId) {
        return examinerMapper.getExaminerById(examinerId);
    }
}
