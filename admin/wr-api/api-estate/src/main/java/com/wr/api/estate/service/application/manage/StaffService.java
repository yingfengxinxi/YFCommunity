package com.wr.api.estate.service.application.manage;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:06:29
 * @Desc: 员工
 */
public interface StaffService {
//    /**
//     * 根据条件分页查询角色数据
//     *
//     * @param staff 员工信息
//     * @return 员工数据集合信息
//     */
//    List<Staff> selectStaffList(Staff staff);
//

    /**
     * 根据条件分页查询角色数据
     *
     * @param userId 员工信息
     * @return 员工数据集合信息
     */
    Staff getStaffById(Long userId);

    //
//    /**
//     * 根据条id查询员工数据
//     *
//     * @param staff
//     * @return
//     */
//    int addStaff(@Param("staff") Staff staff);
//

    /**
     * 修改员工数据
     *
     * @param staff
     * @return
     */
    AjaxResult updateStaff(@Param("staff") Staff staff);

    /**
     * 修改员工信息密码
     *
     * @param Staff 员工信息密码
     * @return 结果
     */
    void resetPass(Staff Staff);

    /**
     * 修改头像
     *
     * @param Staff
     * @return
     */
    void resetAvatar(Staff Staff);

    /**
     * 修改时判断手机号是否存在
     *
     * @param Staff
     * @return
     */
    String selectPhoneNo(Staff Staff);


    /**
     * 修改时判断身份证号是否存在
     *
     * @param Staff
     * @return
     */
    String selectCardNum(Staff Staff);


    Staff getByUserIdStaff( String userId,String staffId);

}
