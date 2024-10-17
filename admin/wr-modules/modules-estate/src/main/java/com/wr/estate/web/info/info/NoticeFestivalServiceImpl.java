package com.wr.estate.web.info.info;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.estate.mapper.info.NoticeFestivalMapper;
import com.wr.estate.service.info.NoticeFestivalService;
import com.wr.remote.domain.NoticeFestival;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/3/5 17:58
 */
@Service
public class NoticeFestivalServiceImpl extends ServiceImpl<NoticeFestivalMapper,NoticeFestival>implements NoticeFestivalService {
}
