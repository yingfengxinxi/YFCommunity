package com.wr.estate.service.impl.manage;

import com.wr.estate.mapper.manage.StaffEvaluationMapper;
import com.wr.estate.service.manage.StaffEvaluationService;
import com.wr.remote.domain.vo.StaffEvaluationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/18 13:43
 */
@Service
public class StaffEvaluationServiceImpl implements StaffEvaluationService {

    @Autowired
    private StaffEvaluationMapper staffEvaluationMapper;

    @Override
    public List<StaffEvaluationVo> getList(Long staffId) {
        return staffEvaluationMapper.getList(staffId);
    }
}
