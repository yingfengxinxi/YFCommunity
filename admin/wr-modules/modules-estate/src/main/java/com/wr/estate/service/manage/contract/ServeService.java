package com.wr.estate.service.manage.contract;

import com.wr.remote.estate.manage.vo.contract.ServerContractVO;
import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysUser;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-17 15:55:57
 * @Desc: 服务合同业务层
 */
public interface ServeService {

    /**
     * 获取服务合同集合
     * @param serverContractVO
     * @return
     */
    List<ServerContractVO> getServeList(ServerContractVO serverContractVO);

    /**
     * 获取服务合同详情
     * @param serverId
     * @return
     */
    ServerContractVO getDetailInfo(Long serverId);

    /**
     * 获取下拉框物业集合
     * @return
     */
    List<Estate> getEstateList();

    /**
     * 根据合同编号查询是否重复
     * @param contractNo
     * @return
     */
    Integer checkServerContractVO(String contractNo);

    /**
     * 服务合同添加
     * @param serverContractVO
     * @return
     */
    int addServe( ServerContractVO serverContractVO);

    /**
     * 根据物业获取签约人列表
     * @param estateId
     * @return
     */
    List<SysUser> getUserInfoByEstateId(Long estateId);

    /**
     * 删除服务合同
     * @param serverId
     * @return
     */
    int delServe(Long[] serverId);

    /**
     * 获取物业信息
     * @param estateId
     * @return
     */
    Estate getEstateDetail(Long estateId);
}
