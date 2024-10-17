package com.wr.estate.service.manage;

import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.StaffVo;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:06:29
 * @Desc: 员工
 */
public interface StaffService {

    public String importData(List<StaffVo> dataList) throws Exception;
    /**
     * 根据条件分页查询角色数据
     *
     * @param staff 员工信息
     * @return 员工数据集合信息
     */
    List<Staff> selectStaffList(Staff staff);

    /**
     * 根据条件分页查询角色数据
     *
     * @param id 员工信息
     * @return 员工数据集合信息
     */
    List<Staff> getStaffById(@Param("id") Long id);

    /**
     * 根据条id查询员工数据
     *
     * @param staff
     * @return
     */
    int addStaff(@Param("staff") Staff staff);

    /**
     * 修改员工数据
     *
     * @param staff
     * @return
     */
    int updateStaff(@Param("staff") Staff staff);

    /**
     * 根据员工删除sys_user
     *
     * @param staffIds
     * @return
     */
    Integer deleteUserByIds(Long[] staffIds);

    /**
     * 批量删除员工信息
     *
     * @param staffIds ID
     * @return 结果
     */
    int deleteStaffByIds(Long[] staffIds);

    /**
     * 批量离职员工信息
     *
     * @param staffIds ID
     * @return 结果
     */
    int updateStatusByIds(Long[] staffIds);

    /**
     * 修改员工信息密码
     *
     * @param Staff 员工信息密码
     * @return 结果
     */
    int resetPass(Staff Staff);


    /*新增时判断手机号是否存在*/
    String selectPhone(String staffPhone);


    /*修改时判断手机号是否存在*/
    String selectPhoneNo(Staff Staff);

    /**
     * 查询工单空闲人员
     *
     * @param allotId     工单分配id
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @author yx
     * @date 2022/11/3 13:20
     */
    List<Staff> listLeisureStaffs(Long allotId, Long communityId);

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Description 查询工单 空闲 繁忙 人员
     * @Param [allotId, communityId] 工单分配id 社区id
     * @Date 2022/11/4 14:50
     * @Author DIANWEI
     **/
    List<Staff> listLeisureBusyStaffs(@Param("allotType") String allotType,
                                      @Param("communityId") Long communityId);

    /**
     * @return com.wr.remote.estate.manage.Staff
     * @Description 查询工单空闲人员 随机返回一个员工信息
     * @Param [allotId, communityId] 工单分配id  社区id
     * @Date 2022/11/3 15:04
     * @Author DIANWEI
     **/
    Staff getStaffsId(Long allotId, Long communityId);


    /*新增时判断身份证号是否存在*/
    String selectCardNo(String cardNo);

    /*修改时判断身份证号是否存在*/
    String selectCardNum(Staff Staff);

    /**
     * 获取员工名称
     *
     * @param staffId 员工id
     * @return java.lang.String
     * @author yx
     * @date 2022/11/16 15:32
     */
    String getStaffName(Long staffId);

    Long getUserIdStaffId(Long userId);

    /**
     * @param communityId
     * @param workPostList
     * @return
     */
    List<Staff> getCommunityIdWorkPostStaff(Long communityId, List<String> workPostList);

}
