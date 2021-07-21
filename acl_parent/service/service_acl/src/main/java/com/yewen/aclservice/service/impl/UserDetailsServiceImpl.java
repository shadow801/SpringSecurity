package com.yewen.aclservice.service.impl;

import com.yewen.aclservice.entities.User;
import com.yewen.aclservice.service.PermissionService;
import com.yewen.aclservice.service.UserService;
import com.yewen.security.entities.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ShadowStart
 * @create 2021-07-18 13:54
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询数据
        User user = userService.selectByUsername(username);
        // 判断
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        com.yewen.security.entities.User curUser = new com.yewen.security.entities.User();
        BeanUtils.copyProperties(user, curUser);

        // 根据用户查询用户权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
