package com.wr.estate.service.impl.manage.contract;

import com.wr.common.security.utils.SecurityUtils;

import com.wr.estate.mapper.manage.ServerContractMapper;
import com.wr.estate.service.manage.contract.ServeService;
import com.wr.remote.estate.manage.vo.contract.ServerContractVO;
import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-17 15:56:34
 * @Desc: 服务合同业务层实现类
 */
@Service
public class ServeServiceImpl implements ServeService {

    @Autowired
    private ServerContractMapper serverContractMapper;

    @Override
    public List<ServerContractVO> getServeList(ServerContractVO serverContractVO) {
        serverContractVO.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return serverContractMapper.getServeList(serverContractVO);
    }

    @Override
    public ServerContractVO getDetailInfo(Long serverId) {
        return serverContractMapper.getDetailInfo(serverId);
    }

    @Override
    public List<Estate> getEstateList() {
        return serverContractMapper.getEstateList();
    }

    @Override
    public Integer checkServerContractVO(String contractNo) {
        return serverContractMapper.checkServerContractVO(contractNo);
    }

    @Transactional
    @Override
    public int addServe(ServerContractVO serverContractVO) {
        return serverContractMapper.addServe(serverContractVO);
    }

    @Override
    public List<SysUser> getUserInfoByEstateId(Long estateId) {
        return serverContractMapper.getUserInfoByEstateId(estateId);
    }

    @Override
    @Transactional
    public int delServe(Long[] serverId) {
        String username = SecurityUtils.getUsername();
        return serverContractMapper.delServe(serverId,username);
    }

    @Override
    public Estate getEstateDetail(Long estateId) {
        return serverContractMapper.getEstateDetail(estateId);
    }
}
