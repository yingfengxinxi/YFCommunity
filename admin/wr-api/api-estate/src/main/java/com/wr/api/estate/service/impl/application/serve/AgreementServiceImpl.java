package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.serve.AgreementMapper;
import com.wr.api.estate.service.application.serve.AgreementService;
import com.wr.remote.domain.Agreement;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/2/7 21:37
 */
@Service
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements AgreementService {
}
