package com.wr.api.device.mapper;

import com.wr.api.device.entity.vo.AccountVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-09-14 19:11:20
 * @Desc: 用户数据层
 */
public interface UserMapper {

    /**
     * 根据身份证号查询用户
     * @param cardNo
     * @return com.wr.api.device.entity.vo.AccountVO
     */
    AccountVO getAccountByCodeNo(String cardNo);

    /**
     * 根据手机号查询
     * @param userPhone
     * @return
     */
    AccountVO getAccountByPhone(String userPhone);

    /**
     * 根据id查询
     * @param accountId
     * @return
     */
    AccountVO getAccountById(@Param("accountId") Long accountId);

}
