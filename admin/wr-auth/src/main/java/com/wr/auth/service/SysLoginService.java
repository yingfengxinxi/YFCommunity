package com.wr.auth.service;

import com.wr.common.core.constant.Constants;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.enums.UserStatus;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.system.RemoteUserAiIotService;
import com.wr.remote.system.RemoteUserEstateService;
import com.wr.remote.system.RemoteUserGovernService;
import com.wr.remote.system.RemoteUserSysService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author wr
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SysLoginService {
    private static final Logger log = LoggerFactory.getLogger(SysLoginService.class);

    private final RemoteUserSysService remoteUserSysService;
    private final RemoteUserEstateService remoteUserEstateService;
    private final RemoteUserAiIotService remoteUserAiIotService;
    private final RemoteUserGovernService remoteUserGovernService;
    private final SysPasswordService passwordService;
    private final SysRecordLogService recordLogService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password, String userType) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // 查询用户信息
        R<LoginUser> userResult;
        if (UserConstants.USER_ESTATE.equals(userType)) {
            userResult = remoteUserEstateService.getUserInfo(username, SecurityConstants.INNER);
        } else if (UserConstants.USER_AI_IOT.equals(userType)) {
            userResult = remoteUserAiIotService.getUserInfo(username, SecurityConstants.INNER);
        } else if (UserConstants.USER_GOVERN.equals(userType)) {
            userResult = remoteUserGovernService.getUserInfo(username, SecurityConstants.INNER);
        } else {
            userResult = remoteUserSysService.getUserInfo(username, SecurityConstants.INNER);
        }

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password, userType);
        recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public LoginUser adminLogin(String username, String password, String userType) {
        log.info("通用端登录===>{}", userType);
        verifyUser(username, password, userType);
        // 查询用户信息
        R<LoginUser> userResult = remoteUserSysService.getUserInfo(username, SecurityConstants.INNER);

        return findLoginUser(userResult, username, password, userType);
    }

    public LoginUser estateLogin(String username, String password, String userType) {
        log.info("物业端登录===>{}", userType);
        verifyUser(username, password, userType);
        // 查询用户信息
        R<LoginUser> userResult = remoteUserEstateService.getUserInfo(username, SecurityConstants.INNER);
        log.info("userResult:{}", userResult);
        return findLoginUser(userResult, username, password, userType);
    }

    public LoginUser governLogin(String username, String password, String userType) {
        log.info("社区治理平台登录===>{}", userType);
        verifyUser(username, password, userType);
        // 查询用户信息
        R<LoginUser> userResult = remoteUserGovernService.getUserInfo(username, SecurityConstants.INNER);

        return findLoginUser(userResult, username, password, userType);
    }

    public LoginUser iotLogin(String username, String password, String userType) {
        log.info("物联网平台登录===>{}", userType);
        verifyUser(username, password, userType);
        // 查询用户信息
        R<LoginUser> userResult = remoteUserAiIotService.getUserInfo(username, SecurityConstants.INNER);

        return findLoginUser(userResult, username, password, userType);
    }

    private void verifyUser(String username, String password, String userType) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
    }

    private LoginUser findLoginUser(R<LoginUser> userResult, String username, String password, String userType) {
        int code = userResult.getCode();

        if (StringUtils.isNull(userResult)) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else {
            if (code != 200) {
                String msg = userResult.getMsg();
                recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, msg);
                throw new ServiceException(msg);
            }
        }

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }
        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password, userType);
        recordLogService.recordLoginInfo(username, userType, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }


    public void logout(String loginName, String userType) {
        recordLogService.recordLoginInfo(loginName, userType, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserSysService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode()) {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogService.recordLoginInfo(username, UserConstants.USER_SYS, Constants.REGISTER, "注册成功");
    }

}