package spring.security3.config.security.login;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import spring.security3.module.common.dto.output.ApiResult;
import spring.security3.module.common.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  <p> 认证权限入口 - 未登录的情况下访问所有接口都会拦截到此 </p>
 *
 * @description : 前后端分离情况下返回json格式数据
 * @author : zhengqing
 * @date : 2019/10/11 17:32
 */
@Slf4j
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error(e.getMessage());
        ResponseUtils.out(response, ApiResult.fail("未登录！！！"));
    }

}
