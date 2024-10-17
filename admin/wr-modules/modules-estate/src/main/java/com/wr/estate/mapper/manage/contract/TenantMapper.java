package com.wr.estate.mapper.manage.contract;


import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 租客信息
 */
@Mapper
public interface TenantMapper {

    /**
     * 修改帐号状态
     * @param accountId 账号id
     * @return {@link Integer}
     * @ver v1.0.0
     */
    Integer updateAccountType(Long accountId);

    /**
     * 根据手机号或者身份证号查询
     * @param tenantPhone 手机号
     * @param cardNo 身份证号
     * @return {@link Tenant}
     * @ver v1.0.0
     */
    Tenant getByPhoneOrCardNo(@Param("tenantPhone") String tenantPhone, @Param("cardNo") String cardNo);

    /**
     * 添加租客信息
     * @param tenant
     * @return
     */
    Integer tenantAdd(Tenant tenant);


    //Tenant selectTenantByIdCardPhone(@Param("cardNo")String cardNo, @Param("tenantPhone")String tenantPhone,@Param("tenantId") Long tenantId);
}
