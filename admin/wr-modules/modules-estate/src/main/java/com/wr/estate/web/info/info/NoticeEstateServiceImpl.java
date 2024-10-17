package com.wr.estate.web.info.info;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.info.NoticeEstateMapper;
import com.wr.estate.service.info.NoticeEstateService;
import com.wr.remote.domain.NoticeEstate;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/3/5 18:19
 */
@Service
public class NoticeEstateServiceImpl extends ServiceImpl<NoticeEstateMapper, NoticeEstate> implements NoticeEstateService {
}
