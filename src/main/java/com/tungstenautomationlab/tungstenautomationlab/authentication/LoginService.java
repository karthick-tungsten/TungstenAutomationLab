package com.tungstenautomationlab.tungstenautomationlab.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final LoginUserDao loginUserDao;

    @Autowired
    public LoginService(@Qualifier("fake") LoginUserDao loginUserDao) {
        this.loginUserDao = loginUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserDao.selectLoginUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username+" not found"));
    }
}
