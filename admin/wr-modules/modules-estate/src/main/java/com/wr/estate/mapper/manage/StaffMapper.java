package com.wr.estate.mapper.manage;

import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 11:28:34
 * @Desc: 员工
 */
@Mapper
public interface StaffMapper {

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
    List<Staff> getStaffById(Long id);

    /**
     * 根据条id查询员工数据
     *
     * @param staff
     * @return
     */
    int addStaff(Staff staff);

    /**
     * 修改员工二维码
     *
     * @param qrCodeBase64
     * @param userId
     * @return
     */
    int updateByIdQrCode(@Param("qrCodeBase64") String qrCodeBase64, @Param("staffId") Long staffId);

    Long getByUserIdStaffId(@Param("userId") Long userId);

    int updateStaff(Staff staff);

    /**
     * 根据员工id删除sys_user数据
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
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    String selectUserIdByUserName(String userName);

    /**
     * 修改员工信息密码
     *
     * @param Staff 员工信息密码
     * @return 结果
     */
    int resetPass(Staff Staff);

    int selectPhone(String staffPhone);

    Staff getStaffPhoneId(String staffPhone);

    int selectPhoneNo(Staff Staff);

    int selectCardNo(String cardNo);

    int selectCardNum(Staff Staff);

    Long selectStaffId(String cardNo);

    /**
     * 查询工单空闲人员
     *
     * @param allotId     工单分配id
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @author yx
     * @date 2022/11/3 13:20
     */
    List<Staff> listLeisureStaffs(@Param("allotId") Long allotId,
                                  @Param("communityId") Long communityId);

    /**
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @Description 查询工单 空闲 繁忙 人员
     * @Param [allotId, communityId] 工单分配id 社区id
     * @Date 2022/11/4 14:50
     * @Author DIANWEI
     **/
    List<Staff> listLeisureBusyStaffs(@Param("allotId") Long allotId,
                                      @Param("communityId") Long communityId);

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
    List<Staff> getCommunityIdWorkPostStaff(@Param("communityId") Long communityId, @Param("workPostList") List<String> workPostList);

    /**
     *
     * @param buildingId
     * @param communityId
     * @return
     */
    int getBuildingCount(@Param("buildingId") String buildingId, @Param("communityId") Long communityId);
}
