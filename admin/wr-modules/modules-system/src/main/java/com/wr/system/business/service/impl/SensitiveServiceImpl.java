package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.remote.domain.Sensitive;
import com.wr.system.business.mapper.SensitiveMapper;
import com.wr.system.business.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 敏感词管理service层实现类
 * @Date 2022/9/19 11:39
 * @Author DIANWEI
 **/
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SensitiveServiceImpl implements SensitiveService {
    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public List<Sensitive> getListBusSensitive(Sensitive busSensitive) {
        return sensitiveMapper.getListBusSensitive(busSensitive);
    }

    @Override
    public String checkPhrasesUnique(Sensitive sensitive) {
        Long userId = StringUtils.isNull(sensitive.getSensitiveId()) ? -1L : sensitive.getSensitiveId();
        Sensitive info = sensitiveMapper.checkPhrasesUnique(sensitive.getPhrases());
        if (StringUtils.isNotNull(info) && info.getSensitiveId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer insertBusSensitive(Sensitive busSensitive) {
        return sensitiveMapper.insertBusSensitive(busSensitive);
    }

    @Override
    public Sensitive selectSensitiveById(Long sensitiveId) {
        return sensitiveMapper.selectSensitiveById(sensitiveId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateBusSensitive(Sensitive busSensitive) {
        return sensitiveMapper.updateBusSensitive(busSensitive);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteSensitiveByIds(Long[] sensitiveIds) {
        return sensitiveMapper.deleteSensitiveByIds(sensitiveIds);
    }
}
