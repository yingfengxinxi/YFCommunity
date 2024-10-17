package com.wr.api.estate.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.estate.entity.dto.UserDTO;
import com.wr.api.estate.mapper.LoginMapper;
import com.wr.api.estate.mapper.application.EstateMobileMenuMapper;
import com.wr.api.estate.mapper.application.manage.StaffMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.common.core.enums.UserStatus;
import com.wr.common.core.utils.ServletUtils;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.auth.Authentication;
import com.wr.common.customize.constant.ApiConstants;
import com.wr.common.customize.model.ApiUser;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.*;
import com.wr.remote.estate.manage.Staff;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 13:12:02
 * @Desc: 物业端登录业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginMapper loginMapper;
    private final RedisService redisService;
    private final EstateMobileMenuMapper estateMobileMenuMapper;
    private final StaffMapper staffMapper;


    @Override
    @Transactional(readOnly = false)
    public AjaxResult login(ApiUser apiUser) {
        SysUser sysUser = loginMapper.selectUserByLoginName(apiUser.getLoginName());
        Assert.notNull(sysUser, "账号不存在");
        if (UserStatus.DISABLE.getCode().equals(sysUser.getStatus())) {
            return AjaxResult.error("此账号已停用");
        }
        if (!SecurityUtils.matchesPassword(apiUser.getPassword(), sysUser.getPassword())) {
            return AjaxResult.error("账号或密码错误");
        }
        List<EstateMobileMenu> menuList = getByUserIdMenuList(sysUser.getUserId());
        if(CollectionUtils.isEmpty(menuList)){
            return AjaxResult.error("当前账号未分配菜单权限，请联系管理员进行分配");
        }
        // 修改登录信息
        insertLoginLog(sysUser);
        // 获取登陆后的 token
        String token = Authentication.createToken(apiUser.getLoginName(), apiUser.getPassword());
        log.info("token:{}", token);
        redisService.setCacheObject(ApiConstants.LOGIN_KEY + apiUser.getLoginName(), apiUser);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("token", token);
        ajaxResult.put("estate", new JSONObject());
        ajaxResult.put("communityList", Lists.newArrayList());
        // 封装返回的数据
        JSONObject loginUser = packageLoginUser(sysUser);
        loginUser.put("loginName", apiUser.getLoginName());

        ajaxResult.put("menuList", menuList);
        if ("12".equals(sysUser.getUserType())) {
            return staffLogin(sysUser.getUserId(), loginUser, ajaxResult);
        }
        ajaxResult.put("loginUser", loginUser);
        return manageLogin(sysUser, ajaxResult);
    }

    /**
     * 修改登录信息
     *
     * @param sysUser 登录用户
     */
    private void insertLoginLog(SysUser sysUser) {
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        loginMapper.updateLoginData(user);
    }

    /**
     * 员工登录
     *
     * @param userId     人员id
     * @param loginUser  需返回的登陆人信息
     * @param ajaxResult 返回结果
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private AjaxResult staffLogin(Long userId, JSONObject loginUser, AjaxResult ajaxResult) {
        Staff staff = loginMapper.selectStaffByUserId(userId);
        if (null == staff) {
            ajaxResult.put("loginUser", loginUser);
            return ajaxResult;
        }
        loginUser.put("userName", staff.getStaffName());
        loginUser.put("userPhone", staff.getStaffPhone());
        loginUser.put("gender", staff.getGender());
        loginUser.put("avatar", staff.getAvatar());
        loginUser.put("address", staff.getAddress());
        loginUser.put("currentId", staff.getCommunityId());
        loginUser.put("communityName", staff.getCommunityName());
        ajaxResult.put("loginUser", loginUser);

        JSONObject staffJson = new JSONObject();
        staffJson.put("staffId", staff.getStaffId());
        staffJson.put("staffName", staff.getStaffName());
        staffJson.put("cardNo", staff.getCardNo());
        staffJson.put("staffType", staff.getStaffType());
        staffJson.put("workPost", staff.getWorkPost());
        staffJson.put("address", staff.getAddress());
        ajaxResult.put("staff", staffJson);

        Estate temp = loginMapper.selectEstateIdByCommunityId(staff.getCommunityId());
        if (null == temp) {
            return ajaxResult;
        }
        packageEstateData(temp, ajaxResult);
        return ajaxResult;
    }

    /**
     * 物业管理员登录
     *
     * @param sysUser    当前登录人
     * @param ajaxResult 返回的结果
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    private AjaxResult manageLogin(SysUser sysUser, AjaxResult ajaxResult) {
        // 默认物业管理员
        Estate temp = loginMapper.selectEstateById(sysUser.getEstateId());
        // 物业自建员工
        /*if("11".equals(sysUser.getUserType())){
            temp = selectEstateByDeptId(sysUser.getDeptId());
        }*/
        if (null == temp) {
            return ajaxResult;
        }
        packageEstateData(temp, ajaxResult);
        List<Community> communityList = loginMapper.selectCommunityList(temp.getEstateId());
        ajaxResult.put("communityList", communityList);
        return ajaxResult;
    }

    /**
     * 查询物业员工已授权菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<EstateMobileMenu> getByUserIdMenuList(Long userId) {
        SysUser user = loginMapper.getByUserIdUser(userId);
        if (user.getUserType().equals("10")) {
            //物业管理员查询全部菜单
            EstateMobileMenu estateMobileMenu = new EstateMobileMenu();
            return estateMobileMenuMapper.getList(estateMobileMenu);
        } else {
            //物业员工
            Staff staff = staffMapper.getStaffById(userId);
            return estateMobileMenuMapper.getStaffIdAuthMenuList(staff.getStaffId());
        }
    }

    public Estate selectEstateByDeptId(Long deptId) {
        SysDept sysDept = loginMapper.selectDeptByDeptId(deptId);
        if ("10".equals(sysDept.getDeptType())) {
            return loginMapper.selectEstateByDeptId(sysDept.getDeptId());
        }
        return selectEstateByDeptId(sysDept.getParentId());
    }

    /**
     * 封装用户数据
     *
     * @param sysUser 当前登录人信息
     * @return com.alibaba.fastjson2.JSONObject
     */
    private static JSONObject packageLoginUser(SysUser sysUser) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", sysUser.getUserId());
        jsonObject.put("userName", sysUser.getNickName());
        jsonObject.put("userType", sysUser.getUserType());
        jsonObject.put("userPhone", sysUser.getPhonenumber());
        jsonObject.put("gender", sysUser.getSex());
        jsonObject.put("avatar", sysUser.getAvatar());
        return jsonObject;
    }

    /**
     * 封装的物业数据
     *
     * @param estate     物业
     * @param ajaxResult 返回数据
     */
    private void packageEstateData(Estate estate, AjaxResult ajaxResult) {
        JSONObject temp = new JSONObject();
        temp.put("estateId", estate.getEstateId());
        temp.put("estateName", estate.getEstateName());
        temp.put("deptId", estate.getDeptId());
        temp.put("hotline", estate.getHotline());
        ajaxResult.put("estate", temp);
    }

    /**
     * 递归查询物业id
     *
     * @param deptId 当前登录人所在部门id
     * @return java.lang.Long
     */
    private Long recursionEstateId(Long deptId) {
        SysDept temp = loginMapper.selectDeptByDeptId(deptId);
        if (null == temp) {
            return 0L;
        }
        if ("10".equals(temp.getDeptType())) {
            return loginMapper.getCurrentEstateId(deptId);
        }
        return recursionEstateId(temp.getParentId());
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult updatePassword(UserDTO userDTO) {
        SysUser sysUser = loginMapper.selectUserByLoginName(userDTO.getLoginName());
        Assert.notNull(sysUser, "未查询到账号信息");
        Staff staff = loginMapper.selectStaffByCardNo(userDTO.getIdCardNo());
        Assert.notNull(staff, "未查询到员工信息");
        if (!sysUser.getUserId().equals(staff.getUserId())) {
            return AjaxResult.error("手机号与身份证号不匹配");
        }
        if (!SecurityUtils.matchesPassword(userDTO.getOldPass(), sysUser.getPassword())) {
            return AjaxResult.error("原密码错误");
        }
        String password = SecurityUtils.encryptPassword(userDTO.getNewPass());
        int num = loginMapper.updatePassword(sysUser.getUserId(), password);
        return num > 0 ? AjaxResult.success("密码修改成功") : AjaxResult.error("密码修改失败");
    }

    @Override
    public AjaxResult logout(String loginName) {
        boolean result = redisService.deleteObject(ApiConstants.LOGIN_KEY + loginName);
        return result ? AjaxResult.success("登出成功") : AjaxResult.error("登出失败");
    }

    @Override
    public SysUser getUserNameNikeName(String userName) {
        return loginMapper.getUserNameNikeName(userName);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public SysUser getByUserIdUser(String userId) {
        return loginMapper.getByUserIdUser(Long.valueOf(userId));
    }


}
