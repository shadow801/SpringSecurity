package com.yewen.security.security;

import com.yewen.servicebase.utils.R;
import com.yewen.servicebase.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShadowStart
 * @create 2021-07-18 9:32
 */
public class UnAuthenticationTryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("失败");
        ResponseUtil.out(response, R.error());
    }

}
