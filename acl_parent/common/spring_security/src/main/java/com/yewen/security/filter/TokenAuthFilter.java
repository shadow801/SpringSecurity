package com.yewen.security.filter;

import com.yewen.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 授权过滤器
 * @author ShadowStart
 * @create 2021-07-18 11:00
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取当前认证成功的用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);

        // 如果有权限信息，放到权限上下文当中
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header获取token
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            // 从token获取用户名
            String username = tokenManager.getUserInfoFromToken(token);

            // 从redis获取对应权限列表信息
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(username);

            Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
                authorityCollection.add(auth);
            }

            return new UsernamePasswordAuthenticationToken(username, token, authorityCollection);
        }
        return null;
    }
}
