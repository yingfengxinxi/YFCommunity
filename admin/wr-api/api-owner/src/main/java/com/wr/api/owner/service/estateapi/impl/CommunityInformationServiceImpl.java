package com.wr.api.owner.service.estateapi.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.CommunityInformationMapper;
import com.wr.api.owner.service.estateapi.CommunityInformationService;
import com.wr.remote.domain.CommunityInformation;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/25 14:12
 */
@Service
public class CommunityInformationServiceImpl extends ServiceImpl<CommunityInformationMapper, CommunityInformation> implements CommunityInformationService {
}
