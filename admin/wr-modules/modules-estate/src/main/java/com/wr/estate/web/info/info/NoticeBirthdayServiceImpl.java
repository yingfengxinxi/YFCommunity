package com.wr.estate.web.info.info;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.info.NoticeBirthdayMapper;
import com.wr.estate.service.info.NoticeBirthdayService;
import com.wr.remote.domain.NoticeBirthday;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/3/5 17:58
 */
@Service
public class NoticeBirthdayServiceImpl extends ServiceImpl<NoticeBirthdayMapper, NoticeBirthday> implements NoticeBirthdayService {
}
