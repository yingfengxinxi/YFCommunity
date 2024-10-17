package com.wr.estate.mapper.manage;


import com.wr.remote.estate.manage.vo.contract.ServerContractVO;
import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServerContractMapper {


    /**
     * 获取服务合同列表
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
     * 获取物业集合
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
     * 添加服务合同
     * @param serverContractVO
     * @return
     */
    int addServe(ServerContractVO serverContractVO);

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
    int delServe(@Param("serverId") Long[] serverId, @Param("userName") String userName);

    /**
     * 获取物业信息
     * @param estateId
     * @return
     */
    Estate getEstateDetail(Long estateId);
}