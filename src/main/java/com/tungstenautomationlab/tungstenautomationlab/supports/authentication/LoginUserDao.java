package com.tungstenautomationlab.tungstenautomationlab.supports.authentication;

import java.util.Optional;

public interface LoginUserDao {
    public Optional<LoginUser> selectLoginUserByUsername(String username);
}
