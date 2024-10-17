package com.wr.estate.service.impl.center.personnelfilemanage;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.personnelfilemanage.VisitorFileMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.personnelfilemanage.VisitorFileService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 15:05:28
 * @Desc:
 */
@Service
public class VisitorFileServiceImpl implements VisitorFileService {
    @Resource
    private AccountManageMapper accountManageMapper;
    @Resource
    private VisitorFileMapper visitorFileMapper;

    @Override
    public List<VisitorFileVo> query(VisitorFileVo visitorFileVo) {

        return visitorFileMapper.query(visitorFileVo);
    }

    @Override
    public VisitorVo getVisitorById(Long visitorId) {
        return visitorFileMapper.getVisitorById(visitorId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleted(Long[] ids) {
        List<Long> accountIds = visitorFileMapper.queryAccountId(ids);
        //删除账号
        accountManageMapper.deleteds(accountIds);
        //删除拜访记录
        visitorFileMapper.removeLiaison(accountIds);
        return visitorFileMapper.deleted(ids);
    }
    @Override
    public List<LinkedHashMap<String, Object>> queryLiaisonByAccountId(Map<String,Object> params) {

        return visitorFileMapper.queryLiaisonByAccountId(params);
    }

}
