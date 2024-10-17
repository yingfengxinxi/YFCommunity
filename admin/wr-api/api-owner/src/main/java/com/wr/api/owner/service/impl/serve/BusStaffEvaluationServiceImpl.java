package com.wr.api.owner.service.impl.serve;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.serve.BusStaffEvaluation;
import com.wr.api.owner.mapper.serve.BusStaffEvaluationMapper;
import com.wr.api.owner.service.serve.BusStaffEvaluationService;
import com.wr.remote.estate.manage.Staff;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/10 20:25
 */
@Service
public class BusStaffEvaluationServiceImpl extends ServiceImpl<BusStaffEvaluationMapper, BusStaffEvaluation> implements BusStaffEvaluationService {

    @Override
    public Staff findOneBusStaff(Long staffId) {
        return baseMapper.findOneBusStaff( staffId );
    }
}
