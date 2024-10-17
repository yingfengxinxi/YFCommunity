package com.wr.api.estate.web;

import com.wr.common.core.utils.JwtUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.auth.AuthUtil;
import com.wr.common.security.service.TokenService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lvzy
 * @Date 2024/6/12 10:10
 */
@RestController
@RequestMapping("v2/token")
public class TokenController extends BaseController {

    @GetMapping("isToken")
    public AjaxResult isToken(@RequestParam("token") String token) {
        String username = JwtUtils.getUserName(token);
        return AjaxResult.success(username);
    }
}
