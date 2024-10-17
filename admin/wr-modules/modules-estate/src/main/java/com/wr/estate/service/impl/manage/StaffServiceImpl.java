package com.wr.estate.service.impl.manage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.CardNoUtil;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.EstateMapper;
import com.wr.estate.mapper.center.BuildingMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.mapper.work.distribution.DistributionMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.VehiclePlateNoUtil;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.StaffVo;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.distribution.Distribution;
import com.wr.system.mapper.UserMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:08:03
 * @Desc:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class StaffServiceImpl implements StaffService {

    //@Value("${estate.defaultMan}")
    //private String defaultMan;

    //@Value("${estate.defaultGirl}")
    //private String defaultGirl;

    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DistributionMapper distributionMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private EstateMapper estateMapper;
    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public String importData(List<StaffVo> dataList) throws Exception {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入员工信息不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StaffVo staffVo : dataList) {

            try {
                String communityName = staffVo.getCommunityName();
                if (StringUtils.isNotEmpty(communityName)) {
                    Long communityId = communityMapper.getByCommunityNameId(communityName);
                    if (communityId != null) {
                        String staffPhone = staffVo.getStaffPhone();
                        if (StringUtils.isNotEmpty(staffPhone)) {
                            if (Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", staffPhone.toString())) {
                                SysUser user = userMapper.getUserByUserName(staffPhone);
                                if (user == null) {
                                    String staffName = staffVo.getStaffName();
                                    if (StringUtils.isNotEmpty(staffName)) {
                                        String workPost = staffVo.getWorkPost();
                                        if (StringUtils.isNotEmpty(workPost)) {
                                            String cardNo = staffVo.getCardNo();
                                            if (StringUtils.isNotEmpty(cardNo)) {
                                                Date entryTime = staffVo.getEntryTime();
                                                if (entryTime != null) {
                                                    //新增数据业务
                                                    Staff staff = new Staff();
                                                    staff.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
                                                    staff.setCommunityId(communityId);
                                                    staff.setStaffName(staffName);
                                                    staff.setStaffPhone(staffPhone);
                                                    staff.setCardNo(cardNo);
                                                    staff.setHealthy(staffVo.getHealthy());
                                                    if (StringUtils.isNotEmpty(staffVo.getCertUrl())) {
                                                        staff.setCertUrl(staffVo.getCertUrl());
                                                    }

                                                    staff.setEntryTime(entryTime);
                                                    staff.setWorkPost(workPost);
                                                    if (StringUtils.isNotEmpty(staffVo.getEducation())) {
                                                        staff.setEducation(staffVo.getEducation());
                                                    }
                                                    if (staffVo.getWorkYear() != null) {
                                                        staff.setWorkYear(staffVo.getWorkYear());
                                                    }
                                                    if (StringUtils.isNotEmpty(staffVo.getAddress())) {
                                                        staff.setAddress(staffVo.getAddress());
                                                    }
                                                    if (StringUtils.isNotEmpty(staffVo.getStaffIntro())) {
                                                        staff.setStaffIntro(staffVo.getStaffIntro());
                                                    }
                                                    staff.setPassword("1234abc");
                                                    this.addStaff(staff);

                                                } else {
                                                    failureNum++;
                                                    failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区,姓名:" + staffVo.getStaffName() + "，入职时间为空，请检查数据后重新导入!");
                                                }

                                            } else {
                                                failureNum++;
                                                failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区,姓名:" + staffVo.getStaffName() + "，身份证号为空，请检查数据后重新导入!");
                                            }
                                        } else {
                                            failureNum++;
                                            failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区,姓名:" + staffVo.getStaffName() + "，未选择岗位，请检查数据后重新导入!");
                                        }

                                    } else {
                                        failureNum++;
                                        failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区下存在姓名为空的数据，请检查数据后重新导入!");
                                    }
                                } else {
                                    failureNum++;
                                    failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区下" + staffVo.getStaffPhone() + "手机号已存在，请检查数据后重新导入!");
                                }

                            } else {
                                failureNum++;
                                failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区下" + staffVo.getStaffPhone() + "手机号格式错误，请检查数据后重新导入!");
                            }
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + staffVo.getCommunityName() + "小区下存在手机号为空的信息，请检查数据后重新导入!");
                        }

                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、" + staffVo.getCommunityName() + "小区不存在,请输入正确的小区全称!");
                    }


                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + " 、所属小区不能为空");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、用户 " + staffVo.getStaffName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据不正确，错误如下：");
            //throw new ServiceException(failureMsg.toString());
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            return successMsg.toString();
        }
    }

    @Override
    public List<Staff> selectStaffList(Staff staff) {
        //查询楼栋
        List<Staff> staffList = staffMapper.selectStaffList(staff);
        if (CollectionUtils.isNotEmpty(staffList)) {
            staffList.stream().forEach(staff1 -> {
                extracted(staff1);
            });
        }
        return staffList;
    }

    private void extracted(Staff staff1) {
        String buildingIds = staff1.getBuildingId();
        if (StringUtils.isNotEmpty(buildingIds)) {
            List<String> buildingIdList = Arrays.asList(buildingIds.split(","));
            StringBuilder sb = new StringBuilder();
            for (String buildingId : buildingIdList) {
                Building building = buildingMapper.getDetailByBuildingId(Long.valueOf(buildingId));
                if (building != null) {
                    sb.append(building.getBuildingName()).append(",");
                }
            }
            String buildingName = sb.toString();
            if (StringUtils.isNotEmpty(buildingName)) {
                staff1.setBuildingName(buildingName.substring(0, buildingName.length() - 1));
            }
        }
    }

    @Override
    public List<Staff> getStaffById(Long id) {
        List<Staff> staffList = staffMapper.getStaffById(id);
        if (CollectionUtils.isNotEmpty(staffList)) {
            staffList.stream().forEach(staff1 -> {
                extracted(staff1);
            });
        }
        return staffList;
    }

    @Override
    @Transactional(readOnly = false)
    public int addStaff(Staff staff) {
        staff.setGender(CardNoUtil.judgeGender(staff.getCardNo()));
        staff.setCreateBy(SecurityUtils.getUsername());
        if (StringUtils.isEmpty(staff.getAvatar())) {
            String avatar = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "estateAvatar");

            //"1".equals(staff.getGender()) ? defaultGirl : defaultMan
            staff.setAvatar(avatar);
        }
        if ("".equals(staff.getGender()) || null == staff.getGender()) {
            staff.setGender("2");
        }
        if ("".equals(staff.getAvatar()) || null == staff.getAvatar()) {
            staff.setAvatar("");
        }
        if ("".equals(staff.getWorkPost()) || null == staff.getWorkPost()) {
            staff.setWorkPost("0");
        }
        if ("".equals(staff.getHealthy()) || null == staff.getHealthy()) {
            staff.setHealthy("0");
        }
        if ("".equals(staff.getCertUrl()) || null == staff.getCertUrl()) {
            staff.setCertUrl("");
        }
        if ("".equals(staff.getStaffType()) || null == staff.getStaffType()) {
            staff.setStaffType("0");
        }
        if (null == staff.getWorkYear()) {
            staff.setWorkYear(0);
        }
        if ("".equals(staff.getAddress()) || null == staff.getAddress()) {
            staff.setAddress("");
        }
        if ("".equals(staff.getStaffIntro()) || null == staff.getStaffIntro()) {
            staff.setStaffIntro("");
        }
        Estate estate = estateMapper.selectEstateByCommunityId(staff.getCommunityId());
        String username = SecurityUtils.getUsername();
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(estate.getEstateId());
        if (StringUtils.isNotEmpty(staff.getAvatar())) {
            sysUser.setAvatar(staff.getAvatar());
        }

        sysUser.setUserName(staff.getStaffPhone());
        sysUser.setPhonenumber(staff.getStaffPhone());
        sysUser.setNickName(staff.getStaffName());
        sysUser.setUserType("12");
        sysUser.setCreateBy(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(staff.getPassword()));
        staffMapper.insertUser(sysUser);
        staff.setUserId(sysUser.getUserId());
        staffMapper.addStaff(staff);
        Long staffId = staffMapper.getByUserIdStaffId(sysUser.getUserId());
        //生成二维码
        String codeImage = QrCodeUtils.getQRCodeImage(String.valueOf(staffId), staff.getStaffName(), "png");
        staffMapper.updateByIdQrCode(codeImage, staffId);
        return 1;
    }

    @Override
    @Transactional(readOnly = false)
    public int updateStaff(Staff staff) {
        String username = SecurityUtils.getUsername();
        SysUser sysUser = userMapper.selectUserById(staff.getUserId());
        sysUser.setAvatar(staff.getAvatar());
        sysUser.setUserName(staff.getStaffPhone());
        sysUser.setPhonenumber(staff.getStaffPhone());
        sysUser.setNickName(staff.getStaffName());
        sysUser.setUpdateBy(username);
        //sysUser.setPassword(SecurityUtils.encryptPassword(staff.getPassword()));
        userMapper.updateUser(sysUser);
        staff.setGender(CardNoUtil.judgeGender(staff.getCardNo()));
        //生成二维码
        staff.setQrCodeBase64(QrCodeUtils.getQRCodeImage(String.valueOf(staff.getStaffId()), staff.getStaffName(), "png"));
        return staffMapper.updateStaff(staff);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer deleteUserByIds(Long[] staffIds) {
        return staffMapper.deleteUserByIds(staffIds);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteStaffByIds(Long[] staffIds) {
        return staffMapper.deleteStaffByIds(staffIds);
    }

    @Override
    @Transactional(readOnly = false)
    public int updateStatusByIds(Long[] staffIds) {
        return staffMapper.updateStatusByIds(staffIds);
    }

    @Override
    @Transactional(readOnly = false)
    public int resetPass(Staff staff) {
        SysUser sysUser = userMapper.selectUserById(staff.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(staff.getPassword()));
        return userMapper.updateUser(sysUser);
        //return staffMapper.resetPass(staff);
    }

    @Override
    public String selectPhone(String staffPhone) {
        int count = staffMapper.selectPhone(staffPhone);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String selectPhoneNo(Staff Staff) {
        int count = staffMapper.selectPhoneNo(Staff);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 查询工单空闲人员
     *
     * @param allotId     工单分配id
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.estate.manage.Staff>
     * @author yx
     * @date 2022/11/3 13:20
     */
    @Override
    public List<Staff> listLeisureStaffs(Long allotId, Long communityId) {
        if (ObjectUtils.isEmpty(allotId) || ObjectUtils.isEmpty(communityId)) {
            throw new ServiceException("参数不能为空！");
        }
        return staffMapper.listLeisureStaffs(allotId, communityId);
    }

    @Override
    public List<Staff> listLeisureBusyStaffs(String allotType, Long communityId) {
        if (ObjectUtils.isEmpty(allotType) || ObjectUtils.isEmpty(communityId)) {
            throw new ServiceException("参数不能为空！");
        }
        Distribution distribution = distributionMapper.getAllotByType(communityId, allotType);
        if (StringUtils.isNull(distribution)) {
            return null;
        }
        return staffMapper.listLeisureBusyStaffs(distribution.getAllotId(), communityId);

    }

    /**
     * @return com.wr.remote.estate.manage.Staff
     * @Description 查询工单空闲人员 随机返回一个员工信息
     * @Param [allotId, communityId] 工单分配id  社区id
     * @Date 2022/11/3 15:04
     * @Author DIANWEI
     **/
    @Override
    public Staff getStaffsId(Long allotId, Long communityId) {
        List<Staff> staffList = listLeisureStaffs(allotId, communityId);
        if (StringUtils.isEmpty(staffList)) {
            return null;
        } else {
            Random random = new Random();
            //随机获取一个员工
            int i = random.nextInt(staffList.size());
            Staff staff = staffList.get(i);
            if (StringUtils.isNull(staff)) {
                return null;
            }
            return staff;
        }

    }

    @Override
    public String selectCardNo(String cardNo) {
        int count = staffMapper.selectCardNo(cardNo);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String selectCardNum(Staff Staff) {
        int count = staffMapper.selectCardNum(Staff);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取员工名称
     *
     * @param staffId 员工id
     * @return java.lang.String
     * @author yx
     * @date 2022/11/16 15:32
     */
    @Override
    public String getStaffName(Long staffId) {
        return staffMapper.getStaffName(staffId);
    }

    @Override
    public Long getUserIdStaffId(Long userId) {
        return staffMapper.getUserIdStaffId(userId);
    }

    /**
     * @param communityId
     * @param workPostList
     * @return
     */
    @Override
    public List<Staff> getCommunityIdWorkPostStaff(Long communityId, List<String> workPostList) {
        return staffMapper.getCommunityIdWorkPostStaff(communityId, workPostList);
    }

}
