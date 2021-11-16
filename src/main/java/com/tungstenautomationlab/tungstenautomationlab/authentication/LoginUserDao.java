package com.tungstenautomationlab.tungstenautomationlab.authentication;

import java.util.Optional;

public interface LoginUserDao {
    public Optional<LoginUser> selectLoginUserByUsername(String username);
}
